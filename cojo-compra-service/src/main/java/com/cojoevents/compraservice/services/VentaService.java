package com.cojoevents.compraservice.services;

import com.cojoevents.compraservice.entities.Producto;
import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.repositories.VentaRepository;
import com.cojoevents.compraservice.responses.LoginResponse;
import com.cojoevents.compraservice.responses.VentaResponse;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VentaService {
    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ProductoService productoService;
    @Autowired
    RestTemplate restTemplate;

    public void insertarVenta(VentaResponse ventaResp){
        Producto producto = productoService.getProducto(ventaResp.producto);
        Venta venta = new Venta(ventaResp.monto, ventaResp.usuario, new Date(), producto);
        ventaRepository.save(venta);
    }

    public List<VentaResponse> obtenerAllVentas(){
        List<Venta> allVentas = ventaRepository.findAll();
        List<VentaResponse> ventas = new ArrayList<>();
        for (Venta venta : allVentas){
            VentaResponse ventaResponse = new VentaResponse();
            ventaResponse.monto = venta.getMonto();
            ventaResponse.producto = venta.getProducto().getNombreProducto();
            ventaResponse.usuario = venta.getUsuario();
            ventas.add(ventaResponse);
        }
        return ventas;
    }
    public List<VentaResponse> obtenerVentasByUser(String username){
        List<Venta> allVentas = ventaRepository.findAllByUsuario(username);
        List<VentaResponse> ventas = new ArrayList<>();
        for (Venta venta : allVentas){
            VentaResponse ventaResponse = new VentaResponse();
            ventaResponse.monto = venta.getMonto();
            ventaResponse.producto = venta.getProducto().getNombreProducto();
            ventaResponse.usuario = venta.getUsuario();
            ventas.add(ventaResponse);
        }
        return ventas;
    }


    public void enviarEmailConfirmacionVenta(VentaResponse ventaResponse) throws IOException {
        Email from = new Email("jmlmartinezg@outlook.com");
        String subject = "Compra Completada - COJOEVENTS";
        String apiKey = "tbd";
        //Recibiendo arreglo de respuestas de tipo LoginResponse del API de usuarios.
        LoginResponse []responses = restTemplate.getForObject("/api/auth/empleados", LoginResponse[].class);
        assert responses != null;
        System.out.println(responses.length);

        for(LoginResponse loginResponse : responses){
            Email to = new Email(loginResponse.email);
            String body = "¡Enhorabuena " + ventaResponse.usuario +
                    "\n\nSu paquete " + ventaResponse.producto + " ha sido procesado y pagado con éxito." +
                    "\n\nUn saludo," +
                    "\n COJOEVENTS Team";
            Content content = new Content("text/plain", body);
            Mail mail = new Mail(from, subject, to, content);
            SendGrid sendGrid  = new SendGrid(apiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            
            System.out.println("==== PRUEBA ENVÍO CORRECTO ====");
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        }
    }
}

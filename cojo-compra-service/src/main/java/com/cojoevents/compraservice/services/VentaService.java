package com.cojoevents.compraservice.services;

import com.cojoevents.compraservice.entities.Producto;
import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.repositories.VentaRepository;
import com.cojoevents.compraservice.responses.LoginResponse;
import com.cojoevents.compraservice.responses.VentaResponse;
import com.sendgrid.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        //String path = "D:\\PUCMM\\2019-2020 (3)\\Web Avanzada\\microservicios-springboot\\cojo-compra-service\\src\\main\\resources\\reportes";
        String path = "C:\\Users\\jmlma\\Documents\\WebAvanzada\\microservicios-springboot\\cojo-compra-service\\src\\main\\resources\\reportes";
        List<Venta> ventas = ventaRepository.findAll();
        //Load File and compile it
        File file = ResourceUtils.getFile("classpath:ventas.jrxml");
        JasperReport jasperReport= JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource= new JRBeanCollectionDataSource(ventas);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("createdBy","Cojo Events");
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"/ventas.html");
        }
        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint,path+"/ventas.pdf");

        }

        return "Reporte generado en ruta: " + path;
    }
}

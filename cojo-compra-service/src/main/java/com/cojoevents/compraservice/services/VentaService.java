package com.cojoevents.compraservice.services;

import com.cojoevents.compraservice.entities.Producto;
import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.repositories.VentaRepository;
import com.cojoevents.compraservice.responses.LoginResponse;
import com.cojoevents.compraservice.responses.UsuarioResponse;
import com.cojoevents.compraservice.responses.VentaResponse;
import com.sendgrid.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.http.impl.bootstrap.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
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
        Email from = new Email("20150189@ce.pucmm.edu.do");
        String subject = "Compra Completada - COJOEVENTS";
        String apiKey = "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";
        String url = "http://localhost:8081/api/find-usuario?username="+ventaResponse.usuario;
        UsuarioResponse uResponse = restTemplate.getForObject(url,UsuarioResponse.class);
        Email to = new Email(uResponse.email);
        String body = "¡Enhorabuena " + ventaResponse.usuario +
                "\n\nSu paquete " + ventaResponse.producto + " ha sido procesado y pagado con éxito." +
                "\n\nUn saludo," +
                "\n COJOEVENTS Team";
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sendGrid  = new SendGrid(apiKey);
        Request request = new Request();

        //Enviando correo a todos los empleados
        enviarCorreoEmpleados(from,ventaResponse,sendGrid);

        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            System.out.println("==== PRUEBA ENVÍO CORRECTO ====");
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        }catch (IOException ex){
            ex.printStackTrace();
        }



    }

    public void enviarCorreoEmpleados(Email from, VentaResponse ventaResponse, SendGrid sendGrid){
        String url = "http://localhost:8081/api/obtener-empleados";
        UsuarioResponse []responses = restTemplate.getForObject(url,UsuarioResponse[].class);
        assert responses != null;
        for(UsuarioResponse empleado: responses){
            Email to = new Email(empleado.email);
            String subject = "Nuevo trabajo pendiente";
            String body = "El usuario: " + ventaResponse.usuario +
                    "\n\nA realizado una compra del paquete " + ventaResponse.producto + " que se debe trabajar en el." +
                    "\n\nUn saludo," +
                    "\n COJOEVENTS Team";
            Content content = new Content("text/plain", body);
            Mail mail = new Mail(from, subject, to, content);
            Request request = new Request();
            try{
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sendGrid.api(request);

                System.out.println("==== PRUEBA ENVÍO CORRECTO ====");
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());

            }catch (IOException ex){
                ex.printStackTrace();
            }


        }



       // LoginResponse []responses = restTemplate.getForObject("/api/auth/empleados", LoginResponse[].class);
        //  assert responses != null;
        //System.out.println(responses.length);


    }

    public String exportReport(String reportFormat, HttpServletResponse response) throws IOException, JRException {
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
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=factura.pdf;");
        }


        return "Reporte generado en ruta: " + path;
    }
}

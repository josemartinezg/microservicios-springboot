package com.cojoevents.compraservice.controllers;

import com.cojoevents.compraservice.entities.Producto;
import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.repositories.ProductoRepository;
import com.cojoevents.compraservice.repositories.VentaRepository;
import com.cojoevents.compraservice.responses.ProductoResponse;
import com.cojoevents.compraservice.services.ProductoService;
import com.cojoevents.compraservice.responses.VentaResponse;
import com.cojoevents.compraservice.services.VentaService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class AppController {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    ProductoService productoService;
    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    VentaService ventaService;

    @PostMapping("realizar-venta")
    public void realizarVenta(@RequestBody VentaResponse ventaResponse) throws IOException, JRException {
        ventaService.insertarVenta(ventaResponse);
       // ventaService.enviarEmailConfirmacionVenta(ventaResponse);
           ventaService.exportReport("pdf");

//        Venta vAux = ventaRepository.save(venta);
//        VentaResponse vResponse = new VentaResponse();
//        vResponse.monto = vAux.getMonto();
//        vResponse.producto = producto.getNombreProducto();
//        vResponse.usuario = vAux.getUsuario();
//
//        return vResponse;
    }

    @GetMapping("obtener-productos")
    public ArrayList<ProductoResponse> obtenerProductos(){
        List<Producto> productos = productoRepository.findAll();
        ArrayList<ProductoResponse> misProductos = new ArrayList<>();
        for(Producto prod : productos){
            ProductoResponse pResponse = new ProductoResponse();
            pResponse.costo = prod.getCosto();
            pResponse.nombreProducto = prod.getNombreProducto();
            misProductos.add(pResponse);
        }
        return misProductos;
    }

    @GetMapping("obtener-compras")
    public List<VentaResponse> obtenerCompras(){
        return ventaService.obtenerAllVentas();
    }
    @GetMapping("obtener-compras/{username}")
    public List<VentaResponse> obtenerVentasCliente(@PathVariable String username){
        return ventaService.obtenerVentasByUser(username);
    }
}

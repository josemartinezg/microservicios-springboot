package com.cojoevents.compraservice.controllers;

import com.cojoevents.compraservice.entities.Producto;
import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.repositories.ProductoRepository;
import com.cojoevents.compraservice.repositories.VentaRepository;
import com.cojoevents.compraservice.responses.ProductoResponse;
import com.cojoevents.compraservice.services.ProductoService;
import com.cojoevents.compraservice.responses.VentaResponse;
import com.cojoevents.compraservice.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class ProductoController {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    ProductoService productoService;
    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    VentaService ventaService;
    @PostMapping("realizar-venta")
    public void realizarVenta(@RequestBody VentaResponse ventaResponse){
        ventaService.insertarVenta(ventaResponse);

//        Venta vAux = ventaRepository.save(venta);
//        VentaResponse vResponse = new VentaResponse();
//        vResponse.monto = vAux.getMonto();
//        vResponse.producto = producto.getNombreProducto();
//        vResponse.usuario = vAux.getUsuario();
//
//        return vResponse;
    }

    @GetMapping("obtener-ventas")
    public ArrayList<ProductoResponse> obtenerProductos(){
        productoService.initProducts();
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

}

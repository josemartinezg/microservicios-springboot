package com.cojoevents.compraservice.services;

import com.cojoevents.compraservice.entities.Producto;
import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.repositories.VentaRepository;
import com.cojoevents.compraservice.responses.VentaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VentaService {
    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ProductoService productoService;
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



}

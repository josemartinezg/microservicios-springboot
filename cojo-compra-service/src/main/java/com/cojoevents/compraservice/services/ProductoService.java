package com.cojoevents.compraservice.services;

import com.cojoevents.compraservice.entities.Producto;
import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.repositories.ProductoRepository;
import com.cojoevents.compraservice.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
@Service
public class ProductoService {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    VentaRepository ventaRepository;

    public void initProducts() {
        Producto producto1 = new Producto();
        Producto producto2 = new Producto();
        Producto producto3 = new Producto();
        Producto producto4 = new Producto();

        producto1.setCosto(1000);
        producto1.setNombreProducto("Pre-Boda");

        producto2.setCosto(5000);
        producto2.setNombreProducto("Boda");

        producto3.setCosto(3000);
        producto3.setNombreProducto("Cumpleaños");

        producto4.setCosto(4000);
        producto4.setNombreProducto("Vídeo de evento");

        productoRepository.save(producto1);
        productoRepository.save(producto2);
        productoRepository.save(producto3);
        productoRepository.save(producto4);
    }

    public Venta crearVenta(Producto producto, String codigoUsuario) {

        Date date = new Date();
        Venta venta = new Venta();
        venta.setUsuario(codigoUsuario);
        venta.setMonto(producto.getCosto());
        venta.setProducto(producto);
        venta.setFechaVenta(date);

        ventaRepository.save(venta);

        return venta;
    }

    public Producto getProducto(String nombre){
        if (productoRepository.existsByNombreProducto(nombre)){
            Producto producto = productoRepository.findByNombreProducto(nombre);
            return producto;
        }else{
            Producto producto = new Producto(nombre, 0);
            return producto;
        }
    }

}

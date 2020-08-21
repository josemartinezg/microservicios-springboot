package com.cojoevents.compraservice.services;

import com.cojoevents.compraservice.entities.Producto;
import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.repositories.VentaRepository;
import com.cojoevents.compraservice.responses.VentaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
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
}

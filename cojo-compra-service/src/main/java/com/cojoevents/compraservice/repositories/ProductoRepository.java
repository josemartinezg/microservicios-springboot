package com.cojoevents.compraservice.repositories;

import com.cojoevents.compraservice.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByNombreProducto(String producto);
    Producto findByNombreProducto(String producto);
}

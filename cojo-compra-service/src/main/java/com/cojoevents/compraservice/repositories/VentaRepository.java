package com.cojoevents.compraservice.repositories;

import com.cojoevents.compraservice.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
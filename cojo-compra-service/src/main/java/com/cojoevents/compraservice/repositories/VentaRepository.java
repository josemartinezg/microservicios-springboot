package com.cojoevents.compraservice.repositories;

import com.cojoevents.compraservice.entities.Venta;
import com.cojoevents.compraservice.responses.VentaResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    public List<Venta> findAllByUsuario(String username);
}

package com.cojoevents.compraservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long codigo;
    private float monto;
    private String usuario;
    private Date fechaVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    private Producto producto;
}

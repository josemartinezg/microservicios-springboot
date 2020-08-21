package com.cojoevents.compraservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue
    private Long id;
    private String nombreProducto;
    private float costo;

    @OneToMany(mappedBy = "producto", cascade= CascadeType.ALL)
    private Set<Venta> ventas;

    public Producto(String nombreProducto, float costo) {
        this.costo = costo;
        this.nombreProducto = nombreProducto;
    }
}

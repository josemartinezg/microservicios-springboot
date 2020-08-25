import { Component, OnInit } from '@angular/core';
import { Producto } from 'app/models/producto';
import { ProductoService } from 'app/services/producto.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  productos : Producto[] = [];
  constructor(private productoService : ProductoService) { }

  ngOnInit() {
    return this.productoService
    .obtenerAllProductos()
    .subscribe(productoResponse => this.productos = productoResponse)
  }

}

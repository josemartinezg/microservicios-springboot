import { Component, OnInit } from '@angular/core';
import { Compra } from 'app/models/compra';
import { CompraService } from 'app/services/compra.service';

@Component({
  selector: 'app-table-user-orders',
  templateUrl: './table-user-orders.component.html',
  styleUrls: ['./table-user-orders.component.css']
})
export class TableUserOrdersComponent implements OnInit {
  compras : Compra[] = [];
  constructor(private compraService : CompraService) { }

  ngOnInit() {
    return this.compraService
    .obtenerComprasByUser()
    .subscribe(compraResponse => this.compras = compraResponse)
  }

}

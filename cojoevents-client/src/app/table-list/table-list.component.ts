import { Component, OnInit } from '@angular/core';
import { Compra } from 'app/models/compra';
import { CompraService } from 'app/services/compra.service';
import { data } from 'jquery';

@Component({
  selector: 'app-table-list',
  templateUrl: './table-list.component.html',
  styleUrls: ['./table-list.component.css']
})
export class TableListComponent implements OnInit {
  compras : Compra[] = [];
  constructor(private compraService : CompraService) { }

  ngOnInit() {
    return this.compraService
                .obtenerAllCompras()
                .subscribe(compraResponse => this.compras = compraResponse) 
  }

}

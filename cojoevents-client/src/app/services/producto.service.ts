import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Http } from '@angular/http';
import { Producto } from '../models/producto'
import { Config } from './config';
import { DataService } from './data.service';

@Injectable({
  providedIn: 'root'
})
export class ProductoService extends DataService{
  url = Config.hostCompras;
  constructor(private _http : HttpClient, http : Http) {
    super(Config.hostCompras + 'realizar-venta', http);
   }

  obtenerAllProductos(){
    return this._http.get<Producto[]>(this.url + 'obtener-productos');
  }
}

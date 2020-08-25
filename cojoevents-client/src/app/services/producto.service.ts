import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Producto } from '../models/producto'
import { Config } from './config';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  url = Config.hostCompras + 'obtener-productos';
  constructor(private _http : HttpClient) { }

  obtenerAllProductos(){
    return this._http.get<Producto[]>(this.url);
  }
}

import { Injectable, ɵɵCopyDefinitionFeature } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Compra } from '../models/compra'
import { Config } from './config';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  urlAllCompras = Config.hostCompras + 'obtener-compras';
  urlComprasUser = Config.hostCompras + 'obtener-compras/'
  constructor(private _http : HttpClient) { }

  obtenerAllCompras(){
    return this._http.get<Compra[]>(this.urlAllCompras);
  }

  obtenerComprasByUser(){
    return this._http.get<Compra[]>(this.urlComprasUser + 'Chema')
  }
}

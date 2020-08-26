import { Injectable, ɵɵCopyDefinitionFeature } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Compra } from '../models/compra'
import { Config } from './config';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { BadInput } from '../common/bad-input';
import { AppError } from '../common/app-error';
import { NotFoundError } from '../common/not-found-error';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  urlAllCompras = Config.hostCompras + 'obtener-compras';
  urlComprasUser = Config.hostCompras + 'obtener-compras/'
  urlVenta = Config.hostCompras + 'realizar-venta';
  constructor(private _http : HttpClient) { }

  obtenerAllCompras(){
    return this._http.get<Compra[]>(this.urlAllCompras);
  }

  obtenerComprasByUser(){
    return this._http.get<Compra[]>(this.urlComprasUser + 'Chema')
  }

  realizarCompra(compra : Compra){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        Authorization: 'my-auth-token'
      })
    };
    return this._http.post<Compra>(this.urlVenta, compra ,httpOptions);
  }
  
  public handlerError(error : Response){
    if (error.status === 400) {
      return Observable.throw(new BadInput(error.json()));
    }
    if (error.status === 404) {
      return Observable.throw(new NotFoundError());
    }
    return Observable.throw(new AppError(error));
  }
}


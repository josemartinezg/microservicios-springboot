import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DataService } from './data.service';
import { Http } from '@angular/http';
import { Config } from './config';
import { Usuario } from '../models/usuario';
import { config } from 'process';
@Injectable({
  providedIn: 'root'
})
export class UsuarioService extends DataService{
  url = Config.hostUsuarios;
  constructor( http : Http, private _http : HttpClient) { 
    super(Config.hostUsuarios + 'crear-usuario', http);
  }
  registrarUsuario(usuario : Usuario){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        Authorization: 'my-auth-token'
      })
    };
    return this._http.post<Usuario>(this.url, usuario, httpOptions)
  }
}

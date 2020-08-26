import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../services/usuario.service';
import { Usuario } from '../models/usuario'
@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  
  usuario: Usuario = new Usuario();
  constructor(private usuarioService : UsuarioService) { }

  ngOnInit() {
  }
  guardarUsuario(){
    const usuario = {
      username : this.usuario.username,
      nombre : this.usuario.nombre,
      contrasenna : this.usuario.contrasenna,
      email : this.usuario.email,
      tipoUsuario : this.usuario.tipoUsuario
    }
    this.usuarioService.registrarUsuario(usuario).subscribe(usuarioResponse => {this.usuario = usuarioResponse;});
  }

}

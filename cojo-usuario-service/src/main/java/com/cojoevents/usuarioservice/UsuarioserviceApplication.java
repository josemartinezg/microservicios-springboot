package com.cojoevents.usuarioservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@SpringBootApplication
public class UsuarioserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuarioserviceApplication.class, args);
    }

}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Usuario {
    @Id
    private String username;
    private String nombre;
    private String contrasenna;
    private String email;
    private String tipoUsuario;
}
@Repository
interface UsuarioRepository extends JpaRepository<Usuario, String>{
    public Usuario findByUsername(String username);
}
@Service
class UsuarioService{
    @Autowired
    UsuarioRepository usuarioRepository;
    public void salvarUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }
    public void editarUsuario(String nombre){
        usuarioRepository.findByUsername(nombre);
    }
    public void crearUsuarioAdmin(){
        if(usuarioRepository.count() == 0){
            Usuario admin = new Usuario("admin","Administrador","admin","admin@admin.com","Administrador");
            usuarioRepository.save(admin);
        }


    }
}
@RestController
@RequestMapping("api")
class AppController{
    @Autowired
    UsuarioService usuarioService;


    @PostMapping("crear-usuario")
//    public String crearUsuario(@RequestParam String name, @RequestParam String password,
//                               @RequestParam String mail, @RequestParam String mail)
    public String crearUsuario(@RequestBody Usuario userResponse){
        String password = DigestUtils.md5Hex(userResponse.getContrasenna());
        Usuario usuario = new Usuario(userResponse.getUsername(), userResponse.getNombre(), password,
                userResponse.getEmail(), userResponse.getTipoUsuario());
        usuarioService.salvarUsuario(usuario);
        return "Usuario creado con éxito";
    }

    @RequestMapping("actualizar-usuario")
    public String editarUsuario(@RequestParam String username){
        usuarioService.editarUsuario(username);
        return "Usuario modificado con éxito";
    }
    @Bean
    public CommandLineRunner admin(){
        return (args -> {
            usuarioService.crearUsuarioAdmin();
        });
    }
}

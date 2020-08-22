package com.cojoevents.usuarioservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private String contrasenna;
    private String tipoUsuario;
}
@Repository
interface UsuarioRepository extends JpaRepository<Usuario, String>{

}
@Service
class UsuarioService{
    @Autowired
    UsuarioRepository usuarioRepository;
}
@RestController
@RequestMapping("api")
class AppController{
    @Autowired
    UsuarioService usuarioService;
}

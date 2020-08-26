package com.cojoevents.cojoconfigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class CojoConfigServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CojoConfigServiceApplication.class, args);
    }

}

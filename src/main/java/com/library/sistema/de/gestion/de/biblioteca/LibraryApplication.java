package com.library.sistema.de.gestion.de.biblioteca;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "API Sistema de Gestión de Biblioteca UPTC",
        version = "1.0",
        description = "API REST para gestionar préstamos de material bibliográfico en la biblioteca de la UPTC Seccional Sogamoso",
        contact = @Contact(
            name = "daniel Rodriguez",
            email = "Daniel.rodriguez10@uptc.edu.co",
            url = "https://github.com/"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"
        )
    )
)
public class LibraryApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }
}
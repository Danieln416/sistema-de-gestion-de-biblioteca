package com.example.sistema.de.gestion.de.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.library.sistema.de.gestion.de.biblioteca.LibraryApplication; 

@SpringBootTest(classes = LibraryApplication.class)
class SistemaDeGestionDeBibliotecaApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Cargando el contexto de la aplicación: " + LibraryApplication.class.getName());
    }

}

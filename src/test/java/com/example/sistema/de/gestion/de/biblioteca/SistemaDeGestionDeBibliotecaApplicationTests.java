package com.example.sistema.de.gestion.de.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.library.sistema.de.gestion.de.biblioteca.LibraryApplication; // Mantén este import

@SpringBootTest(classes = LibraryApplication.class)
class SistemaDeGestionDeBibliotecaApplicationTests {

    @Test
    void contextLoads() {
        // Esta línea hace una referencia directa a la clase, haciendo que el import sea "usado".
        // No afecta la funcionalidad del test, solo satisface la advertencia.
        System.out.println("Cargando el contexto de la aplicación: " + LibraryApplication.class.getName());
    }

}

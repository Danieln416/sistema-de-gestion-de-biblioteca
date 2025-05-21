package com.library.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column
    private String author;
    
    @Column
    private String category;
    
    @Column
    private String isbn;
    
    @Column
    private String location;
    
    @Column(nullable = false)
    private boolean available = true;
    
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();
    
    // Método para actualizar la disponibilidad del recurso
    public void updateAvailability() {
        if (loans == null || loans.isEmpty()) {
            this.available = true;
            return;
        }
        
        // Verificar si hay algún préstamo activo (no devuelto)
        for (Loan loan : loans) {
            if (!loan.isReturned()) {
                this.available = false;
                return;
            }
        }
        
        this.available = true;
    }
}
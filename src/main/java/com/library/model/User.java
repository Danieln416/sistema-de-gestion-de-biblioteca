package com.library.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId;
    
    @Column(name = "contact_info")
    private String contactInfo;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();
    
    // Método para obtener préstamos retrasados
    public List<Loan> getOverdueLoans() {
        if (loans == null || loans.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Loan> overdueLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.isOverdue()) {
                overdueLoans.add(loan);
            }
        }
        
        return overdueLoans;
    }
}
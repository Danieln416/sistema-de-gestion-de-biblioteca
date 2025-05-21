package com.library.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "loan_date", nullable = false)
    private LocalDateTime loanDate;
    
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;
    
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    
    @Column(nullable = false)
    private boolean returned = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;
    
    // Método para verificar si el préstamo está retrasado
    public boolean isOverdue() {
        if (returned) {
            return returnDate != null && returnDate.isAfter(dueDate);
        }
        return LocalDateTime.now().isAfter(dueDate);
    }
    
    // Método para calcular la multa por retraso (si aplica)
    public BigDecimal calculateFine() {
        if (!isOverdue()) {
            return BigDecimal.ZERO;
        }
        
        LocalDateTime endDate = returned ? returnDate : LocalDateTime.now();
        long daysLate = ChronoUnit.DAYS.between(dueDate, endDate);
        
        // Multa de $1000 por día de retraso
        return new BigDecimal(daysLate * 1000);
    }
}
package com.library.controller;

import com.library.model.Loan;
import com.library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loans")
@Tag(name = "Préstamos", description = "API para gestionar préstamos de recursos")
public class LoanController {
    
    private final LoanService loanService;
    
    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    
    @GetMapping
    @Operation(summary = "Obtener todos los préstamos")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener préstamo por ID")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener préstamos por usuario")
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUserId(userId));
    }
    
    @GetMapping("/resource/{resourceId}")
    @Operation(summary = "Obtener préstamos por recurso")
    public ResponseEntity<List<Loan>> getLoansByResourceId(@PathVariable Long resourceId) {
        return ResponseEntity.ok(loanService.getLoansByResourceId(resourceId));
    }
    
    @GetMapping("/active")
    @Operation(summary = "Obtener préstamos activos")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }
    
    @GetMapping("/overdue")
    @Operation(summary = "Obtener préstamos vencidos")
    public ResponseEntity<List<Loan>> getOverdueLoans() {
        return ResponseEntity.ok(loanService.getOverdueLoans());
    }
    
    @GetMapping("/overdue/user/{userId}")
    @Operation(summary = "Obtener préstamos vencidos por usuario")
    public ResponseEntity<List<Loan>> getOverdueLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getOverdueLoansByUser(userId));
    }
    
    @GetMapping("/daterange")
    @Operation(summary = "Obtener préstamos por rango de fechas")
    public ResponseEntity<List<Loan>> getLoansByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(loanService.getLoansByDateRange(startDate, endDate));
    }
    
    @PostMapping
    @Operation(summary = "Crear un nuevo préstamo")
    public ResponseEntity<?> createLoan(@RequestBody Map<String, Object> loanRequest) {
        try {
            Long userId = Long.valueOf(loanRequest.get("userId").toString());
            Long resourceId = Long.valueOf(loanRequest.get("resourceId").toString());
            
            // Obtener la fecha de vencimiento del préstamo (por defecto, 15 días)
            LocalDateTime dueDate;
            if (loanRequest.containsKey("dueDate")) {
                dueDate = LocalDateTime.parse(loanRequest.get("dueDate").toString());
            } else {
                dueDate = LocalDateTime.now().plusDays(15);
            }
            
            Loan loan = loanService.createLoan(userId, resourceId, dueDate);
            return new ResponseEntity<>(loan, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/return/{id}")
    @Operation(summary = "Registrar devolución de un préstamo")
    public ResponseEntity<?> returnLoan(@PathVariable Long id) {
        try {
            Loan loan = loanService.returnLoan(id);
            return ResponseEntity.ok(loan);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un préstamo existente")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        return loanService.getLoanById(id)
                .map(existingLoan -> {
                    loan.setId(id);
                    return ResponseEntity.ok(loanService.saveLoan(loan));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un préstamo")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        return loanService.getLoanById(id)
                .map(loan -> {
                    loanService.deleteLoan(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
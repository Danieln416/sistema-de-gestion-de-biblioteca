package com.library.service;

import com.library.model.Loan;
import com.library.model.Resource;
import com.library.model.User;
import com.library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    
    private final LoanRepository loanRepository;
    private final UserService userService;
    private final ResourceService resourceService;
    
    @Autowired
    public LoanService(LoanRepository loanRepository, UserService userService, ResourceService resourceService) {
        this.loanRepository = loanRepository;
        this.userService = userService;
        this.resourceService = resourceService;
    }
    
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    
    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }
    
    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }
    
    public List<Loan> getLoansByResourceId(Long resourceId) {
        return loanRepository.findByResourceId(resourceId);
    }
    
    public List<Loan> getActiveLoans() {
        return loanRepository.findByReturnedFalse();
    }
    
    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans();
    }
    
    public List<Loan> getOverdueLoansByUser(Long userId) {
        return loanRepository.findOverdueLoansByUser(userId);
    }
    
    public List<Loan> getLoansByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return loanRepository.findByDateRange(startDate, endDate);
    }
    
    @Transactional
    public Loan createLoan(Long userId, Long resourceId, LocalDateTime dueDate) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        
        Optional<Resource> resourceOptional = resourceService.getResourceById(resourceId);
        if (!resourceOptional.isPresent()) {
            throw new IllegalArgumentException("Recurso no encontrado");
        }
        
        User user = userOptional.get();
        Resource resource = resourceOptional.get();
        
        if (!resource.isAvailable()) {
            throw new IllegalStateException("El recurso no está disponible para préstamo");
        }
        
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setResource(resource);
        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(dueDate);
        loan.setReturned(false);
        
        // Actualizar disponibilidad del recurso
        resource.setAvailable(false);
        resourceService.saveResource(resource);
        
        return loanRepository.save(loan);
    }
    
    @Transactional
    public Loan returnLoan(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        
        if (!loanOptional.isPresent()) {
            throw new IllegalArgumentException("Préstamo no encontrado");
        }
        
        Loan loan = loanOptional.get();
        
        if (loan.isReturned()) {
            throw new IllegalStateException("El préstamo ya ha sido devuelto");
        }
        
        loan.setReturned(true);
        loan.setReturnDate(LocalDateTime.now());
        
        // Actualizar disponibilidad del recurso
        Resource resource = loan.getResource();
        resource.setAvailable(true);
        resourceService.saveResource(resource);
        
        return loanRepository.save(loan);
    }
    
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }
    
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
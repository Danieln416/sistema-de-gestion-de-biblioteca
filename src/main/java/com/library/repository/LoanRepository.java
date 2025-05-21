package com.library.repository;

import com.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    List<Loan> findByUserId(Long userId);
    
    List<Loan> findByResourceId(Long resourceId);
    
    List<Loan> findByReturnedFalse();
    
    @Query("SELECT l FROM Loan l WHERE l.returned = false AND l.dueDate < CURRENT_TIMESTAMP")
    List<Loan> findOverdueLoans();
    
    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId AND l.returned = false AND l.dueDate < CURRENT_TIMESTAMP")
    List<Loan> findOverdueLoansByUser(@Param("userId") Long userId);
    
    @Query("SELECT l FROM Loan l WHERE " + "(l.loanDate BETWEEN :startDate AND :endDate) OR " + "(l.returnDate BETWEEN :startDate AND :endDate)")
    List<Loan> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
package com.library.repository;

import com.library.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    
    List<Resource> findByTitleContainingIgnoreCase(String title);
    
    List<Resource> findByAuthorContainingIgnoreCase(String author);
    
    List<Resource> findByCategoryContainingIgnoreCase(String category);
    
    List<Resource> findByIsbnContaining(String isbn);
    
    List<Resource> findByAvailable(boolean available);
    
    @Query("SELECT r FROM Resource r WHERE " +
           "(:title IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:author IS NULL OR LOWER(r.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
           "(:category IS NULL OR LOWER(r.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
           "(:isbn IS NULL OR r.isbn LIKE CONCAT('%', :isbn, '%'))")
    List<Resource> findByMultipleParameters(@Param("title") String title,
                                           @Param("author") String author,
                                           @Param("category") String category,
                                           @Param("isbn") String isbn);
}
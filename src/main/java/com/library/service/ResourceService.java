package com.library.service;

import com.library.model.Resource;
import com.library.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    
    private final ResourceRepository resourceRepository;
    
    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }
    
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
    
    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }
    
    public List<Resource> getResourcesByTitle(String title) {
        return resourceRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<Resource> getResourcesByAuthor(String author) {
        return resourceRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    public List<Resource> getResourcesByCategory(String category) {
        return resourceRepository.findByCategoryContainingIgnoreCase(category);
    }
    
    public List<Resource> getResourcesByIsbn(String isbn) {
        return resourceRepository.findByIsbnContaining(isbn);
    }
    
    public List<Resource> getAvailableResources() {
        return resourceRepository.findByAvailable(true);
    }
    
    public List<Resource> searchResources(String title, String author, String category, String isbn) {
        return resourceRepository.findByMultipleParameters(title, author, category, isbn);
    }
    
    public Resource saveResource(Resource resource) {
        return resourceRepository.save(resource);
    }
    
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }
}
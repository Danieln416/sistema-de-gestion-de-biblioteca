package com.library.controller;

import com.library.model.Resource;
import com.library.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
@Tag(name = "Recursos", description = "API para gestionar recursos bibliográficos")
public class ResourceController {
    
    private final ResourceService resourceService;
    
    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    @GetMapping
    @Operation(summary = "Obtener todos los recursos")
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener recurso por ID")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        return resourceService.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    @Operation(summary = "Buscar recursos por múltiples criterios")
    public ResponseEntity<List<Resource>> searchResources(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String isbn) {
        return ResponseEntity.ok(resourceService.searchResources(title, author, category, isbn));
    }
    
    @GetMapping("/title/{title}")
    @Operation(summary = "Buscar recursos por título")
    public ResponseEntity<List<Resource>> getResourcesByTitle(@PathVariable String title) {
        return ResponseEntity.ok(resourceService.getResourcesByTitle(title));
    }
    
    @GetMapping("/author/{author}")
    @Operation(summary = "Buscar recursos por autor")
    public ResponseEntity<List<Resource>> getResourcesByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(resourceService.getResourcesByAuthor(author));
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "Buscar recursos por categoría")
    public ResponseEntity<List<Resource>> getResourcesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(resourceService.getResourcesByCategory(category));
    }
    
    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Buscar recursos por ISBN")
    public ResponseEntity<List<Resource>> getResourcesByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(resourceService.getResourcesByIsbn(isbn));
    }
    
    @GetMapping("/available")
    @Operation(summary = "Obtener recursos disponibles")
    public ResponseEntity<List<Resource>> getAvailableResources() {
        return ResponseEntity.ok(resourceService.getAvailableResources());
    }
    
    @PostMapping
    @Operation(summary = "Crear un nuevo recurso")
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {
        return new ResponseEntity<>(resourceService.saveResource(resource), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un recurso existente")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody Resource resource) {
        return resourceService.getResourceById(id)
                .map(existingResource -> {
                    resource.setId(id);
                    return ResponseEntity.ok(resourceService.saveResource(resource));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un recurso")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        return resourceService.getResourceById(id)
                .map(resource -> {
                    resourceService.deleteResource(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
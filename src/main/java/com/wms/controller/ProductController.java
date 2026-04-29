package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wms.entity.Product;
import com.wms.repository.ProductRepository;
import com.wms.service.ProductService; // ✅ IMPORT

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private ProductService service; 

    @PostMapping
    public Product addProduct(@RequestBody Product p) {

        return service.addProduct(p);
    }

    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }
 // GET single product
    @GetMapping("/{id}")
    public Product getOne(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
 // UPDATE product
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product p) {

        Product existing = repo.findById(id).orElseThrow();

        existing.setName(p.getName());
        existing.setSku(p.getSku());

        return repo.save(existing);
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "Deleted";
    }
}
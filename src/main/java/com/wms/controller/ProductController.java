package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wms.entity.Product;
import com.wms.repository.ProductRepository;
import com.wms.service.ProductService;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private ProductService service;

    // ➕ ADD PRODUCT (ADMIN only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product addProduct(@RequestBody Product p) {
        return service.addProduct(p);
    }

    // 📄 GET ALL PRODUCTS (logged-in users)
    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    // 🔍 GET SINGLE PRODUCT
    @GetMapping("/{id}")
    public Product getOne(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // ✏️ UPDATE PRODUCT (ADMIN only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product update(@PathVariable Long id, @RequestBody Product p) {

        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(p.getName());
        existing.setSku(p.getSku());
        existing.setQuantity(p.getQuantity());

        return repo.save(existing);
    }

    // ❌ DELETE PRODUCT (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "Deleted Successfully";
    }
}
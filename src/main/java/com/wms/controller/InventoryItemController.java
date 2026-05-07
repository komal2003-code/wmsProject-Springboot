package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wms.entity.InventoryItem;
import com.wms.repository.InventoryItemRepository;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")   // ⭐ /api prefix add कर
public class InventoryItemController {

    @Autowired
    private InventoryItemRepository repo;

    // 👀 VIEW → ADMIN + OPERATOR
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping
    public List<InventoryItem> getAll() {
        return repo.findAll();
    }

    // 👀 VIEW BY ID → ADMIN + OPERATOR
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping("/{id}")
    public InventoryItem getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    // ✏ UPDATE → ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public InventoryItem update(@PathVariable Long id, @RequestBody InventoryItem i) {
        InventoryItem existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existing.setQuantity(i.getQuantity());
        return repo.save(existing);
    }

    // ❌ DELETE → ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "Deleted successfully";
    }
}
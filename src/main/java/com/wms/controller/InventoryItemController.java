//crud logic
package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wms.entity.InventoryItem;
import com.wms.repository.InventoryItemRepository;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryItemController {

    @Autowired
    private InventoryItemRepository repo;

    @GetMapping
    public List<InventoryItem> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public InventoryItem getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @PutMapping("/{id}")
    public InventoryItem update(@PathVariable Long id, @RequestBody InventoryItem i) {
        InventoryItem existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existing.setQuantity(i.getQuantity());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "Deleted successfully";
    }
}
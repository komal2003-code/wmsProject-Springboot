package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wms.entity.StorageBin;
import com.wms.repository.StorageBinRepository;

import java.util.List;
@RestController
@RequestMapping("/bins")
public class StorageBinController {

    @Autowired
    private StorageBinRepository repo;

    @PostMapping
    public StorageBin create(@RequestBody StorageBin b) {
        return repo.save(b);
    }

    @GetMapping
    public List<StorageBin> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public StorageBin getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public StorageBin update(@PathVariable Long id, @RequestBody StorageBin b) {
    	StorageBin existing = repo.findById(id)
    	        .orElseThrow(() -> new RuntimeException("StorageBin not found"));
        if (existing != null) {
            existing.setBinCode(b.getBinCode());
            return repo.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "Deleted";
    }
}
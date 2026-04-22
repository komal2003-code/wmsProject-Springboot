package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wms.entity.Warehouse;
import com.wms.repository.WarehouseRepository;

import java.util.List;
@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseRepository repo;

    @PostMapping
    public Warehouse create(@RequestBody Warehouse w) {
        return repo.save(w);
    }

    @GetMapping
    public List<Warehouse> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Warehouse getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Warehouse update(@PathVariable Long id, @RequestBody Warehouse w) {
        Warehouse existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(w.getName());
            existing.setLocation(w.getLocation()); 
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

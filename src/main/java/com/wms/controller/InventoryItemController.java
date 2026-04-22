package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wms.dto.InventoryRequest;
import com.wms.entity.InventoryItem;
import com.wms.entity.Product;
import com.wms.entity.StorageBin;
import com.wms.repository.InventoryItemRepository;
import com.wms.repository.ProductRepository;
import com.wms.repository.StorageBinRepository;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryItemController {

    @Autowired
    private InventoryItemRepository repo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StorageBinRepository storageBinRepository;

    @PostMapping
    public InventoryItem create(@RequestBody InventoryRequest req) {

        Product p = productRepository.findById(req.productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        StorageBin b = storageBinRepository.findById(req.storageBinId)
                .orElseThrow(() -> new RuntimeException("StorageBin not found"));

        InventoryItem i = new InventoryItem();
        i.setQuantity(req.quantity);
        i.setProduct(p);
        i.setStorageBin(b);

        return repo.save(i);
    }
    
    @GetMapping
    public List<InventoryItem> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public InventoryItem getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public InventoryItem update(@PathVariable Long id, @RequestBody InventoryItem i) {
        InventoryItem existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setQuantity(i.getQuantity());
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
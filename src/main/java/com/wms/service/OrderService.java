package com.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wms.entity.InventoryItem;
import com.wms.entity.Product;
import com.wms.exception.InsufficientStockException;
import com.wms.repository.InventoryItemRepository;

@Service
public class OrderService {

    @Autowired
    private InventoryItemRepository inventoryRepo;

    @Transactional
    public String processOrder(Product product, int qty) {

    	InventoryItem inventory = inventoryRepo
    	        .findByProduct(product)
    	        .orElseThrow(() -> new RuntimeException("Inventory not found"));

        // 🔥 stock check
        if (inventory.getQuantity() < qty) {
            throw new InsufficientStockException("Stock not available");
        }

        // 🔥 reduce stock
        inventory.setQuantity(inventory.getQuantity() - qty);

        inventoryRepo.save(inventory);

        return "Order Packed Successfully";
    }
}
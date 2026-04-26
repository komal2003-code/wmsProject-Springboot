package com.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wms.entity.InventoryItem;
import com.wms.entity.Product;
import com.wms.exception.InsufficientStockException;
import com.wms.repository.InventoryItemRepository;
import com.wms.repository.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private InventoryItemRepository inventoryRepo;

    @Autowired
    private ProductRepository productRepo;

    @Transactional
    public String processOrder(Long productId, int qty) {

        // ✅ Product fetch
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ Inventory fetch
        InventoryItem inventory = inventoryRepo.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        // ✅ Stock check
        if (inventory.getQuantity() < qty) {
            throw new InsufficientStockException("Stock not available");
        }

        // ✅ Reduce stock
        inventory.setQuantity(inventory.getQuantity() - qty);

        inventoryRepo.save(inventory);

        return "Order Packed Successfully";
    }
}
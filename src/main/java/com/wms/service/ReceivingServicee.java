package com.wms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wms.dto.ReceivingRequest;
import com.wms.entity.InventoryItem;
import com.wms.entity.Product;
import com.wms.entity.StorageBin;
import com.wms.repository.InventoryItemRepository;
import com.wms.repository.ProductRepository;
import com.wms.repository.StorageBinRepository;

@Service
public class ReceivingServicee {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StorageBinRepository storageBinRepository;

    @Autowired
    private InventoryItemRepository inventoryRepository;

    @Transactional
    public String receiveStock(ReceivingRequest request) {

        // ✅ 1. Get product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ 2. Get bin (temporary fixed bin)
        StorageBin bin = storageBinRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("No bin available"));

        // ✅ 3. Capacity check
        if (bin.getUsed() + request.getQuantity() > bin.getCapacity()) {
            throw new RuntimeException("Bin capacity exceeded");
        }

        // ✅ 4. Check if inventory already exists
        Optional<InventoryItem> existing =
                inventoryRepository.findByProduct(product);

        if (existing.isPresent()) {
            InventoryItem item = existing.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            inventoryRepository.save(item);
        } else {
            InventoryItem item = new InventoryItem();
            item.setProduct(product);
            item.setStorageBin(bin);
            item.setQuantity(request.getQuantity());
            inventoryRepository.save(item);
        }

        // ✅ 5. Update bin usage
        bin.setUsed(bin.getUsed() + request.getQuantity());
        storageBinRepository.save(bin);

        return "Stock stored successfully!";
    }
}
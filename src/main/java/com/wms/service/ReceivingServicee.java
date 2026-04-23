
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

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

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

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        StorageBin bin = storageBinRepository
                .findFirstByCapacityGreaterThanEqual(request.getQuantity())
                .orElseThrow(() -> new RuntimeException("No bin available"));

        Optional<InventoryItem> existing =
                inventoryRepository.findByProductAndStorageBin(product, bin);

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

        return "Stock stored successfully!";
    }
}

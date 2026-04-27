package com.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.Product;
import com.wms.entity.InventoryItem;
import com.wms.repository.ProductRepository;
import com.wms.repository.InventoryItemRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private BarcodeService barcodeService;

    @Autowired
    private InventoryItemRepository inventoryRepo;

    // ADD PRODUCT WITH QR CODE + INVENTORY
    public Product addProduct(Product product) {

        // 1. null check
        if (product == null) {
            throw new RuntimeException("Product cannot be null");
        }

        // 2. SKU validation
        if (product.getSku() == null || product.getSku().isEmpty()) {
            throw new RuntimeException("SKU cannot be null");
        }

        // 3. generate QR
        String path = barcodeService.generateQR(product.getSku());
        product.setBarcodePath(path);

        // 4. save product
        Product savedProduct = repo.save(product);

        // 5. AUTO create inventory (🔥 IMPORTANT FOR WMS)
        InventoryItem inventory = new InventoryItem();
        inventory.setProduct(savedProduct);
        inventory.setQuantity(savedProduct.getQuantity());

        inventoryRepo.save(inventory);

        return savedProduct;
    }
}
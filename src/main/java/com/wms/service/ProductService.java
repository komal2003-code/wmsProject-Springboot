package com.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wms.entity.Product;
import com.wms.entity.InventoryItem;
import com.wms.repository.ProductRepository;
import com.wms.repository.InventoryItemRepository;
import java.util.List;
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

        // 5. AUTO create inventory
        InventoryItem inventory = new InventoryItem();
        inventory.setProduct(savedProduct);
        inventory.setQuantity(savedProduct.getQuantity());

        inventoryRepo.save(inventory);

        return savedProduct;
    }

    // ✅ DELETE PRODUCT (FIXED)
    @Transactional
    public void deleteProduct(Long id) {

        // Step 1: delete inventory first (child)
        inventoryRepo.deleteByProductId(id);

       

        // Step 3: delete product (parent)
        repo.deleteById(id);
    }
    public List<Product> getAllProducts() {
        return repo.findAll();
    }
}
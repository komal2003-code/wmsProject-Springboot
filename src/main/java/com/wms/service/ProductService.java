package com.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.Product;
import com.wms.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private BarcodeService barcodeService;

    // 🔥 ADD PRODUCT WITH QR CODE
    public Product addProduct(Product product) {

        // ✅ validation (optional but recommended)
        if (product.getSku() == null || product.getSku().isEmpty()) {
            throw new RuntimeException("SKU cannot be null");
        }

        // ✅ generate QR code
        String path = barcodeService.generateQR(product.getSku());

        // ✅ set path in DB
        product.setBarcodePath(path);

        // ✅ save in DB
        return repo.save(product);
    }
}

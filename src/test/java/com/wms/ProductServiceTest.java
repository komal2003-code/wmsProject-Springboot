
package com.wms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.wms.entity.Product;
import com.wms.repository.ProductRepository;
import com.wms.service.ProductService;

import com.wms.service.BarcodeService;
import com.wms.repository.InventoryItemRepository;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repo;

    @Mock
    private BarcodeService barcodeService;

    @Mock
    private InventoryItemRepository inventoryRepo;
    @InjectMocks
    private ProductService service;

    @Test
    public void testGetAllProducts() {

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Laptop");
        p1.setQuantity(10);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Mouse");
        p2.setQuantity(5);

        when(repo.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> products = service.getAllProducts();

        assertEquals(2, products.size());
    }
    @Test
    public void testSaveProduct() {

        Product p = new Product();
        p.setName("Keyboard");
        p.setSku("KB101");
        p.setQuantity(5);

        when(barcodeService.generateQR("KB101"))
                .thenReturn("barcode.png");

        when(repo.save(p)).thenReturn(p);

        Product saved = service.addProduct(p);

        assertEquals("Keyboard", saved.getName());
    }
    @Test
    public void testDeleteProduct() {

        Long id = 1L;

        service.deleteProduct(id);

        verify(inventoryRepo).deleteByProductId(id);

        verify(repo).deleteById(id);
    }
}
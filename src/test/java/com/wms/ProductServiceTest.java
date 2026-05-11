
package com.wms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.wms.entity.Product;
import com.wms.repository.ProductRepository;
import com.wms.service.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repo;

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
}
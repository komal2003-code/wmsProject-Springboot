package com.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wms.dto.OrderRequest;
import com.wms.entity.InventoryItem;
import com.wms.entity.Product;
import com.wms.entity.Orders;
import com.wms.entity.OrderStatus;
import com.wms.exception.InsufficientStockException;
import com.wms.repository.InventoryItemRepository;
import com.wms.repository.ProductRepository;
import com.wms.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private InventoryItemRepository inventoryRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrderRepository orderRepository;

    // 🔥 CREATE + PROCESS ORDER
    @Transactional
    public String processOrder(OrderRequest request) {

        // 1. Product fetch
        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        System.out.println("Incoming productId = " + product.getId());

        // 2. Inventory fetch
        InventoryItem inventory = inventoryRepo.findByProduct_Id(product.getId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        System.out.println("Inventory found for product = " + product.getId());

        // 3. Stock check
        if (inventory.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Stock not available");
        }

        // 4. Reduce stock
        inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
        inventoryRepo.save(inventory);

        // 5. Create Order
        Orders order = new Orders();
        order.setProductId(product.getId());
        order.setQuantity(request.getQuantity());
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);

        return "Order Created & Packed Successfully";
    }

    // 🔥 UPDATE ORDER STATUS
    public Orders updateStatus(Long id, OrderStatus status) {

        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        return orderRepository.save(order);
    }
}
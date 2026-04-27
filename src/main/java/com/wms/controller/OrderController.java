package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wms.dto.OrderRequest;
import com.wms.entity.Orders;
import com.wms.entity.OrderStatus;
import com.wms.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 🔥 CREATE ORDER (with stock check + inventory update)
    @PostMapping
    public String createOrder(@RequestBody OrderRequest request) {
        return orderService.processOrder(request);
    }

    // 🔥 UPDATE ORDER STATUS
    @PutMapping("/{id}/status")
    public Orders updateStatus(@PathVariable Long id,
                               @RequestParam OrderStatus status) {
        return orderService.updateStatus(id, status);
    }
}
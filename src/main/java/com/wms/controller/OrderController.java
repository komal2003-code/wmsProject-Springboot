package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import com.wms.entity.Product;
import com.wms.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/pack")
    public String packOrder(@RequestParam Long productId,
                            @RequestParam int qty) {
        return service.processOrder(productId, qty);
    }
}
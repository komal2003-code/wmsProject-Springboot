//receving logic - stock receive
package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wms.dto.ReceivingRequest;
import com.wms.service.ReceivingServicee;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/receive")
public class ReceivingController {

    @Autowired
    private ReceivingServicee receivingService;

    @PostMapping
    public Map<String, Object> receive(@RequestBody ReceivingRequest req) {
        
        String message = receivingService.receiveStock(req);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", message);
        response.put("quantity", req.getQuantity());

        return response;
    }
}
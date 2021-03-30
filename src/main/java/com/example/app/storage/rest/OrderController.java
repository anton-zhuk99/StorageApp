package com.example.app.storage.rest;

import com.example.app.storage.dto.OrderDto;
import com.example.app.storage.model.Order;
import com.example.app.storage.model.OrderEntry;
import com.example.app.storage.service.ConsumerService;
import com.example.app.storage.service.OrderService;
import com.example.app.storage.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping(value = "api/v1/order")
public class OrderController {

    private ConsumerService consumerService;
    private OrderService orderService;
    private ProductService productService;

    @PostMapping(value = "/publish")
    public ResponseEntity<Map<String, String>> publishOrder(@RequestBody OrderDto dto) {
        Lock lock = new ReentrantLock();
        Order order = null;
        lock.lock();
        try {
            order = orderService.add(dto);
        } finally {
            lock.unlock();
        }
        Map<String, String> body = new HashMap<>();
        if (order == null) {
            body.put("message", "Couldn't publish the order");
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        body.put("orderId", order.getId().toString());
        body.put("description", order.getDescription());
        body.put("status", order.getStatus().toString());
        StringBuffer sb = new StringBuffer("[");
        final String template = "{ \"product\" : \"%s\", \"amount\" : %s }";
        for (OrderEntry entry: order.getEntries()) {
            sb.append(String.format(template, entry.getProduct().getDescription(), entry.getAmount().toString()));
            sb.append(", ");
        }
        sb.substring(0, sb.lastIndexOf(","));
        sb.append("]");
        body.put("list", sb.toString());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}

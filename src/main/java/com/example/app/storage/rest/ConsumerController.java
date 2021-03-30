package com.example.app.storage.rest;

import com.example.app.storage.dto.ConsumerDto;
import com.example.app.storage.model.*;
import com.example.app.storage.service.ConsumerService;
import com.example.app.storage.service.OrderService;
import com.example.app.storage.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/consumer/")
public class ConsumerController {

    private ConsumerService consumerService;
    private OrderService orderService;
    private ProductService productService;

    @PostMapping(value = "add")
    public ResponseEntity<Map<String, String>> addNewConsumer(@RequestBody ConsumerDto dto) {
        Consumer consumer = consumerService.add(dto);
        Map<String, String> body = new HashMap<>();
        body.put("name", consumer.getName());
        body.put("district", consumer.getDistrict().toString());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<ConsumerDto>> allConsumers() {
        List<ConsumerDto> dtoList = consumerService.getConsumers();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
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

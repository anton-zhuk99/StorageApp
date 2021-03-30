package com.example.app.storage.rest;

import com.example.app.storage.dto.ProductDto;
import com.example.app.storage.model.Product;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/product/")
public class ProductController {

    private ConsumerService consumerService;
    private OrderService orderService;
    private ProductService productService;

    @PostMapping(value = "add")
    public ResponseEntity<Map<String, String>> addProduct(@RequestBody ProductDto dto) {
        Product product = productService.add(dto);
        Map<String, String> body = new HashMap<>();
        body.put("id", product.getId().toString());
        body.put("description", product.getDescription());
        body.put("price", product.getPrice().toString());
        body.put("amount", product.getAmount().toString());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<ProductDto>> listProducts() {
        List<ProductDto> body = productService.getAll().stream()
                .map(p -> productService.map(p))
                .collect(Collectors.toList());
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

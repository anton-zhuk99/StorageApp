package com.example.app.storage.service.impl;

import com.example.app.storage.dao.ConsumerDao;
import com.example.app.storage.dao.OrderDao;
import com.example.app.storage.dao.OrderEntryDao;
import com.example.app.storage.dao.ProductDao;
import com.example.app.storage.dto.ProductDto;
import com.example.app.storage.exception.MultipleResultException;
import com.example.app.storage.model.Product;
import com.example.app.storage.service.ConsumerService;
import com.example.app.storage.service.OrderService;
import com.example.app.storage.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private OrderDao orderDao;
    private ConsumerDao consumerDao;
    private ProductDao productDao;
    private OrderEntryDao orderEntryDao;

    private ConsumerService consumerService;
    private OrderService orderService;

    @Override
    public Product add(ProductDto dto) {
        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setAmount(dto.getAmount());
        product.setPrice(dto.getPrice());
        Long id = productDao.save(product);
        return product;
    }

    @Override
    public List<Product> getAll() {
        return productDao.list();
    }

    @Override
    public Product getById(Long id) {
        return productDao.get(id);
    }

    @Override
    public void change(Product obj) {
        productDao.update(obj);
    }

    @Override
    public void deleteById(Long id) {
        this.delete(this.getById(id));
    }

    @Override
    public void delete(Product obj) {
        productDao.delete(obj);
    }

    @Override
    public ProductDto map(Product obj) {
        ProductDto dto = new ProductDto();
        dto.setId(obj.getId());
        dto.setPrice(obj.getPrice());
        dto.setAmount(obj.getAmount());
        dto.setDescription(obj.getDescription());
        return dto;
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
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setConsumerDao(ConsumerDao consumerDao) {
        this.consumerDao = consumerDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setOrderEntryDao(OrderEntryDao orderEntryDao) {
        this.orderEntryDao = orderEntryDao;
    }
}

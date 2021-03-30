package com.example.app.storage.service.impl;

import com.example.app.storage.dao.ConsumerDao;
import com.example.app.storage.dao.OrderDao;
import com.example.app.storage.dao.OrderEntryDao;
import com.example.app.storage.dao.ProductDao;
import com.example.app.storage.dto.ConsumerDto;
import com.example.app.storage.model.Consumer;
import com.example.app.storage.model.District;
import com.example.app.storage.service.ConsumerService;
import com.example.app.storage.service.OrderService;
import com.example.app.storage.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private OrderDao orderDao;
    private ConsumerDao consumerDao;
    private ProductDao productDao;
    private OrderEntryDao orderEntryDao;

    private OrderService orderService;
    private ProductService productService;

    @Override
    public Consumer add(ConsumerDto dto) {
        Consumer consumer = new Consumer();
        consumer.setName(dto.getName());
        consumer.setDistrict(District.getByFullName(dto.getDistrict()));
        consumerDao.save(consumer);
        return consumer;
    }

    @Override
    public List<Consumer> getAll() {
        return consumerDao.list();
    }

    @Override
    public Consumer getById(Long id) {
        return consumerDao.get(id);
    }

    @Override
    public void change(Consumer obj) {
        consumerDao.update(obj);
    }

    @Override
    public void deleteById(Long id) {
        delete(consumerDao.get(id));
    }

    @Override
    public void delete(Consumer obj) {
        consumerDao.delete(obj);
    }

    @Override
    public List<ConsumerDto> getConsumers() {
        return consumerDao.list().stream()
                .map(consumer -> {
                    ConsumerDto dto = new ConsumerDto();
                    dto.setName(consumer.getName());
                    dto.setDistrict(consumer.getDistrict().toString());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ConsumerDto map(Consumer obj) {
        ConsumerDto dto = new ConsumerDto();
        dto.setName(obj.getName());
        dto.setDistrict(obj.getDistrict().toString());
        return dto;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setConsumerDao(ConsumerDao consumerDao) {
        this.consumerDao = consumerDao;
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
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

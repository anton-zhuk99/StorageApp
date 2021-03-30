package com.example.app.storage.service;

import com.example.app.storage.dto.OrderDto;
import com.example.app.storage.model.Order;

import java.util.List;

public interface OrderService extends GenericService<Order, OrderDto, Long> {

    Long triggerOrderProcessing();

}

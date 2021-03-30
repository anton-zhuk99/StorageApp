package com.example.app.storage.service.impl;

import com.example.app.storage.dao.ConsumerDao;
import com.example.app.storage.dao.OrderDao;
import com.example.app.storage.dao.OrderEntryDao;
import com.example.app.storage.dao.ProductDao;
import com.example.app.storage.dto.OrderDto;
import com.example.app.storage.dto.OrderEntryDto;
import com.example.app.storage.model.Order;
import com.example.app.storage.model.OrderEntry;
import com.example.app.storage.model.OrderStatus;
import com.example.app.storage.model.Product;
import com.example.app.storage.service.ConsumerService;
import com.example.app.storage.service.OrderService;
import com.example.app.storage.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDao orderDao;
    private ConsumerDao consumerDao;
    private ProductDao productDao;
    private OrderEntryDao orderEntryDao;

    private ConsumerService consumerService;
    private ProductService productService;

    @Override
    public Order add(OrderDto dto) {
        Order order = new Order();
        order.setOrderDate(Date.valueOf(dto.getDate()));
        order.setConsumer(consumerDao.get(dto.getConsumerId()));
        order.setDescription(dto.getDescription());
        order.setStatus(OrderStatus.WAITING);
        Long id = orderDao.save(order);
        publishEntries(convert(dto.getEntries(), id));
        return order;
    }

    private List<OrderEntry> convert(List<OrderEntryDto> dtoList, Long orderId) {
        List<OrderEntry> entries = new ArrayList<>();
        for (OrderEntryDto dto: dtoList) {
            OrderEntry entry = new OrderEntry();
            entry.setOrder(orderDao.get(orderId));
            entry.setProduct(productDao.get(dto.getProductId()));
            entry.setAmount(dto.getAmount());
            entry.setStatus(OrderStatus.WAITING);
            entries.add(entry);
        }
        return entries;
    }

    private void publishEntries(List<OrderEntry> entries) {
        for (OrderEntry entry: entries) {
            orderEntryDao.save(entry);
        }
    }

    @Override
    public List<Order> getAll() {
        return orderDao.list();
    }

    @Override
    public Order getById(Long id) {
        return orderDao.get(id);
    }

    @Override
    public void change(Order obj) {
        orderDao.update(obj);
    }

    @Override
    public void deleteById(Long id) {
        this.delete(this.getById(id));
    }

    @Override
    public void delete(Order obj) {
        orderDao.delete(obj);
    }

    @Override
    public OrderDto map(Order obj) {
        OrderDto dto = new OrderDto();
        dto.setDescription(obj.getDescription());
        dto.setConsumerId(obj.getId());
        dto.setDate(obj.getOrderDate().toString());
        dto.setEntries(this.reverseConvert(obj.getEntries()));
        return dto;
    }

    private List<OrderEntryDto> reverseConvert(List<OrderEntry> entries) {
        List<OrderEntryDto> dtoList = new ArrayList<>();
        for (OrderEntry entry: entries) {
            OrderEntryDto dto = new OrderEntryDto();
            dto.setProductId(entry.getProduct().getId());
            dto.setAmount(entry.getAmount());
            dtoList.add(dto);
        }
        return dtoList;
    }

    private Order getOldestOrder() {
        Order order;
        try {
            order = this.getAll().stream()
                    .filter(o -> o.getStatus() == OrderStatus.WAITING)
                    .sorted((o1, o2) -> {
                        if (o1.getOrderDate().before(o2.getOrderDate())) {
                            return 1;
                        } else if (o1.getOrderDate().equals(o2.getOrderDate())) {
                            return 0;
                        } else {
                            return -1;
                        }
                    })
                    .limit(1L)
                    .collect(Collectors.toList()).get(0);
        } catch (NullPointerException e) {
            logger.info("CAPTURING NPE! NO WAITING ORDERS AVAILABLE");
            return null;
        }
        return order;
    }

    private void process(Order order) {
        for (OrderEntry entry: order.getEntries()) {
            this.processEntry(entry);
        }
        this.updateStatus(order);
        if (order.getStatus() == OrderStatus.PROCESSED) {
            logger.info("Order No " + order.getId() + " STATUS: PROCESSED");
        } else {
            logger.info("Order No " + order.getId() + " STATUS: DECLINED");
        }
        this.change(order);
    }

    private void updateStatus(Order order) {
        for (OrderEntry entry: order.getEntries()) {
            if (entry.getStatus() != OrderStatus.PROCESSED) {
                order.setStatus(OrderStatus.DECLINED);
                return;
            }
        }
    }

    private void processEntry(OrderEntry entry) {
        Product product = productService.getById(entry.getProduct().getId());
        if (product.getAmount() < entry.getAmount()) {
            entry.setStatus(OrderStatus.DECLINED);
        } else if (product.getAmount().equals(entry.getAmount())) {
            productService.delete(product);
            entry.setStatus(OrderStatus.PROCESSED);
        } else {
            product.setAmount(product.getAmount() - entry.getAmount());
            productService.change(product);
            entry.setStatus(OrderStatus.PROCESSED);
        }
    }

    @Override
    public Long triggerOrderProcessing() {
        Order order = this.getOldestOrder();
        if (order != null) {
            this.process(order);
            return order.getId();
        } else {
            return -1L;
        }
    }

    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setOrderEntryDao(OrderEntryDao orderEntryDao) {
        this.orderEntryDao = orderEntryDao;
    }

    @Autowired
    public void setConsumerDao(ConsumerDao consumerDao) {
        this.consumerDao = consumerDao;
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
}

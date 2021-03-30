package com.example.app.storage;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.app.storage.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private OrderService orderService;

    @Scheduled(fixedDelay = 5000)
    public void triggerOrderProcessing() {
        Long orderId = this.orderService.triggerOrderProcessing();
        if (orderId != -1L) {
            log.info("ORDER No" + orderId + " IS BEING PROCESSED");
        } else {
            log.info("NO WAITING ORDERS AVAILABLE");
        }
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
package com.example.app.storage.dto;

import java.util.List;

public class OrderDto {

    private Long consumerId;
    private String description;
    private String date;
    private List<OrderEntryDto> entries;

    public OrderDto() {}

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OrderEntryDto> getEntries() {
        return entries;
    }

    public void setEntries(List<OrderEntryDto> entries) {
        this.entries = entries;
    }
}

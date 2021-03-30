package com.example.app.storage.dto;

public class OrderEntryDto {

    private Long productId;
    private Integer amount;

    public OrderEntryDto() {}

    public OrderEntryDto(Long productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

package com.example.app.storage.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "goods")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private Integer amount;
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderEntry> associatedOrders;

    public Product() {
    }

    public List<OrderEntry> getAssociatedOrders() {
        return associatedOrders;
    }

    public void setAssociatedOrders(List<OrderEntry> associatedOrders) {
        this.associatedOrders = associatedOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}

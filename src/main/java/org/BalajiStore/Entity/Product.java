package org.BalajiStore.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // BASIC INFO
    // =========================
    private String name;

    private String unit;

    // =========================
    // STOCK INFO
    // =========================
    private Double quantity; // LIVE STOCK

    @Column(name = "opening_quantity")
    private Double openingQuantity; // INITIAL STOCK (NEVER CHANGE AFTER CREATE)

    private Double price;

    // =========================
    // AUDIT
    // =========================
    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @Column(name = "opening_price")
    private Double openingPrice;

    public Product() {}

    // =========================
    // CONSTRUCTOR (SAFE INIT)
    // =========================
    public Product(String name, Double quantity, Double price, String unit) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.openingPrice = price;

        // opening stock fixed at creation time
        this.openingQuantity = quantity;

        this.createdDate = LocalDate.now();

    }

    // =========================
    // GETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getOpeningQuantity() {
        return openingQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public Double getOpeningPrice() {
        return openingPrice;
    }

    // =========================
    // SETTERS
    // =========================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setOpeningQuantity(Double openingQuantity) {
        this.openingQuantity = openingQuantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    public void setOpeningPrice(Double openingPrice) {
        this.openingPrice = openingPrice;
    }
}
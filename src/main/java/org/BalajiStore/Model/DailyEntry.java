package org.BalajiStore.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "daily_entry")
public class DailyEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =====================
    // PRODUCT REFERENCE
    // =====================
    @Column(name = "product_id", nullable = false)
    private Long productId;

    // =====================
    // TRANSACTION DATA
    // =====================
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "type", nullable = false)
    private String type; // purchase / usage

    @Column(name = "price")
    private Double price; // per unit price at time of entry

    // =====================
    // ENTRY DATE
    // =====================
    @Column(name = "entry_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryTime;

    // =====================
    // SOFT DELETE FLAG
    // =====================
    @Column(name = "deleted")
    private Boolean deleted = false;

    public DailyEntry() {}

    // =====================
    // GETTERS
    // =====================

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDate getEntryTime() {
        return entryTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    // =====================
    // SETTERS
    // =====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setEntryTime(LocalDate entryTime) {
        this.entryTime = entryTime;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
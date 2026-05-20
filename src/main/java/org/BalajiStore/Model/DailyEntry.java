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

    // ✅ NEW: link with Product table (IMPORTANT FIX)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "item_name")
    private String itemName; // optional (for display only)

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private Double price;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "entry_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryTime;

    @Column(name = "deleted")
    private Boolean deleted = false;

    public DailyEntry() {}

    // Auto calculate total price
    @PrePersist
    @PreUpdate
    public void calculateTotalPrice() {
        if (quantity != null && price != null) {
            this.totalPrice = quantity * price;
        } else {
            this.totalPrice = 0.0;
        }
    }

    // ================= GETTERS =================

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public LocalDate getEntryTime() {
        return entryTime;
    }

    public Double getPrice() {
        return price;
    }

    public Double getTotalPrice() {
        if (totalPrice == null && quantity != null && price != null) {
            return quantity * price;
        }
        return totalPrice;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    // ================= SETTERS =================

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEntryTime(LocalDate entryTime) {
        this.entryTime = entryTime;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
package org.BalajiStore.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "daily_entry")
public class DailyEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "type")
    private String type;

    @Column(name = "entry_time")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")  // 12-hour format with AM/PM
    private LocalDateTime entryTime;

    public DailyEntry() {}

    // Getters and setters
    public Long getId() { return id; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public String getType() { return type; }
    public LocalDateTime getEntryTime() { return entryTime; }

    public void setId(Long id) { this.id = id; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setType(String type) { this.type = type; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }
}
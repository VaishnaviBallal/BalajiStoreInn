package org.BalajiStore.Dto;

import java.time.LocalDate;

public class ItemReportDto {

    private String itemName;
    private Double openingStock;
    private Double purchased;
    private Double used;
    private Double closingStock;
    private Double purchaseAmount;
    private Double usageAmount;
    private Double stockValue;
    private LocalDate date; // ✅ important

    public ItemReportDto() {
    }

    public ItemReportDto(String itemName,
                         Double openingStock,
                         Double purchased,
                         Double used,
                         Double closingStock,
                         Double purchaseAmount,
                         Double usageAmount,
                         Double stockValue,
                         LocalDate date) {

        this.itemName = itemName;
        this.openingStock = openingStock;
        this.purchased = purchased;
        this.used = used;
        this.closingStock = closingStock;
        this.purchaseAmount = purchaseAmount;
        this.usageAmount = usageAmount;
        this.stockValue = stockValue;
        this.date = date;
    }

    public LocalDate getDate() { return date; }
    public String getItemName() { return itemName; }
    public Double getOpeningStock() { return openingStock; }
    public Double getPurchased() { return purchased; }
    public Double getUsed() { return used; }
    public Double getClosingStock() { return closingStock; }
    public Double getPurchaseAmount() { return purchaseAmount; }
    public Double getUsageAmount() { return usageAmount; }
    public Double getStockValue() { return stockValue;
    }



    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setOpeningStock(Double openingStock) {
        this.openingStock = openingStock;
    }

    public void setPurchased(Double purchased) {
        this.purchased = purchased;
    }

    public void setUsed(Double used) {
        this.used = used;
    }

    public void setClosingStock(Double closingStock) {
        this.closingStock = closingStock;
    }


}
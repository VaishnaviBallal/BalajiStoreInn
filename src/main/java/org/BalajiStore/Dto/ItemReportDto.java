package org.BalajiStore.Dto;

public class ItemReportDto {

    private String itemName;
    private Double openingStock;
    private Double purchased;
    private Double used;
    private Double closingStock;
    private double purchaseAmount;
    private double usageAmount;
    private double stockValue;

    public ItemReportDto() {
    }

    public ItemReportDto(String itemName,
                         Double openingStock,
                         Double purchased,
                         Double used,
                         Double closingStock,
                         Double purchaseAmount,
                         Double usageAmount,
                         Double stockValue) {

        this.itemName = itemName;
        this.openingStock = openingStock;
        this.purchased = purchased;
        this.used = used;
        this.closingStock = closingStock;
        this.purchaseAmount = purchaseAmount;
        this.usageAmount = usageAmount;
        this.stockValue = stockValue;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getOpeningStock() {
        return openingStock;
    }

    public Double getPurchased() {
        return purchased;
    }

    public Double getUsed() {
        return used;
    }

    public Double getClosingStock() {
        return closingStock;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public double getUsageAmount() {
        return usageAmount;
    }

    public double getStockValue() {
        return stockValue;
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
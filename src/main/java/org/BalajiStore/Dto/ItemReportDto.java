package org.BalajiStore.Dto;

public class ItemReportDto {

    private String itemName;
    private long openingStock;
    private long purchased;
    private long used;
    private long closingStock;

    public ItemReportDto() {
    }

    public ItemReportDto(String itemName,
                         long openingStock,
                         long purchased,
                         long used,
                         long closingStock) {
        this.itemName = itemName;
        this.openingStock = openingStock;
        this.purchased = purchased;
        this.used = used;
        this.closingStock = closingStock;
    }

    public String getItemName() {
        return itemName;
    }

    public long getOpeningStock() {
        return openingStock;
    }

    public long getPurchased() {
        return purchased;
    }

    public long getUsed() {
        return used;
    }

    public long getClosingStock() {
        return closingStock;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setOpeningStock(long openingStock) {
        this.openingStock = openingStock;
    }

    public void setPurchased(long purchased) {
        this.purchased = purchased;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public void setClosingStock(long closingStock) {
        this.closingStock = closingStock;
    }
}
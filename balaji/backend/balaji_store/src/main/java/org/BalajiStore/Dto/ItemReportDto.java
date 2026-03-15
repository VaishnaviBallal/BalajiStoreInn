package org.BalajiStore.Dto;

public class ItemReportDto {

    private String itemName;
    private long purchased;
    private long used;
    private long pending;

    public ItemReportDto(String itemName, long purchased, long used, long pending) {
        this.itemName = itemName;
        this.purchased = purchased;
        this.used = used;
        this.pending = pending;
    }

    public String getItemName() {
        return itemName;
    }

    public long getPurchased() {
        return purchased;
    }

    public long getUsed() {
        return used;
    }

    public long getPending() {
        return pending;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }
}
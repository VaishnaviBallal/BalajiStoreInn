package org.BalajiStore.Dto;

import java.time.LocalDate;

public class ItemHistoryDto {

    private LocalDate date;
    private Double purchased;
    private Double used;
    private Double purchaseAmount;
    private Double usageAmount;

    public ItemHistoryDto(
            LocalDate date,
            Double purchased,
            Double used,
            Double purchaseAmount,
            Double usageAmount
    ) {
        this.date = date;
        this.purchased = purchased;
        this.used = used;
        this.purchaseAmount = purchaseAmount;
        this.usageAmount = usageAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getPurchased() {
        return purchased;
    }

    public Double getUsed() {
        return used;
    }

    public Double getPurchaseAmount() {
        return purchaseAmount;
    }

    public Double getUsageAmount() {
        return usageAmount;
    }
}



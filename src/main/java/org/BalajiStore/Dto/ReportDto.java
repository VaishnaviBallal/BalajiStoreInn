package org.BalajiStore.Dto;

public class ReportDto {
    private int purchased;
    private int used;
    private int pending;

    public ReportDto(int purchased, int used, int pending) {
        this.purchased = purchased;
        this.used = used;
        this.pending = pending;
    }

    public int getPurchased() {
        return purchased;
    }

    public int getUsed() {
        return used;
    }

    public int getPending() {
        return pending;
    }
}

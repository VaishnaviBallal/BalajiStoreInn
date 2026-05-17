package org.BalajiStore.Orders.Dto;

import java.util.List;

public class OrderDTO {

    private List<OrderItemDTO> items;
    private Double total;
    private Integer tableNo;

    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Integer getTableNo() { return tableNo; }
    public void setTableNo(Integer tableNo) { this.tableNo = tableNo; }
}
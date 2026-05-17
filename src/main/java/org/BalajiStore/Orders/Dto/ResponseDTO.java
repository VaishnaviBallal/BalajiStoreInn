package org.BalajiStore.Orders.Dto;

import java.util.List;

public class ResponseDTO {

    private Long id;
    private Integer tableNo;
    private Double total;
    private String status;
    private List<OrderItemDTO> items;

    public ResponseDTO() {}

    public ResponseDTO(Long id, Integer tableNo, Double total, String status, List<OrderItemDTO> items) {
        this.id = id;
        this.tableNo = tableNo;
        this.total = total;
        this.status = status;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
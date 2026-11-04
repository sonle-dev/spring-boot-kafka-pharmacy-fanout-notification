package com.example.inventoryservice.event;

import java.io.Serializable;

public class OrderEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private Long medicineId;
    private int quantity;
    private String timestamp;

    public OrderEvent() {
    }

    public OrderEvent(String orderId, Long medicineId, int quantity, String timestamp) {
        this.orderId = orderId;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
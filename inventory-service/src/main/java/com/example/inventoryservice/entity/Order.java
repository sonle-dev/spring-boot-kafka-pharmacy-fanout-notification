package com.example.inventoryservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medicine_id")
    private Long medicineId;

    @Column(name = "price_sell")
    private double priceSell;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "total_amount")
    private double totalAmount;

    public Order() {
    }

    public Order(Long medicineId, double priceSell, int quantity, String timestamp, double totalAmount) {
        this.medicineId = medicineId;
        this.priceSell = priceSell;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public double getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(double priceSell) {
        this.priceSell = priceSell;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
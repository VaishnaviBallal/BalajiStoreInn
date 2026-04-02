package org.BalajiStore.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unit;
    private String name;

    private int quantity;

    private Double price;

    public Product() {
    }

    public Product(String name, int quantity, double price,String unit) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.unit=unit;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
    public  String getUnit(){
        return  unit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUnit(String unit){
        this.unit=unit;
    }
}
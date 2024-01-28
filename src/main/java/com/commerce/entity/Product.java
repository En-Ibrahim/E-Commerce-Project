package com.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull
    private String name;
    @NotNull
    private double price;

    @NotNull(message = "Serial number must not be null")
    @Digits(integer = 9, fraction = 0, message = "Serial number must have at most 9 digits")
    private Long serialNumber;

    private String vendor;

    private int counter;

    @ManyToMany
    @JsonIgnore
    private List<Order> orders;

    public Product(Long productId) {
        this.productId = productId;
    }

    public void decrementCounter() {
        if(counter > 0) this.counter--;
    }

    public void incrementCounter() {
        setCounter(getCounter()+1);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", serialNumber=" + serialNumber +
                ", vendor='" + vendor + '\'' +
                '}';
    }
}

package com.commerce.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String  location;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double totalPrice;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Product> products;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", location='" + location + '\'' +
                ", totalPrice=" + totalPrice +
                ", products=" + products +
                '}';
    }
}

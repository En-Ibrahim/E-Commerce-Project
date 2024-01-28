package com.commerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private Long userId;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Value must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Value must be greater than or equal 0")
    private Double balance;

    @OneToMany
    @JsonIgnore
    private List<Order> orders;


    public double decrementBalance(double price){

        if(price > getBalance()){
            throw new IllegalArgumentException("Your balance is less than "+price);
        }


        setBalance(this.balance - price);
        return getBalance();
    }

    public double incrementBalance(double price){

        if(price <= 0){
            throw new IllegalArgumentException("Invalid price");
        }
        setBalance(this.balance + price);
        return getBalance();
    }

}

package com.commerce.entity.dto;

import com.commerce.entity.User;
import com.commerce.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDtoResponse {

    private Long userId;
    private String name;
    private String email;
    private Double balance;
    private List<Order> orders;



    public static UserDtoResponse mapCustomer(User user){
        UserDtoResponse customerDto= new UserDtoResponse();

            customerDto.setUserId(user.getUserId());
            customerDto.setName(user.getName());
            customerDto.setEmail(user.getEmail());
            customerDto.setBalance(user.getBalance());
            customerDto.setOrders(user.getOrders());


        return customerDto;
    }


}

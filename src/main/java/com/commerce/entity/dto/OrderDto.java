package com.commerce.entity.dto;

import com.commerce.entity.User;
import com.commerce.entity.Order;
import com.commerce.entity.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Data
public class OrderDto {

    private Long orderId;
    private String location;
    private String userEmail;
    private List<Long> products;



    public static Order mapOrderDto(OrderDto orderDto){
        Order order = new Order();
        User user = new User();
        ArrayList<Product> productList = new ArrayList<>();
        orderDto.getProducts().forEach(productId -> productList.add(new Product(productId)));

        user.setEmail(orderDto.getUserEmail());
        order.setOrderId(orderDto.getOrderId());
        order.setLocation(orderDto.getLocation());
        order.setUser(user);
        order.setProducts(productList);
        return order;
    }

    public static List<Order> mapOrderDtos(List<OrderDto> orderDtos){
        List<Order> orders=new ArrayList<>();
        orderDtos.forEach(orderDto -> {
            Order order = new Order();
            User user = new User();
            ArrayList<Product> productList = new ArrayList<>();
            orderDto.getProducts().forEach(productId -> productList.add(new Product(productId)));

            user.setEmail(orderDto.getUserEmail());
            order.setOrderId(orderDto.getOrderId());
            order.setLocation(orderDto.getLocation());
            order.setUser(user);
            order.setProducts(productList);

            orders.add(order);
        });
        return orders;
    }












}

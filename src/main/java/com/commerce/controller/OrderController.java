package com.commerce.controller;


import com.commerce.entity.Order;
import com.commerce.entity.dto.OrderDto;
import com.commerce.service.UserService;
import com.commerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;



    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderDto orderDto) {
        try {
            Order savedOrder = this.orderService.save(orderDto);
            return ResponseEntity.ok(savedOrder);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<?> saveAll(@RequestBody List<OrderDto> orderDtos) {
        try {
            return  ResponseEntity.ok(this.orderService.saveAll(orderDtos));
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<?> findAllByCustomerEmail(@Param("email") String  email){
        return ResponseEntity.ok(orderService.findAllByCustomerEmail(email));
    }



    //cancel order
    @DeleteMapping("/canceled")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        try {
            orderService.delete(id);

            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {

            throw new IllegalArgumentException("It's already deleted :>"+e.getMessage());
        }


    }
}
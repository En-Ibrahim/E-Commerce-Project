package com.commerce.service;


import com.commerce.entity.User;
import com.commerce.entity.Order;
import com.commerce.entity.Product;
import com.commerce.entity.dto.UserDtoResponse;
import com.commerce.entity.dto.OrderDto;
import com.commerce.repo.UserRepo;
import com.commerce.repo.OrderRepo;
import com.commerce.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService  {

        @Autowired
        private OrderRepo orderRepo;
        @Autowired
        private UserRepo userRepo;
        @Autowired
        private ProductRepo productRepo;

        @Autowired
        private UserService userService;

        @Autowired
        private ProductService productService;

        @Autowired
        private NotificationService notificationService;

        private Logger logger= LoggerFactory.getLogger(OrderService.class);
        public Order save(OrderDto orderDto){
            Optional<User> user= userRepo.findByEmail(orderDto.getUserEmail());
            if(user.isEmpty()) {
                logger.error("Anonymous User");
                throw new IllegalArgumentException("Anonymous User ");
            }

            Order order = mapOrder(orderDto,user);
            user.get().decrementBalance(order.getTotalPrice());

            this.orderRepo.save(order);
             String subject="E-Commerce";
             String message="Dear "+user.get().getName()+" , your booking of the "+order+" is confirmed. thanks for using our store :) ";
             notificationService.sendEmail(orderDto.getUserEmail(),subject,message);
            return order;
        }

        private Order mapOrder(OrderDto orderDto,Optional<User> user){
            Order order = OrderDto.mapOrderDto(orderDto);
            order.setUser(user.get());

            List<Product> products = this.productRepo.findAllById(orderDto.getProducts());
            order.setProducts(products);
            // get total price
            double totalPrice = products.stream().mapToDouble(Product::getPrice).sum();

            // decrement count of products
            products.forEach(Product::decrementCounter);

            // debit from all customers (price for each one + fees for each one)
            totalPrice += .01 * totalPrice;
            order.setTotalPrice(totalPrice);

            return order;
        }


    public List<Order> saveAll(List<OrderDto> orderDtos){

        List<Order> orders= new ArrayList<>();

        for (OrderDto orderDto:orderDtos) {
            Order order = save(orderDto);
            orders.add(order);
        }

        return orders;
    }


        public Order findById(Long id){
            return orderRepo.findByOrderId(id)
                    .orElseThrow(()-> new IllegalArgumentException("Order Not Found"));
        }

        public List<Order> findAll(){
            return orderRepo.findAll();
        }



        //cancel order
        public void delete(Long id){
            Optional<Order> order = orderRepo.findById(id);
            if(order.isEmpty()) {
                logger.error("Order Not Found");
                throw new IllegalArgumentException("Order Not Found");
            }

            List<Product> products = order.get().getProducts();
            for (Product p:products) {
                p.incrementCounter();
                logger.info("counter "+p.getCounter());
//                productRepo.save(p);
            }

            orderRepo.deleteById(id);

            productRepo.saveAll(products);

            User user= order.get().getUser();
            user.incrementBalance(order.get().getTotalPrice());
            userRepo.save(user);
            String subject= "E-Commerce";
            String message="You canceled your order and paid "+order.get().getTotalPrice()+
                    " back you again \nand your balance now is : "+order.get().getUser().getBalance();

            notificationService.sendEmail(order.get().getUser().getEmail(),subject,message);
        }




       // findAll method for a specific user that takes (customerId)
        public UserDtoResponse findAllByCustomerEmail(String email){
            User user = userService.findByEmail(email);
            List<Order> orders= orderRepo.findAllByUserUserId(user.getUserId());
            user.setOrders(orders);
           return UserDtoResponse.mapCustomer(user);

        }






}

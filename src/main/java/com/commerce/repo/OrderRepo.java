package com.commerce.repo;


import com.commerce.entity.Order;
import com.commerce.entity.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {


    Optional<Order> findByOrderId(Long id);

    List<Product> findProductsByOrderId(Long id);

    List<Order> findAllByUserUserId(Long id);





}

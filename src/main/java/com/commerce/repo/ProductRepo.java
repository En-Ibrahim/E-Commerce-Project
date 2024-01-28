package com.commerce.repo;

import com.commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    Optional<Product> findByProductId(Long id);

    List<Product> findAllByOrdersOrderId(Long id);

}

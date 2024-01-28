package com.commerce.service;


import com.commerce.entity.Product;
import com.commerce.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService  {

    @Autowired
    private ProductRepo productRepo;
    private Logger logger = LoggerFactory.getLogger(ProductService.class);
    public Product save(Product p){
        return productRepo.save(p);
    }

    public Product findById(Long id){
        return productRepo.findByProductId(id).orElseThrow(()->
                new IllegalArgumentException("Not found product")
        );
    }

    public List<Product> findAll(){
        return productRepo.findAll();
    }


    public void delete(Long id){
        Optional<Product> product = productRepo.findByProductId(id);
        if(product.isEmpty()) {
            logger.error("Not found product");
            throw new IllegalArgumentException("Not found product");
        }
        productRepo.deleteById(id);
    }

}

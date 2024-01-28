package com.commerce.controller;


import com.commerce.entity.Product;
import com.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController  {

    @Autowired
    private ProductService productService;



    @PostMapping
    public ResponseEntity<?> save(@RequestBody Product t){
        return ResponseEntity.ok(productService.save(t));
    }
    @GetMapping("/id")
    public ResponseEntity<?> findById(@Param("id") Long id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
           }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }


    @DeleteMapping()
    public ResponseEntity<?> delete(@Param("id") Long id){
        try {
            productService.delete(id);

            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }catch (Exception e){
            throw new IllegalArgumentException("It's already deleted");
        }
}
}

package com.commerce.controller;

import com.commerce.entity.User;
import com.commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> add(@RequestBody User t) {
        return ResponseEntity.ok(userService.add(t));
    }

    @PostMapping("/check")
    public ResponseEntity<?> chechUser(@Param("email") String email,@Param("password")String password){
        try {
            userService.check(email, password);
            return new ResponseEntity<>("Successful Access", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(" Unsuccessful Access",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    @GetMapping("/email")
    public ResponseEntity<?> findByEmail(@Param("email") String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@Param("id") Long id) {
        try {
            userService.delete(id);

            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("It's already deleted");
        }

    }
}
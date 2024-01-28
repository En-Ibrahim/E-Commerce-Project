package com.commerce.service;


import com.commerce.entity.User;
import com.commerce.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NotificationService notificationService;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User add(User user) {
        Optional<User> customer1 = userRepo.findByEmail(user.getEmail());
        if (customer1.isPresent()) {
            logger.error("It's already Existed");
            throw new IllegalArgumentException("It's already Existed");
        }
        userRepo.save(user);
        String subject = "E-commerce";
        String message = "Dear " + user.getName() + "\nYou have successful created \nYour mail is : " + user.getEmail();
        notificationService.sendEmail(user.getEmail(), subject, message);
        return user;

    }

    public boolean check(String email, String pass) {
        Optional<User> user = userRepo.findByEmail(email);
        if (!user.isPresent() && user.isEmpty()) {
            logger.error("Not found the user");
            throw new IllegalArgumentException("Not found the user");
        }
        if (!user.get().getPassword().equals(pass)) {
            logger.error("Input a validation password");
            throw new IllegalArgumentException("Input a validation password");
        }

        return true;
    }


    public User findById(Long id) {
        return userRepo.findByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
    }

    public User findByEmail(String email) {

        logger.info("find user >> "+userRepo.findByEmail(email).toString());
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
    }

    public List<User> findAll() {

        List<User> users=userRepo.findAll();
        logger.info(users.toString());
        return users;
    }

    public void delete(Long id) {
        Optional<User> customer = userRepo.findById(id);
        if (customer.isEmpty()) {
            logger.error("User not found");
            throw new IllegalArgumentException("User Not Found");
        }
        userRepo.deleteById(id);
    }


}

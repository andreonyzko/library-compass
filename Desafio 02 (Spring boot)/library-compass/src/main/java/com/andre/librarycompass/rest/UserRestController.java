package com.andre.librarycompass.rest;

import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId){
        return userService.findById(userId);
    }
}

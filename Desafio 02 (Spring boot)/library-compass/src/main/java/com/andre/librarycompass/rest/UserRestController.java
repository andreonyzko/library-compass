package com.andre.librarycompass.rest;

import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    public User getUser(@PathVariable Long userId){
        return userService.findById(userId);
    }

    @PostMapping
    public User registerUser(@RequestBody User user){
        user.setId(null);
        return userService.save(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user){
        user.setId(userId);
        return userService.update(user);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
        return "User deleted: " + userId;
    }
}

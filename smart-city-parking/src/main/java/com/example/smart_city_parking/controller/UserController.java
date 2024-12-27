package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.User;
import com.example.smart_city_parking.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user) {
        userService.updateUser(user);
        return "User updated successfully!";
    }
}

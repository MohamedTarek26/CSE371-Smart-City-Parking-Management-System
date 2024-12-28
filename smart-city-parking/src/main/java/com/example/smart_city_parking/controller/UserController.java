package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.User;
import com.example.smart_city_parking.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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

       @PostMapping("/updateRole")
    public ResponseEntity<String> updateUserRole(@RequestBody Map<String, Object> requestBody) {
        try {
            if (!requestBody.containsKey("userId") || !requestBody.containsKey("roleId")) {
                return ResponseEntity.badRequest().body("Missing 'userId' or 'roleId' in request body.");
            }

            int userId = Integer.parseInt(requestBody.get("userId").toString());
            int newRoleId = Integer.parseInt(requestBody.get("roleId").toString());
            int rows = userService.updateUserRoleId(userId, newRoleId);

            if (rows > 0) {
                return ResponseEntity.ok("User role updated successfully.");
            } else {
                return ResponseEntity.status(404).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating user role: " + e.getMessage());
        }
    }
}

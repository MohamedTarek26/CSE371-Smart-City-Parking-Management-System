package com.example.smart_city_parking.controller;

import com.example.smart_city_parking.models.User;
import com.example.smart_city_parking.services.UserService;
import com.example.smart_city_parking.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        // user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        user.setRoleId(1); // Set default role to 1 (user)
        userService.saveUser(user); // Save user to DB

        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        User user = userService.findByEmail(email); // Fetch user by email
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // if (!passwordEncoder.matches(password, user.getPassword())) {
        //     throw new RuntimeException("Invalid password");
        // }
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(user.getUserEmail(), user.getUserName(), user.getUserEmail(), user.getRoleId());

    }
}

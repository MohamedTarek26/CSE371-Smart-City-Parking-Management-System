package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Fetch all users from the database
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
            rs.getInt("user_id"),
            rs.getString("user_name"),
            rs.getInt("role_id"),
            rs.getString("user_email"),
            rs.getString("user_phone"),
            rs.getString("license_plate"),
            rs.getString("payment_method"),
            rs.getString("password")
        ));
    }

    // Fetch a user by their ID
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) -> new User(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getInt("role_id"),  
                rs.getString("user_email"),
                rs.getString("user_phone"),
                rs.getString("license_plate"),
                rs.getString("payment_method"),
                rs.getString("password")
            ));
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if user not found
        }
    }

    // Fetch a user by their email
    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE user_email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> new User(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getInt("role_id"),
                rs.getString("user_email"),
                rs.getString("user_phone"),
                rs.getString("license_plate"),
                rs.getString("payment_method"),
                rs.getString("password") // Include password
            ));
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if user not found
        }
    }

    // Save a new user to the database
    public int saveUser(User user) {
        String sql = "INSERT INTO Users (user_name, user_email, user_phone, license_plate, payment_method, password) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            user.getUserName(), 
            user.getUserEmail(), 
            user.getUserPhone(), 
            user.getLicensePlate(), 
            user.getPaymentMethod(), 
            user.getPassword()
        );
    }

    // Update an existing user's details
    public int updateUser(User user) {
        String sql = "UPDATE Users SET user_name = ?, user_email = ?, user_phone = ?, license_plate = ?, payment_method = ?, password = ? WHERE user_id = ?";
        return jdbcTemplate.update(sql, 
            user.getUserName(), 
            user.getUserEmail(), 
            user.getUserPhone(), 
            user.getLicensePlate(), 
            user.getPaymentMethod(), 
            user.getPassword(),
            user.getUserId()
        );
    }

    // Delete a user from the database
    public int deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        return jdbcTemplate.update(sql, userId);
    }
}

package com.example.smart_city_parking.services;

import com.example.smart_city_parking.models.Sensor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SensorService {

    private final JdbcTemplate jdbcTemplate;

    public SensorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all sensors
    public List<Sensor> getAllSensors() {
        String sql = "SELECT * FROM Sensor";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Sensor(
                rs.getInt("sensor_id"),
                rs.getInt("spot_id"),
                rs.getString("status"),
                rs.getTimestamp("last_updated")
        ));
    }

    // Get sensor by ID
    public Sensor getSensorById(int sensorId) {
        String sql = "SELECT * FROM Sensor WHERE sensor_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{sensorId}, (rs, rowNum) -> new Sensor(
                rs.getInt("sensor_id"),
                rs.getInt("spot_id"),
                rs.getString("status"),
                rs.getTimestamp("last_updated")
        ));
    }

    // Add a new sensor
    public int addSensor(int spotId, String status) {
        String sql = "INSERT INTO Sensor (spot_id, status) VALUES (?, ?)";
        return jdbcTemplate.update(sql, spotId, status);
    }

    // Update sensor status
    public int updateSensorStatus(int sensorId, String status) {
        String sql = "UPDATE Sensor SET status = ? WHERE sensor_id = ?";
        return jdbcTemplate.update(sql, status, sensorId);
    }

    // Delete a sensor
    public int deleteSensor(int sensorId) {
        String sql = "DELETE FROM Sensor WHERE sensor_id = ?";
        return jdbcTemplate.update(sql, sensorId);
    }
}

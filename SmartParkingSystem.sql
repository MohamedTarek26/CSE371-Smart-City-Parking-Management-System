-- Drop Triggers
DROP TRIGGER IF EXISTS UpdateSpotStatusAfterReservation;
DROP TRIGGER IF EXISTS UpdateSpotStatusAfterCancellation;
DROP TRIGGER IF EXISTS UpdateSensorStatus;
DROP TRIGGER IF EXISTS AdjustDynamicPricing;
DROP TRIGGER IF EXISTS UpdateSensorOnSpotChange;
DROP TRIGGER IF EXISTS UpdateSpotOnSensorChange;

-- Drop Stored Procedures
DROP PROCEDURE IF EXISTS GenerateParkingLots;
DROP PROCEDURE IF EXISTS GenerateParkingSpots;
DROP PROCEDURE IF EXISTS GenerateUsers;
DROP PROCEDURE IF EXISTS GenerateReservations;
DROP PROCEDURE IF EXISTS GenerateDynamicPricing;
DROP PROCEDURE IF EXISTS GenerateSensorData;
DROP PROCEDURE IF EXISTS ApplyLatePenalty;
DROP PROCEDURE IF EXISTS GetAvailableSpots;
DROP PROCEDURE IF EXISTS CalculateRevenue;


USE SmartParkingSystem;

DROP TABLE IF EXISTS Sensor;
DROP TABLE IF EXISTS Notification;
DROP TABLE IF EXISTS DynamicPricing;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS ParkingSpot;
DROP TABLE IF EXISTS ParkingLot;

-- Table: ParkingLot
CREATE TABLE ParkingLot (
    lot_id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    pricing_structure TEXT,
    types_of_spots VARCHAR(255),
    latitude DECIMAL(9, 6) NOT NULL DEFAULT 1,
    longitude DECIMAL(9, 6) NOT NULL DEFAULT 1
);

-- Table: ParkingSpot
CREATE TABLE ParkingSpot (
    spot_id INT AUTO_INCREMENT PRIMARY KEY,
    lot_id INT NOT NULL,
    status ENUM('Available', 'Occupied', 'Reserved') DEFAULT 'Available',
    type ENUM('Regular', 'Disabled', 'EV Charging') NOT NULL,
    FOREIGN KEY (lot_id) REFERENCES ParkingLot(lot_id) ON DELETE CASCADE
);

-- Table: Roles
CREATE TABLE Roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

-- Table: Users
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL Default 3,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) UNIQUE NOT NULL,
    user_phone VARCHAR(15),
    license_plate VARCHAR(45) UNIQUE NOT NULL,
    payment_method VARCHAR(50),
    password VARCHAR(255),
    FOREIGN KEY (role_id) REFERENCES Roles(role_id)
);

-- Table: Reservation
CREATE TABLE Reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    spot_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status ENUM('Reserved', 'Completed', 'No_Show') DEFAULT 'Reserved',
    penalty DECIMAL(10,2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (spot_id) REFERENCES ParkingSpot(spot_id) ON DELETE CASCADE
);

-- Table: Payment
CREATE TABLE Payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method ENUM('Credit_card', 'Debit_card', 'InstaPay', 'Wallet') NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id)
);

-- Table: DynamicPricing
CREATE TABLE DynamicPricing (
    pricing_id INT AUTO_INCREMENT PRIMARY KEY,
    spot_id INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    demand_level ENUM('Low', 'Medium', 'High') NOT NULL,
    FOREIGN KEY (spot_id) REFERENCES ParkingSpot(spot_id)
);

DROP TABLE IF EXISTS Sensor;
CREATE TABLE Sensor (
    sensor_id INT AUTO_INCREMENT PRIMARY KEY,
    spot_id INT NOT NULL,
    status ENUM('Free', 'Occupied') DEFAULT 'Free',
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    license_plate VARCHAR(45),
    FOREIGN KEY (spot_id) REFERENCES ParkingSpot(spot_id)
);

-- Table to store notifications for users
DROP TABLE IF EXISTS Notification;
CREATE TABLE Notification (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    date_sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Adding a Constraint to ensure start_time < end_time in reservation table
ALTER TABLE Reservation 
ADD CONSTRAINT chk_start_end_time 
CHECK (start_time < end_time);

-- Insert Data into the Roles Table
INSERT INTO Roles (role_name)
VALUES
('driver'),
('parking lot administrator'),
('system administrator');

-- Insert Data into the ParkingLot Table
DELIMITER //

CREATE PROCEDURE GenerateParkingLots(IN numLots INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= numLots DO
        INSERT INTO ParkingLot (location, capacity, pricing_structure, types_of_spots, latitude, longitude)
        VALUES (
            CONCAT('Location_', i),
            FLOOR(50 + RAND() * 150), -- Random capacity between 50 and 200
            'Flat_Rate',
            'Regular,Disabled,EV Charging',
            40.7128 + (RAND() * 0.1),  -- Random latitude near New York City
            -74.0060 + (RAND() * 0.1)  -- Random longitude near New York City
        );
        SET i = i + 1;
    END WHILE;
END
//
DELIMITER ;

-- Generate 100 parking lots
CALL GenerateParkingLots(100);

-- Insert Data into the ParkingSpot Table
DELIMITER //

CREATE PROCEDURE GenerateParkingSpots(IN spotsPerLot INT)
BEGIN
    DECLARE lot_count INT;
    SET lot_count = (SELECT COUNT(*) FROM ParkingLot);

    INSERT INTO ParkingSpot (lot_id, status, type)
    SELECT 
        pl.lot_id,
        'Available',
        ELT(FLOOR(1 + RAND() * 3), 'Regular', 'Disabled', 'EV Charging')
    FROM 
        ParkingLot pl
    CROSS JOIN (
        SELECT 1 AS n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 
        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 
        UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 
        UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15 UNION ALL SELECT 16 
        UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL SELECT 20
    ) Numbers;
END//

DELIMITER ;

-- Generate 20 spots per parking lot
CALL GenerateParkingSpots(20);

-- Generate Data for Users
-- Generate Users Without Using Cursors
INSERT INTO Users (role_id, user_name, user_email, user_phone, license_plate, payment_method, password)
SELECT 
    ELT(FLOOR(1 + RAND() * 3), 1, 2, 3) AS role_id,  -- Randomly assign role
    CONCAT('User_', n) AS user_name,
    CONCAT('user', n, '@example.com') AS user_email,
    CONCAT('+12345678', LPAD(n, 4, '0')) AS user_phone,
    CONCAT('ABC-', LPAD(n, 4, '0')) AS license_plate,
    ELT(FLOOR(1 + RAND() * 4), 'Credit Card', 'Debit Card', 'Wallet', 'InstaPay') AS payment_method,
    UUID() AS password  -- Random password
FROM (
    SELECT @row := @row + 1 AS n
    FROM (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) t1,
         (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) t2,
         (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) t3,
         (SELECT @row := 0) t4
    LIMIT 1000  -- Change 1000 to the desired number of users
) AS Numbers;

-- Generate Data for Reservation
INSERT INTO Reservation (user_id, spot_id, start_time, end_time, status, penalty)
SELECT 
    (SELECT user_id FROM Users ORDER BY RAND() LIMIT 1) AS user_id,  -- Random User
    (SELECT spot_id FROM ParkingSpot WHERE status = 'Available' ORDER BY RAND() LIMIT 1) AS spot_id,  -- Random Available Spot
    @start_time := NOW() - INTERVAL FLOOR(RAND() * 30) DAY AS start_time,  -- Random start time
    @start_time + INTERVAL FLOOR(1 + RAND() * 5) HOUR AS end_time,  -- Ensure end_time > start_time
    'Reserved' AS status,
    NULL AS penalty
FROM (
    SELECT @row := @row + 1 AS n
    FROM (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) t1,
         (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) t2,
         (SELECT @row := 0) t3
    LIMIT 1500  -- Change 1500 to the desired number of reservations
) AS Numbers;

-- Update parking spot statuses
UPDATE ParkingSpot
SET status = 'Reserved'
WHERE spot_id IN (SELECT spot_id FROM Reservation);

-- Generate Data for Payment 
INSERT INTO Payment (reservation_id, amount, payment_method, transaction_date)
SELECT 
    reservation_id,  -- Use the reservation ID from the Reservation table
    FLOOR(10 + RAND() * 90) + (RAND() * 0.99),  -- Generate a random amount between 10.00 and 100.00
    ELT(FLOOR(1 + RAND() * 4), 'Credit_card', 'Debit_card', 'InstaPay', 'Wallet') AS payment_method,  -- Random payment method
    NOW() - INTERVAL FLOOR(RAND() * 30) DAY AS transaction_date  -- Random transaction date within the last 30 days
FROM Reservation
WHERE status = 'Reserved'  -- Only generate payments for reservations with a status of 'Reserved'
LIMIT 1000;  -- Adjust the limit based on the desired number of payments

-- Generate Data for Dynamic Pricing
INSERT INTO DynamicPricing (spot_id, price, demand_level)
SELECT 
    ps.spot_id,
    FLOOR(20 + RAND() * 80) AS price,  -- Random price between 20 and 100
    ELT(FLOOR(1 + RAND() * 3), 'Low', 'Medium', 'High') AS demand_level
FROM ParkingSpot ps
LEFT JOIN DynamicPricing dp ON ps.spot_id = dp.spot_id
WHERE dp.spot_id IS NULL  -- Ensure no duplicate pricing entries
ORDER BY RAND()
LIMIT 2000;  -- Change 2000 to the desired number of dynamic pricing records

-- Generate Data for Sensor Table
INSERT INTO Sensor (spot_id, status, last_updated)
SELECT 
    ps.spot_id,
    CASE WHEN RAND() > 0.5 THEN 'Free' ELSE 'Occupied' END AS status,  -- Random status
    CURRENT_TIMESTAMP AS last_updated
FROM ParkingSpot ps
LEFT JOIN Sensor s ON ps.spot_id = s.spot_id
WHERE s.spot_id IS NULL  -- Ensure no duplicate sensors
ORDER BY RAND()
LIMIT 500;  -- Change 500 to the desired number of sensors

Drop Trigger IF EXISTS set_parking_spot_reserved;
DELIMITER $$

CREATE TRIGGER set_parking_spot_reserved
BEFORE INSERT ON Reservation
FOR EACH ROW
BEGIN
    -- Update the status of the parking spot to 'Reserved' when a new reservation is created
    UPDATE ParkingSpot
    SET status = 'Reserved'
    WHERE spot_id = NEW.spot_id;
END $$

DELIMITER ;

DROP EVENT IF EXISTS updateSpotStatuses;
-- Create an event to update parking spot statuses periodically
DELIMITER //

CREATE EVENT updateSpotStatuses
ON SCHEDULE EVERY 1 MINUTE
DO
BEGIN
    -- Update all parking spots for completed reservations with past end_times
    UPDATE ParkingSpot ps
    JOIN Reservation r ON ps.spot_id = r.spot_id
    SET ps.status = 'Available'
    WHERE r.end_time < NOW();
END//

DELIMITER ;
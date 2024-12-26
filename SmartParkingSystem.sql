USE SmartParkingSystem;

DROP TABLE IF EXISTS Sensor;
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
    latitude DECIMAL(9, 6) NOT NULL, -- Store latitude of the parking lot
    longitude DECIMAL(9, 6) NOT NULL -- Store longitude of the parking lot
);


-- Table: ParkingSpot
CREATE TABLE ParkingSpot (
    spot_id INT AUTO_INCREMENT PRIMARY KEY,
    lot_id INT,
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
    role_id INT,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) UNIQUE NOT NULL,
    user_phone VARCHAR(15),
    license_plate VARCHAR(45) UNIQUE NOT NULL,
    payment_method VARCHAR(50),
    FOREIGN KEY (role_id) REFERENCES Roles(role_id)
);

-- Table: Reservation
CREATE TABLE Reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    spot_id INT,
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
    reservation_id INT,
    amount DECIMAL(10,2) NOT NULL,
    payment_method ENUM('Credit_card', 'Debit_card', 'InstaPay', 'Wallet') NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id)
);


CREATE TABLE DynamicPricing (
    spot_id INT PRIMARY KEY,
    base_price DECIMAL(10, 2) NOT NULL,
    demand_level ENUM('Low', 'Medium', 'High') DEFAULT 'Low',
    location_factor DECIMAL(3, 2) DEFAULT 1.00,
    peak_factor DECIMAL(3, 2) DEFAULT 1.00,
    current_price DECIMAL(10, 2) GENERATED ALWAYS AS 
        (base_price * location_factor * peak_factor * 
        CASE demand_level 
            WHEN 'Low' THEN 1.00 
            WHEN 'Medium' THEN 1.50 
            WHEN 'High' THEN 2.00 
        END) STORED,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (spot_id) REFERENCES ParkingSpot(spot_id)
);

-- Table: Sensor
CREATE TABLE Sensor (
    sensor_id INT AUTO_INCREMENT PRIMARY KEY,
    spot_id INT,
    status ENUM('Free', 'Occupied') DEFAULT 'Free',
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (spot_id) REFERENCES ParkingSpot(spot_id)
);


-- Adding a Constraint to ensure start_time < end_time in Reservation table
ALTER TABLE Reservation 
ADD CONSTRAINT chk_start_end_time 
CHECK (start_time < end_time);

DELIMITER $$

CREATE TRIGGER update_peak_factor
AFTER INSERT ON Reservation
FOR EACH ROW
BEGIN
    DECLARE reserved_count INT;
    DECLARE total_count INT;
    DECLARE reservation_rate DECIMAL(5, 2);
    DECLARE lot_id INT;

    -- Get the lot ID for the reserved spot
    SELECT lot_id INTO lot_id
    FROM ParkingSpot
    WHERE spot_id = NEW.spot_id;

    -- Count reserved spots in the lot
    SELECT COUNT(*) INTO reserved_count
    FROM ParkingSpot
    WHERE lot_id = lot_id AND status = 'Reserved';

    -- Get total capacity directly from the ParkingLot table
    SELECT capacity INTO total_count
    FROM ParkingLot
    WHERE lot_id = lot_id;

    -- Calculate reservation rate
    SET reservation_rate = (reserved_count * 100.0) / total_count;

    -- Update the peak_factor for the specific spot based on the reservation rate
    IF reservation_rate > 80 THEN
        UPDATE DynamicPricing
        SET peak_factor = 2.00
        WHERE spot_id = NEW.spot_id;
    ELSEIF reservation_rate > 50 THEN
        UPDATE DynamicPricing
        SET peak_factor = 1.50
        WHERE spot_id = NEW.spot_id;
    ELSE
        UPDATE DynamicPricing
        SET peak_factor = 1.00
        WHERE spot_id = NEW.spot_id;
    END IF;
END $$

DELIMITER ;

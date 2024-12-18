Create Database SmartParkingSystem;

Use SmartParkingSystem;

-- Table: ParkingLot
CREATE TABLE ParkingLot (
    lot_id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    pricing_structure TEXT,
    types_of_spots VARCHAR(255)
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

-- Table: DynamicPricing
CREATE TABLE DynamicPricing (
    pricing_id INT AUTO_INCREMENT PRIMARY KEY,
    spot_id INT,
    price DECIMAL(10,2) NOT NULL,
    demand_level ENUM('Low', 'Medium', 'High') NOT NULL,
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


-- Adding a Constraint to ensure start_time < end_time in reservation table
ALTER TABLE Reservation 
ADD CONSTRAINT chk_start_end_time 
CHECK (start_time < end_time);



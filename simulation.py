import threading
import time
import random
import mysql.connector.pooling
from datetime import datetime, timezone

# Database connection pool configuration
# Connection pooling ensures efficient handling of multiple database connections
db_config = {
    "host": "localhost",  # Database host
    "user": "root",       # Database username
    "password": "your_password",  # Database password
    "database": "parking_management"  # Name of the database
}
connection_pool = mysql.connector.pooling.MySQLConnectionPool(pool_name="mypool", pool_size=5, **db_config)

# Class to simulate a single parking spot
class ParkingSpotSimulator:
    def __init__(self, spot_id):
        """
        Initialize a ParkingSpotSimulator instance.
        :param spot_id: Unique identifier for the parking spot
        """
        self.spot_id = spot_id
        self.probability = random.uniform(0.2, 0.8)  # Unique probability for this spot to be occupied

    def update_status(self):
        """
        Update the status of the parking spot in the database.
        The status can be 'occupied', 'available', or 'error' (simulating sensor malfunction).
        """
        # Get a connection from the connection pool
        conn = connection_pool.get_connection()
        cursor = conn.cursor()
        try:
           
            # Determine status based on the predefined probability
            status = "occupied" if random.random() < self.probability else "available"

            # Get the current UTC time
            now = datetime.now(timezone.utc)

            # Update the parking spot's status and timestamp in the database
            cursor.execute(
                "UPDATE parking_spots SET status = %s, last_updated = %s WHERE spot_id = %s",
                (status, now, self.spot_id)
            )
            conn.commit()  # Commit the transaction

            # Print a log for debugging
            print(f"Spot {self.spot_id} status updated to {status} at {now}")
        finally:
            # Always close the cursor and connection to prevent resource leaks
            cursor.close()
            conn.close()

# Class to simulate an entire parking lot
class ParkingLotSimulator:
    def __init__(self, num_spots):
        """
        Initialize a ParkingLotSimulator instance.
        :param num_spots: Total number of parking spots in the parking lot
        """
        # Create a list of ParkingSpotSimulator instances for all spots in the lot
        self.spots = [ParkingSpotSimulator(i) for i in range(1, num_spots + 1)]

    def update_all_spots(self):
        """
        Update the status of all parking spots in the lot.
        """
        for spot in self.spots:
            spot.update_status()

# Function to continuously update the parking lot's status
def update_parking_lot(parking_lot):
    """
    Periodically update the status of all parking spots in the lot.
    :param parking_lot: An instance of ParkingLotSimulator
    """
    while True:
        # Get the current update interval based on the time of day
        interval = get_update_interval()

        # Update the status of all spots in the parking lot
        parking_lot.update_all_spots()

        # Wait for the specified interval before the next update
        time.sleep(interval)

# Function to determine the update interval based on the time of day
def get_update_interval():
    """
    Calculate the update interval for the simulation based on the time of day.
    :return: Update interval in seconds
    """
    now = datetime.now().hour  # Get the current hour of the day
    if 8 <= now < 10 or 17 <= now < 19:  # Peak hours: 8-10 AM and 5-7 PM
        return 15  # Update every 2 seconds during peak hours
    return 30  # Update every 10 seconds during off-peak hours

# Main entry point for the simulation
if __name__ == "__main__":
    num_spots = 10  # Number of parking spots in the parking lot

    # Initialize the parking lot simulator
    parking_lot_simulator = ParkingLotSimulator(num_spots)

    # Create a background thread to periodically update the parking lot's status
    update_thread = threading.Thread(target=update_parking_lot, args=(parking_lot_simulator,))
    update_thread.daemon = True  # Daemonize the thread so it exits when the main program stops
    update_thread.start()

    # Keep the main program running to allow the background thread to work
    try:
        while True:
            time.sleep(1)  # Sleep to keep the program alive
    except KeyboardInterrupt:
        # Handle graceful shutdown on Ctrl+C
        print("Simulation stopped.")

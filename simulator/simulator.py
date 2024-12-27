# simulator/simulator.py
import threading
import time
import random
from pymongo import MongoClient
from datetime import datetime

# Connect to MongoDB
client = MongoClient("mongodb://localhost:27017/")
db = client.parking_management
collection = db.parking_spots

class ParkingSpotSimulator:
    def __init__(self, spot_id):
        self.spot_id = spot_id

    def update_status(self):
        status = "occupied" if random.choice([True, False]) else "available"
        collection.update_one(
            {"spot_id": self.spot_id},
            {"$set": {"status": status, "last_updated": datetime.utcnow()}}
        )

class ParkingLotSimulator:
    def __init__(self, num_spots):
        self.spots = [ParkingSpotSimulator(i) for i in range(1, num_spots + 1)]

    def update_all_spots(self):
        for spot in self.spots:
            spot.update_status()

def update_parking_lot(parking_lot, interval):
    while True:
        parking_lot.update_all_spots()
        time.sleep(interval)

if __name__ == "__main__":
    num_spots = 10  # Number of parking spots in the lot
    update_interval = 5  # Update interval in seconds

    parking_lot_simulator = ParkingLotSimulator(num_spots)

    # Create a thread to update the parking lot status periodically
    update_thread = threading.Thread(target=update_parking_lot, args=(parking_lot_simulator, update_interval))
    update_thread.daemon = True  # Daemonize thread to exit when the main program exits
    update_thread.start()

    # Keep the main program running
    while True:
        try:
            time.sleep(1)
        except KeyboardInterrupt:
            print("Simulation stopped.")
            break
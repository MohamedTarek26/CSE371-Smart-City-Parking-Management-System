class LotDTO {
    constructor(id, name, capacity, availableSpots, longitude, latitude) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.capacity = capacity;
        this.availableSpots = availableSpots;
    }
}

export default LotDTO;
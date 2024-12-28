class LotDTO {
    constructor(lotId, location, capacity, pricingStructure, typesOfSpots, longitude, latitude) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.pricingStructure = pricingStructure;
        this.typesOfSpots = typesOfSpots
        this.longitude = longitude;
        this.latitude = latitude;
        this.Spots = [];
        // this.availableSpots = availableSpots;
    }
}

export default LotDTO;
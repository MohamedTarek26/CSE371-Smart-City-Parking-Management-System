class SpotDTO {
    constructor(spotId, lotId, status, type, price=null, nextAvailable="Available Now") {
        this.spotId = spotId;
        this.lotId = lotId;
        this.status = status;
        this.type = type;
        this.price = price;
        this.nextAvailable = nextAvailable;
    }
}

export default SpotDTO;
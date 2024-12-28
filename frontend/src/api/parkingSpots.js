import API_URL, { endpoints } from './config'
import SpotDTO  from '../DTOs/SpotDTO'

// Parking spots API functions
export const parkingSpotsAPI = {

    // Get parking spot details
    // async getSpotDetails(spotId) {
    //     try {
    //         const response = await fetch(API_URL + endpoints.parkingSpots.details(spotId), {
    //             // credentials: 'include'
    //         })
    //         return await response.json()
    //     } catch (error) {
    //         console.error('Get spot details error:', error)
    //         throw error
    //     }
    // },

    // Get parking spot details
    async getSpotDetails(spotId) {
        try {
            const response = await fetch(API_URL + endpoints.parkingSpots.details(spotId), {
                // credentials: 'include'
            });
            const spot = await response.json();
            return new SpotDTO(spot.spotId, spot.lotId, spot.status, spot.type, spot.price);
        } catch (error) {
            console.error('Get spot DTO error:', error);
            throw error;
        }
    },

    async getAllSpotsForLot(lotId) {
        try {
            const response = await fetch(API_URL + endpoints.parkingSpots.allForLot(lotId), {
                // credentials: 'include'
            })
            const spots = await response.json();
            return spots.map(spot => ({
                id: spot.spotId,
                lotId: spot.lotId,
                status: spot.status,
                type: spot.type,
                // size: spot.size
            }));
        } catch (error) {
            console.error('Get all spots for lot error:', error)
            throw error
        }
    },

    async reserveSpot(reservationData) {
        try {
            const response = await fetch(API_URL + endpoints.reservations.reserve, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    user_id: reservationData.user_id,
                    spot_id: reservationData.spot_id,
                    start_time: reservationData.start_time,
                    end_time: reservationData.end_time
                }),
                // credentials: 'include'
                
            })
            return await response.text()
        } catch (error) {
            console.error('Reserve spot error:', error)
            throw error
        }
    },
    async getNextAvailableSpot(spotId) {
        try {
            const response = await fetch(API_URL + endpoints.parkingSpots.nextAvailable(spotId), {
                // credentials: 'include'
            })
            return response.text()
        } catch (error) {
            console.error('Get next available spot error:', error)
            throw error
        }
    },
    async cancelSpot(spotId) {
        try {
            const response = await fetch(API_URL + endpoints.parkingSpots.cancel(spotId), {
                method: 'POST',
                // credentials: 'include'
            })
            return await response.json()
        } catch (error) {
            console.error('Cancel spot error:', error)
            throw error
        }
    },
    async createSpot(spotData) {
        try {
            const response = await fetch(API_URL + endpoints.parkingSpots.create, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(spotData),
                // credentials: 'include'
            })
            return response
        } catch (error) {
            console.error('Create spot error:', error)
            throw error
        }
    }


}
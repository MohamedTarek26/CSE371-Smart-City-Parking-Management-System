import API_URL, { endpoints } from './config'

// Parking lots API functions
export const parkingLotsAPI = {
  // Search parking lots
  async search(query) {
    try {
      const response = await fetch(`${API_URL}${endpoints.parkingLots.search}?location=${query}`, {
        // credentials: 'include'
      })
      const data = await response.json()
      return data.map(lot => ({
        lotId: lot.lotId,
        location: lot.location,
        capacity: lot.capacity,
        pricingStructure: lot.pricingStructure,
        typesOfSpots: lot.typesOfSpots,
        latitude: lot.latitude,
        longitude: lot.longitude
      }))
    } catch (error) {
      console.error('Search parking lots error:', error)
      throw error
    }
  },

  // Get parking lot details
  async getLotDetails(lotId) {
    try {
      const response = await fetch(API_URL + endpoints.parkingLots.details(lotId), {
        // credentials: 'include'
      })
      const data = await response.json()
      return {
        lotId: data.lotId,
        location: data.location,
        capacity: data.capacity,
        pricingStructure: data.pricingStructure,
        typesOfSpots: data.typesOfSpots,
        latitude: data.latitude,
        longitude: data.longitude
      }
    } catch (error) {
      console.error('Get lot details error:', error)
      throw error
    }
  },

  // Toggle favorite parking lot
  async toggleFavorite(lotId) {
    try {
      const response = await fetch(API_URL + endpoints.parkingLots.favorite(lotId), {
        method: 'POST',
        // credentials: 'include'
      })
      const data = await response.json()
      return { id: data.id, ...data }
    } catch (error) {
      console.error('Toggle favorite error:', error)
      throw error
    }
  },

  async navigateToLot(lotId) {
    try {
      const response = await fetch(API_URL + endpoints.parkingLots.navigateToLot(lotId), {
        // credentials: 'include'
      })
      const data = await response.json()
      return { id: data.id, ...data }
    } catch (error) {
      console.error('Navigate to lot error:', error)
      throw error
    }
  },
  async addLot(lotData) {
    try {
      const response = await fetch(API_URL + endpoints.parkingLots.addLot, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(lotData)
      })
      const data = await response.json()
      // console.log('data of addLot: ', data)
      return data
    } catch (error) {
      console.error('Add lot error:', error)
      throw error
    }
  },
}
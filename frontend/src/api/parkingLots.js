import API_URL, { endpoints } from './config'

// Parking lots API functions
export const parkingLotsAPI = {
  // Search parking lots
  async search(query) {
    try {
      const response = await fetch(`${API_URL}${endpoints.parkingLots.search}?q=${query}`, {
        credentials: 'include'
      })
      return await response.json()
    } catch (error) {
      console.error('Search parking lots error:', error)
      throw error
    }
  },

  // Get parking lot details
  async getLotDetails(lotId) {
    try {
      const response = await fetch(API_URL + endpoints.parkingLots.details(lotId), {
        credentials: 'include'
      })
      return await response.json()
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
        credentials: 'include'
      })
      return await response.json()
    } catch (error) {
      console.error('Toggle favorite error:', error)
      throw error
    }
  }
}
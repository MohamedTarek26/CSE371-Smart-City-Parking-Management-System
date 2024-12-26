import API_URL, { endpoints } from './config'

// Reservations API functions
export const reservationsAPI = {
  // Get user's reservations
  async getUserReservations() {
    try {
      const response = await fetch(API_URL + endpoints.reservations.list, {
        credentials: 'include'
      })
      return await response.json()
    } catch (error) {
      console.error('Get reservations error:', error)
      throw error
    }
  },

  // Create new reservation
  async createReservation(spotId, reservationData) {
    try {
      const response = await fetch(API_URL + endpoints.reservations.create, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ spotId, ...reservationData })
      })
      return await response.json()
    } catch (error) {
      console.error('Create reservation error:', error)
      throw error
    }
  },

  // Cancel reservation
  async cancelReservation(reservationId) {
    try {
      const response = await fetch(API_URL + endpoints.reservations.cancel(reservationId), {
        method: 'POST',
        credentials: 'include'
      })
      return await response.json()
    } catch (error) {
      console.error('Cancel reservation error:', error)
      throw error
    }
  }
}
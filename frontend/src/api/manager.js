// Manager-specific API endpoints
// import { supabase } from './supabase'

export const managerAPI = {
  // Parking spot management
  async addParkingSpot(spotData) {
    // try {
    //   const { data, error } = await supabase
    //     .from('parking_spots')
    //     .insert([spotData])
      
    //   if (error) throw error
    //   return data
    // } catch (error) {
    //   console.error('Error adding parking spot:', error)
    //   throw error
    // }
  },

  async updateParkingSpot(spotId, updates) {
    // try {
    //   const { data, error } = await supabase
    //     .from('parking_spots')
    //     .update(updates)
    //     .eq('id', spotId)
      
    //   if (error) throw error
    //   return data
    // } catch (error) {
    //   console.error('Error updating parking spot:', error)
    //   throw error
    // }
  },

  async searchParkingSpots(query) {
    // try {
    //   const { data, error } = await supabase
    //     .from('parking_spots')
    //     .select('*')
    //     .or(`number.ilike.%${query}%,type.ilike.%${query}%`)
      
    //   if (error) throw error
    //   return data
    // } catch (error) {
    //   console.error('Error searching parking spots:', error)
    //   throw error
    // }
  }
}
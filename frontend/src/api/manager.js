// Manager-specific API endpoints
import API_URL, { endpoints } from './config'
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
  },
  async deleteParkingSpot(spotId) {
    // try {
    //   const { error } = await supabase
    //     .from('parking_spots')
    //     .delete()
    //     .eq('id', spotId)
      
    //   if (error) throw error
    // } catch (error) {
    //   console.error('Error deleting parking spot:', error)
    //   throw error
    // }
  },

  async getLotDetails(LotId) {
    try {
      const response = await fetch(API_URL + endpoints.parkingLots.getReport(LotId), {
        // credentials: 'include'
        method: 'GET',
      });

      // Check if the response is OK
      if (!response.ok) {
        throw new Error('Failed to fetch PDF');
      }

      // Convert the response to a Blob
      const blob = await response.blob();

      // Create a URL for the Blob
      const url = URL.createObjectURL(blob);

      // Trigger the download
      const link = document.createElement('a');
      link.href = url;
      link.download = `Lot${LotId}_report.pdf`; // Default filename
      link.click();

      // Cleanup the URL
      URL.revokeObjectURL(url);
  }catch (error) {
    console.error('Error getting parking lot details:', error)
    throw error
  }
  },
}


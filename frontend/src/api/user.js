import API_URL, { endpoints } from './config'

// User API functions
export const userAPI = {
  // Get user profile
  async getProfile() {
    try {
      const response = await fetch(API_URL + endpoints.user.profile, {
        credentials: 'include'
      })
      return await response.json()
    } catch (error) {
      console.error('Get profile error:', error)
      throw error
    }
  },

  // Update user profile
  async updateProfile(profileData) {
    try {
      const response = await fetch(API_URL + endpoints.user.updateProfile, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(profileData)
      })
      return await response.json()
    } catch (error) {
      console.error('Update profile error:', error)
      throw error
    }
  },

  // Get user settings
  async getSettings() {
    try {
      const response = await fetch(API_URL + endpoints.user.settings, {
        credentials: 'include'
      })
      return await response.json()
    } catch (error) {
      console.error('Get settings error:', error)
      throw error
    }
  },

  // Update user settings
  async updateSettings(settingsData) {
    try {
      const response = await fetch(API_URL + endpoints.user.settings, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(settingsData)
      })
      return await response.json()
    } catch (error) {
      console.error('Update settings error:', error)
      throw error
    }
  }
}
import API_URL, { endpoints } from './config'
import  UserDTO  from '../DTOs/UserDTO'
// User API functions
export const userAPI = {
  // Get user profile
  async getProfile(id) {
    try {
      const response = await fetch(API_URL + endpoints.user.profile(id), {
        // credentials: 'include'
      })
      console.log('response of getProfile: ', response)
      if (response.status === 200) {
        const data = await response.json();
        console.log('data of getProfile: ', data)
        return new UserDTO(
          data.userId,
          data.userName,
          data.userEmail,
          data.userPhone,
          data.roleId,
          data.licensePlate,
        )
      }
      console.log('Error: Non-200 status code: ', response.status)
      return null
      // return await response.json()
    } catch (error) {
      console.error('Get profile error:', error)
      throw error
    }
  },

  // Update user profile
  async updateProfile(profileData) {
    try {
      const response = await fetch(API_URL + endpoints.user.updateProfile(profileData.userId), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        // credentials: 'include',
        body: JSON.stringify(profileData)
      })
      return await response.text()
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
  },

  async getAllUsers() {
    try {
      const response = await fetch(API_URL + endpoints.user.allUsers, {
        // credentials: 'include'
      })
      const data = await response.json()
      // return data.map(user => new UserDTO(...Object.values(user).slice(0, 6)))
      return data.map(user => new UserDTO(
        user.userId,
        user.userName,
        user.userEmail,
        user.userPhone,
        user.roleId,
        user.licensePlate,
      ))
    } catch (error) {
      console.error('Get all users error:', error)
      throw error
    }
  },

  async upgradeUser(userId, roleId) {
    try {
      const response = await fetch(API_URL + endpoints.user.upgrade, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, roleId }),
        // credentials: 'include'
      })
      return await response.text()
    } catch (error) {
      console.error('Upgrade user error:', error)
      throw error
    }
  }
}
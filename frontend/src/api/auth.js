import API_URL, { endpoints } from './config'

// Authentication API functions
export const authAPI = {
  // Sign in user
  async signIn(email, password) {
    try {
      const response = await fetch(API_URL + endpoints.auth.signIn, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      })
      const data = await response.json()
      return data
    } catch (error) {
      console.error('Sign in error:', error)
      throw error
    }
  },

  // Sign up new user
  async signUp(userData) {
    try {
      const response = await fetch(API_URL + endpoints.auth.signUp, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData)
      })
      return response
    } catch (error) {
      console.error('Sign up error:', error)
      throw error
    }
  },

  // Sign out user
  async signOut() {
    try {
      const response = await fetch(API_URL + endpoints.auth.signOut, {
        method: 'POST',
        credentials: 'include'
      })
      return response
    } catch (error) {
      console.error('Sign out error:', error)
      throw error
    }
  }
}
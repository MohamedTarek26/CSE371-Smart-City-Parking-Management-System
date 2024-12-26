// API configuration
const API_URL = 'http://localhost:3000/api'  // Replace with your actual API URL

export const endpoints = {
  // Auth endpoints
  auth: {
    signIn: '/auth/signin',
    signUp: '/auth/signup',
    signOut: '/auth/signout'
  },
  // User endpoints
  user: {
    profile: '/user/profile',
    updateProfile: '/user/profile/update',
    settings: '/user/settings'
  },
  // Parking lots endpoints
  parkingLots: {
    search: '/parking-lots/search',
    details: (id) => `/parking-lots/${id}`,
    favorite: (id) => `/parking-lots/${id}/favorite`
  },
  // Parking spots endpoints
  parkingSpots: {
    details: (id) => `/spots/${id}`,
    reserve: (id) => `/spots/${id}/reserve`,
    cancel: (id) => `/spots/${id}/cancel`
  },
  // Reservations endpoints
  reservations: {
    list: '/reservations',
    create: '/reservations/create',
    cancel: (id) => `/reservations/${id}/cancel`
  }
}

export default API_URL
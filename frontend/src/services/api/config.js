// API configuration
const API_URL = 'http://localhost:3000/api'  // Replace with your actual API URL
import { loadToken } from '../storage'
import axios from 'axios'

export const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Add Authorization Token for Requests
api.interceptors.request.use(
  (config) => {
    const token = loadToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Check for Authenticity
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      // Redirect to login on 401
      this.$router.push({ name: "Login" });
    }
    return Promise.reject(error);
  }
);

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

export const fetchData = async (endpoint, DTO) => {
  try {
    const response = await api.get(endpoint);
    return new DTO(response.data);
  } catch (error) {
    console.error(`Error fetching data from ${endpoint}:`, error);
    throw error;
  }
};

export const postData = async (endpoint, data) => {
  try {
    const response = await api.post(endpoint, data);
    return response.data;
  } catch (error) {
    console.error(`Error posting data to ${endpoint}:`, error);
    throw error;
  }
};

export const putData = async (endpoint, data) => {
  try {
    const response = await api.put(endpoint, data);
    return response.data;
  } catch (error) {
    console.error(`Error putting data to ${endpoint}:`, error);
    throw error;
  }
};

export default API_URL
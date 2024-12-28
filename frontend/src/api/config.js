// API configuration
const API_URL = 'http://192.168.1.11:8080/api'  // Replace with your actual API URL
import { loadToken } from '../services/storage'
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
    signIn: '/auth/login',
    signUp: '/auth/signup',
    // signOut: '/auth/signout'
  },
  // User endpoints
  user: {
    profile: (id) => `/users/${id}`,
    updateProfile: (id) => `/users/update/${id}`,
    settings: (id) => `/user/settings/${id}`,
    allUsers: '/users/get-all',
    upgrade: '/users/updateRole',
  },
  // Parking lots endpoints
  parkingLots: {
    search: '/parking-lots/search',
    details: (id) => `/parking-lots/${id}`,
    favorite: (id) => `/parking-lots/${id}/favorite`,
    all: '/parking-lots/all',
    navigateToLot: (id) => `/navigation/toLot/String/${id}`,
    getReport: (id) => `/reports/download/${id}`,
    addLot: '/parking-lots',

  },
  // Parking spots endpoints
  parkingSpots: {
    details: (id) => `/parking-spots/with_price/${id}`,
    cancel: (id) => `/spots/${id}/cancel` , 
    allForLot: (lotId) => `/parking-lots/spots/${lotId}`,
    nextAvailable: (spotId) => `/parking-spots/next_available/${spotId}`,
    create: '/parking-spots/create',
  },
  // Reservations endpoints
  reservations: {
    list: '/reservations',
    all: '/reservations/get-all',
    userReservation: (id) => `/reservations/user/${id}`,
    // create: '/reservations/create',
    reserve: '/reservations/reserve',
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
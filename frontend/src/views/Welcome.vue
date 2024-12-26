<script setup>
import { useRouter } from 'vue-router'

// Initialize router for navigation
const router = useRouter()

// Navigation handler functions
const navigateToSignIn = () => {
  router.push('/signin')
}

const navigateToSignUp = () => {
  router.push('/signup')
}
</script>

<template>
  <!-- Main container with full height and centered content -->
  <div class="min-h-screen bg-gray-100 flex flex-col items-center justify-center">
    <!-- Welcome card -->
    <div class="max-w-md w-full space-y-8 p-8 bg-white rounded-lg shadow-lg">
      <!-- Header section -->
      <div class="text-center">
        <h1 class="text-4xl font-bold text-gray-900 mb-2">
          Welcome to Smart Parking
        </h1>
        <p class="text-gray-600">
          Find and reserve parking spots in real-time
        </p>
      </div>
      
      <!-- Action buttons -->
      <div class="space-y-4">
        <!-- Sign In button -->
        <button
          @click="navigateToSignIn"
          class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
        >
          Sign In
        </button>
        
        <!-- Sign Up button -->
        <button
          @click="navigateToSignUp"
          class="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
        >
          Sign Up
        </button>
      </div>
    </div>
  </div>
</template>
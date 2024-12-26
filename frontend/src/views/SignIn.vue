<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

// Initialize router for navigation
const router = useRouter()

// Form data refs
const email = ref('')
const password = ref('')

// Handle form submission
const handleSignIn = async () => {
  // TODO: Implement authentication logic with Supabase
  router.push('/dashboard/account')
}

// Navigation to sign up page
const navigateToSignUp = () => {
  router.push('/signup')
}
</script>

<template>
  <!-- Main container -->
  <div class="min-h-screen bg-gray-100 flex flex-col items-center justify-center">
    <!-- Sign In card -->
    <div class="max-w-md w-full space-y-8 p-8 bg-white rounded-lg shadow-lg">
      <!-- Header -->
      <div class="text-center">
        <h2 class="text-3xl font-bold text-gray-900">Sign In</h2>
        <p class="mt-2 text-gray-600">Welcome back!</p>
      </div>
      
      <!-- Sign In form -->
      <form class="mt-8 space-y-6" @submit.prevent="handleSignIn">
        <div class="space-y-4">
          <!-- Email input -->
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700">
              Email address
            </label>
            <input
              id="email"
              v-model="email"
              type="email"
              required
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>
          
          <!-- Password input -->
          <div>
            <label for="password" class="block text-sm font-medium text-gray-700">
              Password
            </label>
            <input
              id="password"
              v-model="password"
              type="password"
              required
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>
        </div>

        <!-- Submit button -->
        <div>
          <button
            type="submit"
            class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
          >
            Sign In
          </button>
        </div>
      </form>

      <!-- Sign up link -->
      <div class="text-center">
        <p class="text-sm text-gray-600">
          Don't have an account?
          <button
            @click="navigateToSignUp"
            class="font-medium text-blue-600 hover:text-blue-500"
          >
            Sign up
          </button>
        </p>
      </div>
    </div>
  </div>
</template>
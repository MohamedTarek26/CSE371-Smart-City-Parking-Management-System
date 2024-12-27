<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authAPI } from '../api/auth'

const router = useRouter()
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const username = ref('')
const licensePlate = ref('')
const phoneNumber = ref('')

const handleSignUp = async () => {
  // TODO: Implement Supabase authentication
  if (!validateForm()) {
    alert('Invalid form data')
    return
  }
  const res = await authAPI.signUp(
    {
      userName: username.value,
      userEmail: email.value,
      userPhone: phoneNumber.value,
      licensePlate: licensePlate.value,
      paymentMethod: 'Credit Card',
      password: password.value
    }
  )
  if (res.error) {
    alert(res.error)
    return
  }
  console.log('Signing up:', username.value, email.value, licensePlate.value, password.value, confirmPassword.value)
  router.push('/signin')
}

const validateEmail = (email) => {
  const re = /\S+@\S+\.\S+/
  return re.test(email)
}

const validatePassword = (password) => {
  return password.length >= 6
}

const validateCarPlate = (carPlate) => {
  return carPlate.length >= 6
}

const confirmPasswordTheSame = (password, confirmPassword) => {
  return password === confirmPassword
}

const validateForm = () => {
  return validateEmail(email.value) && validatePassword(password.value) && validateCarPlate(licensePlate.value) && confirmPasswordTheSame(password.value, confirmPassword.value)
}

</script>

<template>
  <div class="min-h-screen bg-gray-100 flex flex-col items-center justify-center">
    <div class="max-w-md w-full space-y-8 p-8 bg-white rounded-lg shadow-lg">
      <div class="text-center">
        <h2 class="text-3xl font-bold text-gray-900">Create Account</h2>
        <p class="mt-2 text-gray-600">Join Smart Parking today</p>
      </div>
      
      <form class="mt-8 space-y-6" @submit.prevent="handleSignUp">
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700">Username</label>
            <input
              v-model="username"
              type="text"
              required
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">Email</label>
            <input
              v-model="email"
              type="email"
              required
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">License Plate</label>
            <input
              v-model="licensePlate"
              type="text"
              required
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">Phone Number</label>
            <input
              v-model="phoneNumber"
              type="tel"
              required
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700">Password</label>
            <input
              v-model="password"
              type="password"
              required
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">Confirm Password</label>
            <input
              v-model="confirmPassword"
              type="password"
              required
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>
        </div>

        <button
          type="submit"
          class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
        >
          Sign Up
        </button>
      </form>
    </div>
  </div>
</template>
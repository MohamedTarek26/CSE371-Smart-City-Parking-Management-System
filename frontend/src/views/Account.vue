<script setup>
import { onMounted, ref } from 'vue'
import {isAuthenticated, loadUserId} from '../services/storage'
import { useRouter } from 'vue-router'
import { authAPI } from '../api/auth'

const user = ref({
  name: 'John Doe',
  email: 'john@example.com',
  licensePlate: 'ABC123',
  joinDate: '2024-01-01'
})

const updateProfile = async () => {
  // TODO: Implement profile update logic
  
  // const token = localStorage.getItem('token')
  // if (token) {
  //   const payload = JSON.parse(atob(token.split('.')[1]))
  //   if (payload.userId) {
  //     console.log('User ID found in token:', payload.userId)
  //   } else {
  //     console.log('User ID not found in token')
  //   }
  // } else {
  //   console.log('No token found')
  // }
  console.log('Updating profile:', user.value)
}
onMounted(() => {
  console.log('Account page mounted')
  if(!isAuthenticated())
  {
    alert("You are not authenticated")
    const router = useRouter()
    router.push('/signin')
    return
  }
  let id = loadUserId()
  console.log('User ID:', id)
})
</script>

<template>
  <div class="max-w-2xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Account Settings</h2>
    
    <div class="bg-white rounded-lg shadow p-6">
      <form @submit.prevent="updateProfile" class="space-y-6">
        <div>
          <label class="block text-sm font-medium text-gray-700">Username</label>
          <input
            v-model="user.name"
            type="text"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">Email</label>
          <input
            v-model="user.email"
            type="email"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">License Plate</label>
          <input
            v-model="user.licensePlate"
            type="text"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <button
          type="submit"
          class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Update Profile
        </button>
      </form>
    </div>
  </div>
</template>
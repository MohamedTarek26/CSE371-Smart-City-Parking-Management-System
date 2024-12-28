<script setup>
import { onMounted, ref } from 'vue'
import { isAuthenticated, loadUserId, loadUserRoleId, clearToken, clearUserId, clearUserRoleId} from '../services/storage'
import { useRouter } from 'vue-router'
import { userAPI } from '../api/user'

const user = ref(null)
const router = useRouter()

const fetchUser = async () => {
  let id = loadUserId()
  console.log('User ID:', id)
  let response = await userAPI.getProfile(id)
  console.log('User profile:', response)
  if (response) {
    user.value = response
  } else {
    alert("Failed to load user profile")
    goToSignIn()
  }
}

const updateProfile = async () => {
  console.log('Updating profile:', user.value)
  let response = await userAPI.updateProfile(user.value)
  if (response === null) {
    alert("Failed to update profile")
    return
  }
  alert("Profile updated successfully")
}


onMounted(async () => {
  console.log('Account page mounted')
  if (!isAuthenticated()) {
    alert("You are not authenticated")
    goToSignIn()
    return
  }
  await fetchUser()
})
</script>

<template>
  <div class="max-w-2xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Account Settings</h2>
    
    <div class="bg-white rounded-lg shadow p-6" v-if="user">
      <form @submit.prevent="updateProfile" class="space-y-6">
        <div>
          <label class="block text-sm font-medium text-gray-700">Username</label>
          <input
            v-model="user.userName"
            type="text"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">Email</label>
          <input
            v-model="user.userEmail"
            type="email"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div v-if="user.roleId !== 2 && user.roleId !== 1">
          <label class="block text-sm font-medium text-gray-700">License Plate</label>
          <input
            v-model="user.licensePlate"
            type="text"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">Phone</label>
          <input
            v-model="user.userPhone"
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

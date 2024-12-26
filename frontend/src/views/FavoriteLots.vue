<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const favoriteLots = ref([
  {
    id: 1,
    name: 'Downtown Parking',
    address: '123 Main St',
    availableSpots: 45,
    totalSpots: 100,
    latitude: 37.7749,
    longitude: -122.4194,
    locationName: 'Empire State Building'
  },
  {
    id: 2,
    name: 'Uptown Parking',
    address: '456 Elm St',
    availableSpots: 30,
    totalSpots: 50,
    latitude: 37.7749,
    longitude: -122.4194,
    locationName: 'Empire State Building'
  }
])

const viewLot = (id) => {
  router.push(`/lot/${id}`)
}

const removeFavorite = async (id) => {
  // TODO: Implement remove from favorites logic
  console.log('Removing from favorites:', id)
}
</script>

<template>
  <div class="max-w-4xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Favorite Parking Lots</h2>
    
    <div class="grid gap-6">
      <div
        v-for="lot in favoriteLots"
        :key="lot.id"
        class="bg-white rounded-lg shadow p-6"
      >
        <div class="flex justify-between items-center">
          <div>
            <h3 class="text-lg font-medium">{{ lot.name }}</h3>
            <p class="text-sm text-gray-500">{{ lot.address }}</p>
            <p class="text-sm text-gray-500">
              Available: {{ lot.availableSpots }}/{{ lot.totalSpots }} spots
            </p>
          </div>
          <div class="space-x-4">
            <button
              @click="viewLot(lot.id)"
              class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
            >
              View
            </button>
            <button
              @click="removeFavorite(lot.id)"
              class="px-4 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-gray-300"
            >
              Remove
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
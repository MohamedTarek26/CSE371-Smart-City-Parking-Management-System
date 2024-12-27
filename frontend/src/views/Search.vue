<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const Lots = ref([
    {
        id: 1,
        name: 'Downtown Parking',
        address: '123 Main St',
        totalSpots: 100,
        availableSpots: 45,
        pricePerHour: 5,
    },
    {
        id: 2,
        name: 'Uptown Parking',
        address: '456 Elm St',
        totalSpots: 80,
        availableSpots: 20,
        pricePerHour: 4,
    }
])

const viewLot = (lotId) => {
    router.push(`/lot/${lotId}`)
}

onMounted(() => {
    console.log('Fetching favorite parking lots')
})
</script>

<template>
  <div class="max-w-4xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Search Results</h2>
    
    <div class="grid gap-6">
      <div
        v-for="lot in Lots"
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
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.btn {
    padding: 0.5rem 1rem;
    background-color: #3182ce;
    color: white;
    border-radius: 0.375rem;
    cursor: pointer;
}
.btn:hover {
    background-color: #2c5282;
}
</style>

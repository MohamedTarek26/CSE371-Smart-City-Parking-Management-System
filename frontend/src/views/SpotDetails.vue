<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const spot = ref({
  id: route.params.id,
  number: 'A12',
  status: 'available',
  pricePerHour: 5,
  nextAvailable: '2024-03-20 16:00'
})

const reservation = ref({
  startTime: '',
  duration: 1
})

const reserveSpot = async () => {
  // TODO: Implement reservation logic
  console.log('Reserving spot:', {
    spotId: spot.value.id,
    ...reservation.value
  })
}

onMounted(() => {
  // TODO: Fetch spot data
  console.log('Fetching data for spot:', route.params.id)
})
</script>

<template>
  <div class="max-w-2xl mx-auto p-6">
    <div class="bg-white rounded-lg shadow-lg p-6">
      <h2 class="text-2xl font-bold mb-4">Spot {{ spot.number }}</h2>
      
      <div class="mb-6">
        <div class="flex items-center mb-2">
          <div
            :class="[
              'w-4 h-4 rounded-full mr-2',
              spot.status === 'available' ? 'bg-green-500' : 'bg-red-500'
            ]"
          ></div>
          <span class="capitalize">{{ spot.status }}</span>
        </div>
        <p class="text-gray-600">${{ spot.pricePerHour }}/hour</p>
      </div>

      <form v-if="spot.status === 'available'" @submit.prevent="reserveSpot" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700">Start Time</label>
          <input
            v-model="reservation.startTime"
            type="datetime-local"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">Duration (hours)</label>
          <input
            v-model="reservation.duration"
            type="number"
            min="1"
            max="24"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div class="mt-4">
          <p class="text-lg font-semibold">
            Total: ${{ spot.pricePerHour * reservation.duration }}
          </p>
        </div>

        <button
          type="submit"
          class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Reserve Spot
        </button>
      </form>

      <div v-else class="text-center p-4 bg-gray-50 rounded-md">
        <p class="text-gray-600">
          This spot is currently occupied.
          <br>
          Next available at: {{ spot.nextAvailable }}
        </p>
      </div>
    </div>
  </div>
</template>
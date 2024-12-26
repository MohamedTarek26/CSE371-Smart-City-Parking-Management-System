<script setup>
import { ref } from 'vue'

const reservations = ref([
  {
    id: 1,
    lotName: 'Downtown Parking',
    spotNumber: 'A12',
    startTime: '2024-03-20 14:00',
    endTime: '2024-03-20 16:00',
    status: 'active'
  }
])

const cancelReservation = async (id) => {
  // TODO: Implement cancellation logic
  console.log('Cancelling reservation:', id)
}
</script>

<template>
  <div class="max-w-4xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Reserved Spots</h2>
    
    <div class="bg-white rounded-lg shadow overflow-hidden">
      <div class="divide-y divide-gray-200">
        <div
          v-for="reservation in reservations"
          :key="reservation.id"
          class="p-6"
        >
          <div class="flex justify-between items-center">
            <div>
              <h3 class="text-lg font-medium">{{ reservation.lotName }}</h3>
              <p class="text-sm text-gray-500">Spot: {{ reservation.spotNumber }}</p>
              <p class="text-sm text-gray-500">
                {{ reservation.startTime }} - {{ reservation.endTime }}
              </p>
            </div>
            <button
              @click="cancelReservation(reservation.id)"
              class="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
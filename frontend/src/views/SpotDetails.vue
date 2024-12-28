<script setup>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { parkingSpotsAPI } from '../api/parkingSpots'
import { loadUserId } from '../services/storage'

const route = useRoute()

const spot = ref({
  id: route.params.id,
  number: 'A12',
  status: 'available',
  pricePerHour: 5,
  nextAvailable: '2024-03-20 16:00'
})

const reservation = ref({
  duration: 1
})

const reserveSpot = async () => {
  // TODO: Implement reservation logic
  const startTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
  const endTime = new Date(new Date().getTime() + reservation.value.duration * 60 * 60 * 1000)
    .toISOString()
    .slice(0, 19)
    .replace('T', ' ');

  console.log('Reserving spot:', {
    user_id: loadUserId(),
    spot_id: spot.value.spotId,
    start_time: startTime,
    end_time: endTime,
  });
  console.log('Responseassawaw:', JSON.stringify({
    user_id: 5,
    spot_id: spot.value.spotId,
    start_time: startTime,
    end_time: endTime,
                }));
  let response = await parkingSpotsAPI.reserveSpot({
    user_id: 5,
    spot_id: spot.value.spotId,
    start_time: startTime,
    end_time: endTime,
  });
  if (response.error) {
    alert('Failed to reserve spot');
    return;
  }
  console.log('Spot reserved successfully', response);
  alert('Spot reserved successfully');
  // window.location.reload();
}

watch(
  () => route.params.id, // Watching the specific query parameter
  async (newId) => {
    console.log('Fetching data details for spot:', newId)
    let response = await parkingSpotsAPI.getSpotDetails(newId)
    if (response) {
      spot.value = response
      let nextAvailable = await parkingSpotsAPI.getNextAvailableSpot(newId)
      spot.value.nextAvailable = nextAvailable
      console.log('Spot details:', spot.value)
      console.log('Next available:', nextAvailable)
    }
  },
  { immediate: true } // Run immediately when the component is created)
);
</script>

<template>
  <div class="max-w-2xl mx-auto p-6">
    <div class="bg-white rounded-lg shadow-lg p-6">
      <h2 class="text-2xl font-bold mb-4">Lot {{ spot.lotId }}</h2>
      <h2 class="text-2xl font-bold mb-4">Spot {{ spot.spotId }}</h2>
      <span v-if="spot.type === 'EV Charging'">⚡</span>
      <span v-else-if="spot.type === 'Disabled'">♿</span>
      <span v-else>🚗</span>
      
      <div class="mb-6">
        <div class="flex items-center mb-2">
          <div
            :class="[
              'w-4 h-4 rounded-full mr-2',
              spot.status === 'Available' ? 'bg-green-500' : 'bg-red-500'
            ]"
          ></div>
          <span class="capitalize">{{ spot.status }}</span>
        </div>
        <p class="text-gray-600">${{ spot.price }}/hour</p>
      </div>

      <form v-if="spot.status === 'Available'" @submit.prevent="reserveSpot" class="space-y-4">
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
            Total: ${{ spot.price * reservation.duration }}
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

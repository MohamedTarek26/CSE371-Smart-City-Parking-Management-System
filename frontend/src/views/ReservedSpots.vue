<script setup>
import { ref } from 'vue'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { reservationsAPI } from '../api/reservations'
import { loadUserRoleId, loadUserId } from '../services/storage'
const reservations = ref([
  {
    id: 1,
    lotName: 'Downtown Parking',
    spotNumber: 'A12',
    startTime: '2024-03-20 14:00',
    endTime: '2024-03-20 16:00',
    status: 'active',
    latitude: 37.7749,
    longitude: -122.4194,
    locationName: 'Empire State Building'
  },
  {
    id: 2,
    lotName: 'Uptown Parking',
    spotNumber: 'B34',
    startTime: '2024-03-21 10:00',
    endTime: '2024-03-21 12:00',
    status: 'active',
    latitude: 37.7749,
    longitude: -122.4194,
    locationName: 'Empire State Building'
  }
])

onMounted(async () => {
  console.log('ReservedSpots component has been mounted');
  if (!isNormalUser()) {
    console.log('User is a customer');
    let response = await reservationsAPI.getUserReservations(loadUserId())
    reservations.value = response
    console.log("USER Response is: ",response)
  } else {
    console.log('User is an admin or manager');
    let response = await reservationsAPI.getAllReservations()
    reservations.value = response
    console.log("ADMIN Response is: ",response)
  }
});

const isNormalUser = () => {
  return loadUserRoleId() !== 1 && loadUserRoleId() !== 2
}

const redirectToGoogleMaps = async (parkingLot) => {
      // Replace with your desired coordinates or search string
      const latitude = 40.748817;
      const longitude = -73.985428;
      const locationName = 'Empire State Building';
      
      // Open Google Maps with either coordinates or a search query
      const googleMapsUrl = `https://www.google.com/maps?q=${parkingLot.latitude},${parkingLot.longitude}`;
      // let response = await parkingLotsAPI.navigateToLot(route.params.id)
      // if (!response.error) {
      //   alert('Failed to get navigation data')
      //   return
      // }
      console.log("Response is: ",response)
      // const googleMapsUrl = response.url
      // Or using a search string
      // const googleMapsUrl = `https://www.google.com/maps/search/${encodeURIComponent(locationName)}`;
      
      // Redirect to the URL
      window.open(googleMapsUrl, '_blank');
}

const cancelReservation = async (id) => {
  // TODO: Implement cancellation logic
  console.log('Cancelling reservation:', id)
}
</script>

<template>
  <div class="max-w-4xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Reserved Spots</h2>
    
    <!-- <div class="bg-white rounded-lg shadow overflow-hidden"> -->
      <div class="grid gap-6">
        <div
          v-for="reservation in reservations"
          :key="reservation.id"
          class="bg-white rounded-lg shadow p-6"
        >
          <div class="flex justify-between items-center">
            <div>
              <h3 class="text-lg font-medium">Reservation ID: {{ reservation.reservationId }}</h3>
              <p class="text-sm text-gray-500">User ID: {{ reservation.userId }}</p>
              <p class="text-sm text-gray-500">Spot ID: {{ reservation.spotId }}</p>
              <p class="text-sm text-gray-500">Start Time: {{ reservation.startTime }}</p>
              <p class="text-sm text-gray-500">End Time: {{ reservation.endTime }}</p>
              <p class="text-sm text-gray-500">Status: {{ reservation.status }}</p>
              <p v-if="reservation.penalty" class="text-sm text-gray-500">Penalty: {{ reservation.penalty }}</p>
            </div>
            <div class="space-x-4">
            <button v-if="isNormalUser" @click="redirectToGoogleMaps(reservation)" class="btn">Go to Location</button>

            <button
              @click="cancelReservation(reservation.id)"
              class="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700"
            >
          remove
            </button>
          </div>
          </div>
        </div>
        <div v-if="!reservations || reservations.length === 0" class="bg-white rounded-lg shadow p-6">
          <p class="text-lg text-gray-500">No reservations found</p>  
        </div>
      </div>
    <!-- </div> -->
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
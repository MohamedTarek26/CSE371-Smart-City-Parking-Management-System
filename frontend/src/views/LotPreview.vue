<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const parkingLot = ref({
  id: route.params.id,
  name: 'Downtown Parking',
  address: '123 Main St',
  totalSpots: 100,
  availableSpots: 45,
  pricePerHour: 5,
  spots: Array.from({ length: 100 }, (_, i) => ({
    id: i + 1,
    number: `A${i + 1}`,
    status: Math.random() > 0.5 ? 'available' : 'occupied',
    nextAvailable: '2024-03-20 16:00'
  })),
  latitude: 37.7749,
  longitude: -122.4194,
  locationName: 'Empire State Building'
})

const viewSpotDetails = (spotId) => {
  router.push(`/spot/${spotId}`)
}
const redirectToGoogleMaps = () => {
      // Replace with your desired coordinates or search string
      // const latitude = 40.748817;
      // const longitude = -73.985428;
      // const locationName = 'Empire State Building';
      
      // Open Google Maps with either coordinates or a search query
      const googleMapsUrl = `https://www.google.com/maps?q=${parkingLot.latitude},${parkingLot.longitude}`;
      // Or using a search string
      // const googleMapsUrl = `https://www.google.com/maps/search/${encodeURIComponent(locationName)}`;
      
      // Redirect to the URL
      window.open(googleMapsUrl, '_blank');
}

onMounted(() => {
  console.log('Fetching data for lot:', route.params.id)
})
</script>

<template>
  <div class="max-w-6xl mx-auto p-4">
    <div class="bg-white rounded-lg shadow-lg p-4 md:p-6">
      <!-- Lot Information -->
      <div class="mb-6">
        <div key="lot.id" class="flex items-center justify-between">
          
        <h2 class="text-xl md:text-2xl font-bold">{{ parkingLot.name }}</h2>
          <button @click="redirectToGoogleMaps" class="btn">Go to Location</button>
        </div>
        
        <p class="text-gray-600">{{ parkingLot.address }}</p>
        <div class="flex flex-col md:flex-row md:items-center gap-2 md:gap-4 mt-2">
          <p class="text-gray-600">
            Available: {{ parkingLot.availableSpots }}/{{ parkingLot.totalSpots }} spots
          </p>
          <p class="text-gray-600">${{ parkingLot.pricePerHour }}/hour</p>
        </div>
      </div>

      <!-- Parking Spot Grid -->
      <div class="grid grid-cols-5 md:grid-cols-10 gap-2">
        <button
          v-for="spot in parkingLot.spots"
          :key="spot.id"
          @click="viewSpotDetails(spot.id)"
          :class="[
            'p-2 rounded-md text-center text-sm',
            spot.status === 'available'
              ? 'bg-green-100 hover:bg-green-200 text-green-800'
              : 'bg-red-100 text-red-800'
          ]"
        >
          {{ spot.number }}
        </button>
      </div>

      <!-- Legend -->
      <div class="mt-6 flex flex-wrap gap-4">
        <div class="flex items-center">
          <div class="w-4 h-4 bg-green-100 rounded mr-2"></div>
          <span class="text-sm">Available</span>
        </div>
        <div class="flex items-center">
          <div class="w-4 h-4 bg-red-100 rounded mr-2"></div>
          <span class="text-sm">Occupied</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.btn {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 8px;
  padding: 10px 24px;
}
.btn:hover {
  background-color: #45a049;
}
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
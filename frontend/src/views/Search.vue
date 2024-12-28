<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { parkingLotsAPI } from '../api/parkingLots'
import { loadUserRoleId, isAuthenticated } from '../services/storage'
import { managerAPI } from '../api/manager'

// const route = useRoute()
const router = useRouter()
let Lots = ref(null)
let toggler = ref(false)

// const Lots = ref([
//     {
//         id: 1,
//         name: 'Downtown Parking',
//         address: '123 Main St',
//         totalSpots: 100,
//         availableSpots: 45,
//         pricePerHour: 5,
//     },
//     {
//         id: 2,
//         name: 'Uptown Parking',
//         address: '456 Elm St',
//         totalSpots: 80,
//         availableSpots: 20,
//         pricePerHour: 4,
//     },
//     {
//         id: 3,
//         name: 'Midtown Parking',
//         address: '789 Oak St',
//         totalSpots: 50,
//         availableSpots: 10,
//         pricePerHour: 3,
//     },
//     {
//         id: 4,
//         name: 'Suburban Parking',
//         address: '101 Pine St',
//         totalSpots: 200,
//         availableSpots: 100,
//         pricePerHour: 2,
//     },
//     {
//         id: 5,
//         name: 'Rural Parking',
//         address: '202 Maple St',
//         totalSpots: 30,
//         availableSpots: 5,
//         pricePerHour: 1,
//     },

// ])

const getReport = (lotId) => {
    let response = managerAPI.getLotDetails(lotId)
    console.log('Lot details:', response)
    // router.push(`/report/${lotId}`)
}

const isNormalUser = () => {
    // return loadUserRoleId() !== 3 && loadUserRoleId() !== 2
    // console.log('User role:', loadUserRoleId())
    return false;
}

const viewLot = (lotId) => {
    router.push(`/lot/${lotId}`)
}
const addToFavorites = (lotId) => {
    console.log('Adding to favorites:', lotId)
}

const getSearchResults = async (query) => {
  console.log('Fetching search results for:', query);
  try {
    const response = await parkingLotsAPI.search(query);
    Lots.value = response; // Assign fetched results to Lots
    toggler.value=!toggler
    console.log('Search results:', Lots);
  } catch (error) {
    console.error('Error fetching search results:', error);
  }
};

watch(
  () => router.currentRoute.value.query.location, // Watching the specific query parameter
  (newQuery) => {
    console.log('Search query changed:', newQuery);
    getSearchResults(newQuery); // Fetch results whenever query changes
  },
  { immediate: true } // Run immediately when the component is created
);

// onMounted(async () => {
//     console.log('Fetching favorite parking lots')
//     let searchQuery = router.currentRoute.value.query.loact
//     console.log('Search query henaaaaaaaaaaaa:', searchQuery)
//     Lots = getSearchResults(searchQuery)
//     console.log('Search results:', Lots)
// })
</script>

<template>
  <div class="max-w-4xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Search Results</h2>
    
    <div class="lots grid gap-6">
      <div
        v-for="lot in Lots"
        :key="lot.lotId"
        class="bg-white rounded-lg shadow p-6"
      >
        <div class="flex justify-between items-center">
          <div>
            <h3 class="text-lg font-medium">{{ lot.location }}</h3>
            <p class="text-sm text-gray-500">Capacity {{ lot.capacity }}</p>
            <p class="text-sm text-gray-500">Pricing Structure {{ lot.pricingStructure }}</p>
            <p class="text-sm text-gray-500">
              <!-- Available: {{ lot.availableSpots }}/{{ lot.totalSpots }} spots -->
              Type of spots: {{ lot.typesOfSpots }}
            </p>
          </div>
          <div class="space-x-4">
          
            <button
              @click="viewLot(lot.lotId)"
              class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
            >
              View
            </button>
            <button
            v-if="isNormalUser()"
            @click="addToFavorites(lot.lotId)"
            class="px-4 py-2 bg-yellow-400 text-white rounded-md hover:bg-yellow-500"
          >
            ★
          </button>
          <button
            v-else
            @click="getReport(lot.lotId)"
            class="px-4 py-2 bg-yellow-400 text-white rounded-md hover:bg-yellow-500"
          >
            Report
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
.lots {
    /* grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); */
    height: 100vh;
    overflow: auto;
}
</style>

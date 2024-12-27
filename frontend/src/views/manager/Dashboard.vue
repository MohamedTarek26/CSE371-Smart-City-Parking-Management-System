<!-- Updated Manager Dashboard -->
<script setup>
import { ref, onMounted } from 'vue'
import { managerAPI } from '../../api/manager'
import SpotForm from '../../components/manager/SpotForm.vue'

const searchQuery = ref('')
const spots = ref([])
const selectedSpot = ref(null)
const showAddModal = ref(false)

onMounted(() => {
  searchSpots()
})

const searchSpots = async () => {
  try {
    spots.value = await managerAPI.searchParkingSpots(searchQuery.value)
  } catch (error) {
    console.error('Error searching spots:', error)
  }
}

const handleSpotSubmit = async (spotData) => {
  try {
    if (selectedSpot.value) {
      await managerAPI.updateParkingSpot(selectedSpot.value.id, spotData)
      selectedSpot.value = null
    } else {
      await managerAPI.addParkingSpot(spotData)
      showAddModal.value = false
    }
    await searchSpots()
  } catch (error) {
    console.error('Error managing spot:', error)
  }
}
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6">Manager Dashboard</h1>

    <!-- Search and Add -->
    <div class="flex flex-col md:flex-row gap-4 mb-6">
      <div class="flex-1">
        <input
          v-model="searchQuery"
          @input="searchSpots"
          type="text"
          placeholder="Search parking spots..."
          class="w-full px-4 py-2 border rounded-md"
        />
      </div>
      <button
        @click="showAddModal = true"
        class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
      >
        Add New Spot
      </button>
    </div>

    <!-- Spots List -->
    <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-4">
      <div
        v-for="spot in spots"
        :key="spot.id"
        class="bg-white p-4 rounded-lg shadow"
      >
        <div class="flex justify-between items-start">
          <div>
            <h3 class="font-medium">Spot {{ spot.number }}</h3>
            <p class="text-sm text-gray-500">
              Floor {{ spot.floor }}, Section {{ spot.section }}
            </p>
            <div class="flex gap-2 mt-1">
              <span v-if="spot.isElectric" class="text-green-600" title="Electric Vehicle Spot">⚡</span>
              <span v-if="spot.isDisabled" class="text-blue-600" title="Disabled Parking">♿</span>
            </div>
          </div>
          <button
            @click="selectedSpot = spot"
            class="text-blue-600 hover:text-blue-800"
          >
            Edit
          </button>
        </div>
      </div>
    </div>

    <!-- Add Modal -->
    <div v-if="showAddModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg p-6 max-w-md w-full">
        <h2 class="text-xl font-bold mb-4">Add New Parking Spot</h2>
        <SpotForm
          @submit="handleSpotSubmit"
          @cancel="showAddModal = false"
        />
      </div>
    </div>

    <!-- Edit Modal -->
    <div v-if="selectedSpot" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg p-6 max-w-md w-full">
        <h2 class="text-xl font-bold mb-4">Edit Parking Spot</h2>
        <SpotForm
          :spot="selectedSpot"
          :is-edit="true"
          @submit="handleSpotSubmit"
          @cancel="selectedSpot = null"
        />
      </div>
    </div>
  </div>
</template>
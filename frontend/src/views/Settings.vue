<script setup>
import { ref } from 'vue'

const settings = ref({
  notifications: true,
  emailAlerts: true,
  autoRenew: false,
  defaultDuration: 2
})

const saveSettings = async () => {
  // TODO: Implement settings save logic
  console.log('Saving settings:', settings.value)
}
</script>

<template>
  <div class="max-w-2xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Settings</h2>
    
    <div class="bg-white rounded-lg shadow p-6">
      <form @submit.prevent="saveSettings" class="space-y-6">
        <div class="flex items-center justify-between">
          <label class="text-sm font-medium text-gray-700">Push Notifications</label>
          <input
            v-model="settings.notifications"
            type="checkbox"
            class="h-4 w-4 text-blue-600 focus:ring-blue-500 rounded"
          />
        </div>

        <div class="flex items-center justify-between">
          <label class="text-sm font-medium text-gray-700">Email Alerts</label>
          <input
            v-model="settings.emailAlerts"
            type="checkbox"
            class="h-4 w-4 text-blue-600 focus:ring-blue-500 rounded"
          />
        </div>

        <div class="flex items-center justify-between">
          <label class="text-sm font-medium text-gray-700">Auto-renew Reservations</label>
          <input
            v-model="settings.autoRenew"
            type="checkbox"
            class="h-4 w-4 text-blue-600 focus:ring-blue-500 rounded"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">Default Reservation Duration (hours)</label>
          <input
            v-model="settings.defaultDuration"
            type="number"
            min="1"
            max="24"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <button
          type="submit"
          class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Save Settings
        </button>
      </form>
    </div>
  </div>
</template>
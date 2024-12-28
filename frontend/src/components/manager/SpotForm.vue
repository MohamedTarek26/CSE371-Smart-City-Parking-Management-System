<!-- Reusable form component for adding/editing spots -->
<script setup>
import { ref } from 'vue'

const props = defineProps({
  spot: {
    type: Object,
    default: () => ({
      number: '',
      isElectric: false,
      isDisabled: false,
      floor: 1,
      section: 'A',
      status: 'available'
    })
  },
  isEdit: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['submit', 'cancel'])

const formData = ref({ ...props.spot })

const submitForm = () => {
  emit('submit', { ...formData.value })
}
</script>

<template>
  <form @submit.prevent="submitForm" class="space-y-4">
    <div>
      <label class="block text-sm font-medium text-gray-700">Spot Number</label>
      <input
        v-model="formData.number"
        type="text"
        required
        class="mt-1 block w-full px-3 py-2 border rounded-md"
      />
    </div>

    <div>
      <label class="block text-sm font-medium text-gray-700">Floor</label>
      <input
        v-model="formData.floor"
        type="number"
        required
        min="1"
        class="mt-1 block w-full px-3 py-2 border rounded-md"
      />
    </div>

    <div>
      <label class="block text-sm font-medium text-gray-700">Section</label>
      <input
        v-model="formData.section"
        type="text"
        required
        class="mt-1 block w-full px-3 py-2 border rounded-md"
      />
    </div>

    <div class="flex items-center gap-4">
      <label class="flex items-center">
        <input
          v-model="formData.isElectric"
          type="checkbox"
          class="rounded border-gray-300"
        />
        <span class="ml-2">Electric Vehicle Spot</span>
      </label>

      <label class="flex items-center">
        <input
          v-model="formData.isDisabled"
          type="checkbox"
          class="rounded border-gray-300"
        />
        <span class="ml-2">Disabled Parking</span>
      </label>
    </div>

    <div class="flex justify-end gap-4 mt-6">
      <button
        type="button"
        @click="$emit('cancel')"
        class="px-4 py-2 border rounded-md hover:bg-gray-50"
      >
        Cancel
      </button>
      <button
        type="submit"
        class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
      >
        {{ isEdit ? 'Update' : 'Add' }} Spot
      </button>
    </div>
  </form>
</template>
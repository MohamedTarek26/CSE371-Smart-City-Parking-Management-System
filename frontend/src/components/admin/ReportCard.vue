<!-- Reusable component for displaying reports -->
<script setup>
defineProps({
  report: {
    type: Object,
    required: true
  }
})
</script>

<template>
  <div class="bg-white p-4 rounded-lg shadow">
    <h3 class="font-medium text-lg">{{ report.title }}</h3>
    <p class="text-gray-600 mt-2">{{ report.description }}</p>
    <div class="mt-4 flex justify-between items-center text-sm text-gray-500">
      <span>{{ new Date(report.created_at).toLocaleDateString() }}</span>
      <span>{{ report.type }}</span>
    </div>
  </div>
</template>
<!-- Updated Admin Dashboard -->
<script setup>
import { ref, onMounted } from 'vue'
import { adminAPI } from '../../api/admin'
import ReportCard from '../../components/admin/ReportCard.vue'

const users = ref([])
const reports = ref([])
const selectedUser = ref(null)

onMounted(async () => {
  await loadData()
})

const loadData = async () => {
  try {
    users.value = await adminAPI.getUsers()
    reports.value = await adminAPI.getReports()
  } catch (error) {
    console.error('Error loading admin dashboard:', error)
  }
}

const updateRole = async (userId, role) => {
  try {
    await adminAPI.updateUserRole(userId, role)
    await loadData()
  } catch (error) {
    console.error('Error updating role:', error)
  }
}
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6">Admin Dashboard</h1>
    
    <!-- User Management -->
    <div class="bg-white rounded-lg shadow p-6 mb-6">
      <h2 class="text-xl font-semibold mb-4">User Management</h2>
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead>
            <tr>
              <th class="px-4 py-2 text-left">Name</th>
              <th class="px-4 py-2 text-left">Email</th>
              <th class="px-4 py-2 text-left">Current Role</th>
              <th class="px-4 py-2 text-left">Actions</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr v-for="user in users" :key="user.id">
              <td class="px-4 py-2">{{ user.name }}</td>
              <td class="px-4 py-2">{{ user.email }}</td>
              <td class="px-4 py-2">
                <span 
                  :class="{
                    'px-2 py-1 rounded text-sm': true,
                    'bg-blue-100 text-blue-800': user.role === 'admin',
                    'bg-green-100 text-green-800': user.role === 'manager',
                    'bg-gray-100 text-gray-800': user.role === 'user'
                  }"
                >
                  {{ user.role }}
                </span>
              </td>
              <td class="px-4 py-2">
                <select 
                  :value="user.role"
                  @change="updateRole(user.id, $event.target.value)"
                  class="border rounded px-2 py-1"
                >
                  <option value="user">User</option>
                  <option value="manager">Manager</option>
                  <option value="admin">Admin</option>
                </select>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Reports -->
    <div class="bg-white rounded-lg shadow p-6">
      <h2 class="text-xl font-semibold mb-4">System Reports</h2>
      <div class="grid md:grid-cols-2 gap-4">
        <ReportCard
          v-for="report in reports"
          :key="report.id"
          :report="report"
        />
      </div>
    </div>
  </div>
</template>
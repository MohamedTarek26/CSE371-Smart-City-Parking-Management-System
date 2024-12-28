<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { isAuthenticated, loadUserRoleId, clearToken, clearUserId, clearUserRoleId } from '../../services/storage'

const router = useRouter()
const searchQuery = ref('')
const isMobileMenuOpen = ref(false) // For mobile menu toggle

// Search functionality
const handleSearch = () => {
  // console.log('Searching for:', searchQuery.value)
  router.push(`/admin/search?location=${searchQuery.value}`)
}

// Navigation handler
const navigateToSection = (section) => {
  router.push(`/admin/${section}`)
  isMobileMenuOpen.value = false // Close mobile menu after navigation
}

// Navigation items
const navigationItems = [
  { name: 'Account', path: 'account', icon: '👤' },
  { name: 'Reserved Spots', path: 'reserved-spots', icon: '🅿️' },
  { name: 'Adding Lots', path: 'adding-lots', icon: '➕' },
  { name: 'Upgrade Users', path: 'upgrade-user', icon: '🚀' },
  { name: 'Settings', path: 'settings', icon: '⚙️' }
]

// Toggle mobile menu
const toggleMobileMenu = () => {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
}

const logout = () => {
  clearToken()
  clearUserId()
  clearUserRoleId()
  goToSignIn()
}

const goToSignIn = () => {
  router.push('/signin')
}

onMounted(async () => {
  // if (!isAuthenticated()) {
  //   alert("You are not authenticated")
  //   goToSignIn()
  //   return
  // }
  // if (loadUserRoleId() == 3) {
  //   alert("You are not authorized to access this page")
  //   goToSignIn()
  //   return
  // }
  console.log('Dashboard page mounted for admin with user role:', loadUserRoleId())
})
</script>

<template>
  <div class="min-h-screen bg-gray-100">
    <!-- Header -->
    <header class="bg-white shadow fixed w-full top-0 z-50">
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <div class="flex items-center justify-between py-3">
      <!-- Admin Page (Left-aligned) -->
      <div class="flex-1">
        <h1 class="text-lg md:text-2xl font-bold text-gray-900">Admin Page</h1>
      </div>

      <!-- Search Bar (Centered) -->
      <div class="hidden md:flex flex-1 justify-center">
        <div class="relative w-full max-w-lg">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Search parking lots by location..."
            class="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
            @keyup.enter="handleSearch"
          />
          <button
            @click="handleSearch"
            class="absolute right-2 top-1/2 transform -translate-y-1/2 px-3 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:ring-2 focus:ring-blue-500 focus:outline-none"
          >
            Search
          </button>
        </div>
      </div>

      <!-- Sign Out Button (Right-aligned) -->
      <div class="flex-1 flex justify-end">
        <button
          @click="logout"
          class="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500 ml-4"
        >
          Sign Out
        </button>
      </div>
    </div>
  </div>
</header>


    <!-- Main Layout -->
    <div class="flex pt-24 md:pt-16"> <!-- Added padding-top to account for fixed header -->
      <!-- Sidebar - Hidden on mobile -->
      <aside 
        class="fixed md:static w-64 bg-white shadow-sm h-screen transition-transform duration-300 transform "
        :class="{'translate-x-0': isMobileMenuOpen, '-translate-x-full': !isMobileMenuOpen, 'md:translate-x-0': true}"
      >
        <nav class="px-4 py-6 space-y-2">
          <button
            v-for="(item, index) in navigationItems"
            :key="index"
            @click="navigateToSection(item.path)"
            class="w-full text-left px-4 py-3 text-gray-700 hover:bg-gray-100 rounded-md flex items-center space-x-3"
          >
            <span>{{ item.icon }}</span>
            <span>{{ item.name }}</span>
          </button>
        </nav>
      </aside>

      <!-- Main Content -->
      <main class="flex-1 p-4 md:p-8 min-h-screen">
        <RouterView />
      </main>
    </div>

    <!-- Mobile menu overlay -->
    <div 
      v-if="isMobileMenuOpen" 
      class="fixed inset-0 bg-black bg-opacity-50 z-40 md:hidden"
      @click="toggleMobileMenu"
    ></div>
  </div>
</template>

<style scoped>
/* Prevent body scroll when mobile menu is open */
:root {
  overflow: hidden;
}

@media (min-width: 768px) {
  :root {
    overflow: auto;
  }
}
</style>
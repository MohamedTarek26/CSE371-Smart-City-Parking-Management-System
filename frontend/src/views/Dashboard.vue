<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const searchQuery = ref('')
const isMobileMenuOpen = ref(false) // For mobile menu toggle

// Search functionality
const handleSearch = () => {
  console.log('Searching for:', searchQuery.value)
  router.push(`/dashboard/search?q=${searchQuery.value}`)
}

// Navigation handler
const navigateToSection = (section) => {
  router.push(`/dashboard/${section}`)
  isMobileMenuOpen.value = false // Close mobile menu after navigation
}

// Navigation items
const navigationItems = [
  { name: 'Account', path: 'account', icon: '👤' },
  { name: 'Reserved Spots', path: 'reserved-spots', icon: '🅿️' },
  { name: 'Favorite Lots', path: 'favorite-lots', icon: '⭐' },
  { name: 'Settings', path: 'settings', icon: '⚙️' }
]

// Toggle mobile menu
const toggleMobileMenu = () => {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
}
</script>

<template>
  <div class="min-h-screen bg-gray-100">
    <!-- Header -->
    <header class="bg-white shadow-sm fixed w-full top-0 z-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
        <div class="flex items-center justify-between">
          <!-- Mobile menu button -->
          <button 
            @click="toggleMobileMenu"
            class="md:hidden p-2 rounded-md text-gray-600 hover:bg-gray-100"
          >
            <span class="text-xl">☰</span>
          </button>

          <!-- Logo -->
          <h1 class="text-xl md:text-2xl font-bold text-gray-900">Smart Parking</h1>
          
          <!-- Search Bar - Hidden on mobile, shown in menu -->
          <div class="hidden md:block flex-1 max-w-lg mx-8">
            <div class="relative">
              <input
                v-model="searchQuery"
                type="text"
                placeholder="Search parking lots..."
                class="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm"
                @keyup.enter="handleSearch"
              />
              <button
                @click="handleSearch"
                class="absolute right-2 top-1/2 transform -translate-y-1/2 px-3 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700"
              >
                Search
              </button>
            </div>
          </div>

          <!-- User Profile -->
          <div class="flex items-center">
            <span class="text-gray-700 hidden md:block">John Doe</span>
            <span class="md:hidden">👤</span>
          </div>
        </div>

        <!-- Mobile Search - Shown below header on mobile -->
        <div class="md:hidden mt-4">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Search parking lots..."
            class="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm"
            @keyup.enter="handleSearch"
          />
        </div>
      </div>
    </header>

    <!-- Main Layout -->
    <div class="flex pt-24 md:pt-16"> <!-- Added padding-top to account for fixed header -->
      <!-- Sidebar - Hidden on mobile -->
      <aside 
        class="fixed md:static w-64 bg-white shadow-sm h-screen transition-transform duration-300 transform"
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
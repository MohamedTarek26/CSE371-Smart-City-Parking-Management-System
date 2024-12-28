<template>
    <div class="max-w-4xl mx-auto">
    <h2 class="text-2xl font-bold mb-6">Manage Users</h2>
    <div class="hidden md:block flex-1 max-w-lg mx-8 mb-6">
            <div class="relative">
              <input
                v-model="searchQuery"
                type="text"
                placeholder="Search by userName..."
                class="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm"
                @keyup.enter="searchUsers"
              />
              <button
                @click="searchUsers"
                class="absolute right-2 top-1/2 transform -translate-y-1/2 px-3 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700"
              >
                Search
              </button>
            </div>
          </div>
    <div class="Users grid gap-6">
      <div
        v-for="user in filteredUsers"
        :key="user.id"
        class="bg-white rounded-lg shadow p-6"
      >
        <div class="flex justify-between items-center">
          <div>
            <h3 class="text-lg font-medium">{{ user.userName }}</h3>
            <p class="text-sm text-gray-500">{{ user.userEmail }}</p>
            <p class="text-sm text-gray-500">Phone: {{ user.userPhone }}</p>
            <p class="text-sm text-gray-500">License Plate: {{ user.licensePlate }}</p>

            <div class="text-sm text-gray-500 flex items-center">Role: 
                <p v-if="user.roleId === 3"> User</p>
                <p v-else-if="user.roleId === 2"> Manager</p>
                <p v-else-if="user.roleId === 1"> Admin</p>
            </div>


            <!-- <p class="text-sm text-gray-500">Capacity {{ user.capacity }}</p>
            <p class="text-sm text-gray-500">Pricing Structure {{ user.pricingStructure }}</p>
            <p class="text-sm text-gray-500">
              Type of spots: {{ user.typesOfSpots }}
            </p> -->
          </div>
          <div class="space-x-4">
          
            <button
              @click="updateRole(user, 3)"
              class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
            >
              User
            </button>
            <button
            @click="updateRole(user, 2)"
            class="px-4 py-2 bg-yellow-400 text-white rounded-md hover:bg-yellow-500"
          >
            Manager
          </button>
          <button
            @click="updateRole(user, 1)"
            class="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700"
          >
            Admin
          </button>
          </div>
        </div>
      </div>
    </div>
  </div>
    <!-- <div>
        <h1>Manage Users</h1>
        <input v-model="searchQuery" placeholder="Search by userName" />
        <button @click="searchUsers">Search</button>
        <ul>
            <li v-for="user in filteredUsers" :key="user.id">
                {{ user.userName }} - {{ user.roleId }}
                <button @click="updateRole(user, 'admin')">Make Admin</button>
                <button @click="updateRole(user, 'manager')">Make Manager</button>
                <button @click="updateRole(user, 'user')">Make User</button>
            </li>
        </ul>
    </div> -->
</template>

<script>
import  {userAPI}  from '../../api/user';

export default {
    data() {
        return {
            users: [],
            searchQuery: '',
        };
    },
    computed: {
        filteredUsers() {
            return this.users.filter(user =>
                user.userName.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
        },
    },
    methods: {
        async fetchUsers() {
            // Fetch users from API or service
            // This is a placeholder for actual API call
            console.log('Fetching users');
            this.users = await userAPI.getAllUsers()
            console.log('Users:', this.users)
            // this.users = [
            //     { userId: 1, userName: 'john_doe', roleId: 'user', userEmail: 'alo@email.com'},
            //     { userId: 2, userName: 'jane_doe', roleId: 'manager' , userEmail: 'alo@email.com'},
            //     { userId: 3, userName: 'admin_user', roleId: 'admin', userEmail: 'alo@email.com' },
            // ];
        },
        searchUsers() {
            // This method can be used to trigger any additional search logic if needed
        },
        async updateRole(user, newRole) {
            // Update user roleId logic
            // This is a placeholder for actual API call
            let response = await userAPI.upgradeUser(user.userId, newRole)
            alert(`response: ${response}`);
            user.roleId = newRole;
        },
    },
    mounted() {
        this.fetchUsers();
    },
};
</script>

<style scoped>
/* Add your styles here */
</style>
<template>
    <div class="admin-page">
        <h1>Admin Dashboard</h1>
        <div class="admin-controls">
            <button @click="manageUsers">Manage Users</button>
            <button @click="manageParkingSpots">Manage Parking Spots</button>
            <button @click="viewReports">View Reports</button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'AdminPage',
    methods: {
        manageUsers() {
            // Logic to manage users
            console.log('Managing Users');
        },
        manageParkingSpots() {
            // Logic to manage parking spots
            console.log('Managing Parking Spots');
        },
        viewReports() {
            // Logic to view reports
            console.log('Viewing Reports');
        }
    }
}
</script>

<style scoped>
.admin-page {
    padding: 20px;
}

.admin-controls {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

button {
    padding: 10px;
    font-size: 16px;
    cursor: pointer;
}
</style>
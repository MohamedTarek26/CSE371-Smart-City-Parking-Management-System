import { createApp } from 'vue'
import { createRouter } from './router'
import './style.css'
import App from './App.vue'

// Create Vue application instance
const app = createApp(App)

// Initialize router
const router = createRouter()

// Add router to the application
app.use(router)

// Mount the application
app.mount('#app')
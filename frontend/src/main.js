import { createApp } from 'vue'
import { createRouter } from './router'
import './style.css'
import App from './App.vue'

// Create Vue application instance
const app = createApp(App)

// Initialize router
const router = createRouter()

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('access_token')
  if (to.name !== 'signin' && to.name !== 'signup' && !token) {
    next({ name: 'signin' })
  } else {
    next()
  }
})


// Add router to the application
app.use(router)

// Mount the application
app.mount('#app')
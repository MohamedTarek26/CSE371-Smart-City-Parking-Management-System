// Import Vue Router and view components
import { createRouter as createVueRouter, createWebHistory } from 'vue-router'
import Welcome from '../views/Welcome.vue'
import SignIn from '../views/SignIn.vue'
import SignUp from '../views/SignUp.vue'
import Dashboard from '../views/Dashboard.vue'
import Account from '../views/Account.vue'
import ReservedSpots from '../views/ReservedSpots.vue'
import FavoriteLots from '../views/FavoriteLots.vue'
import Settings from '../views/Settings.vue'
import LotPreview from '../views/LotPreview.vue'
import SpotDetails from '../views/SpotDetails.vue'
import Search from '../views/Search.vue'
import AdminDashboard from '../views/admin/Dashboard.vue'
import ManagerDashboard from '../views/manager/Dashboard.vue'

// Create router instance
export const createRouter = () => {
  return createVueRouter({
    history: createWebHistory(),
    routes: [
      {
        path: '/',
        name: 'welcome',
        component: Welcome
      },
      {
        path: '/signin',
        name: 'signin',
        component: SignIn
      },
      {
        path: '/signup',
        name: 'signup',
        component: SignUp
      },
      {
        path: '/dashboard',
        name: 'dashboard',
        component: Dashboard,
        children: [
          {
            path: 'account',
            name: 'account',
            component: Account
          },
          {
            path: 'reserved-spots',
            name: 'reserved-spots',
            component: ReservedSpots
          },
          {
            path: 'favorite-lots',
            name: 'favorite-lots',
            component: FavoriteLots
          },
          {
            path: 'settings',
            name: 'settings',
            component: Settings
          },
          {
            path: 'search',
            name: 'search',
            component: Search
          }
        ]
      },
      {
        path: '/admin',
        name: 'admin',
        component: AdminDashboard,
      },
      {
        path: '/manager',
        name: 'manager',
        component: ManagerDashboard,
      },

      {
        path: '/lot/:id',
        name: 'lot-preview',
        component: LotPreview
      },
      {
        path: '/spot/:id',
        name: 'spot-details',
        component: SpotDetails
      }
    ]
  })
}


// createRouter.beforeEach((to, from, next) => {
//   const user = JSON.parse(localStorage.getItem('user')); // Example: Retrieve user info from localStorage or Vuex
  
//   if (to.meta.requiresAuth) {
//     if (!user) {
//       // User is not authenticated, redirect to login
//       return next({ name: 'signin' });
//     }

//     if (to.meta.roles && !to.meta.roles.includes(user.role)) {
//       // User does not have the required role
//       return next({ name: 'welcome' }); // Redirect to a safe route
//     }
//   }

//   // Proceed if no restrictions or all conditions are met
//   next();
// });

// export default router;
import { createRouter, createWebHistory } from 'vue-router'
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

const router = createRouter({
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
        }
      ]
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

export default router
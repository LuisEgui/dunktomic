import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import AuthPage from '@/pages/AuthPage.vue'
//import { authGuard } from './authGuard'

const routes = [
  {
    name: 'HomePage',
    path: '/',
    component: HomePage,
    //meta: {
    //  requiresAuth: true,
    //},
  },
  {
    name: 'AuthPage',
    path: '/auth',
    component: AuthPage,
  },
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})

//router.beforeEach(authGuard)
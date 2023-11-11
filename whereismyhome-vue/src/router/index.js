import { createRouter, createWebHistory } from 'vue-router';
import TheLoginView from '../views/TheLoginView.vue';
import TheSignUpView from '../views/TheSignUpView.vue';
import TheMainView from '../views/TheMainView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: TheMainView,
    },
    {
      path: '/login',
      name: 'login',
      component: TheLoginView,
    },
    {
      path: '/signup',
      name: 'signup',
      component: TheSignUpView,
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: TheLoginView,
    },
  ],
});

export default router;

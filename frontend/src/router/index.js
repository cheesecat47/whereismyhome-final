import { createRouter, createWebHistory } from 'vue-router';
import TheLoginView from '../views/TheLoginView.vue';
import TheSignUpView from '../views/TheSignUpView.vue';
import TheMainView from '../views/TheMainView.vue';
import TheCommunityView from '../views/TheCommunityView.vue';
import TheNoticeView from '../views/TheNoticeView.vue';
import TheActualPriceView from '../views/TheActualPriceView.vue';
import CommunityWrite from '../components/community/CommunityWrite.vue';
import NoticeDetailItem from '../components/notice/NoticeDetailItem.vue';

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
      path: '/notice',
      name: 'notice',
      component: TheNoticeView,
    },
    {
      path: '/notice/:boardId',
      name: 'notice-detail',
      component: NoticeDetailItem,
    },
    {
      path: '/community',
      name: 'community',
      component: TheCommunityView,
    },
    {
      path: '/community/write',
      name: 'community-write',
      component: CommunityWrite,
    },
    {
      path: '/actualprice',
      name: 'actualprice',
      component: TheActualPriceView,
    },
  ],
});

export default router;

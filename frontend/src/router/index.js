import Vue from 'vue';
import Router from 'vue-router';
import CampaignPage from '@/components/CampaignPage.vue';
import LoginPage from '@/components/LoginPage.vue';
import MainPage from '@/components/MainPage.vue';
import SignupPage from '@/components/SignupPage.vue';
import WebPage from '@/components/WebPage.vue';


Vue.use(Router);

const router = new Router({
  mode: 'history',  // URL 깔끔하게 유지
  routes: [
    {
      path: '/CampaignPage',
      name: 'CampaignPage',
      component: CampaignPage,
      meta: { requiresAuth: true },  // 로그인 필요
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage,
    },
    {
      path: '/',
      name: 'MainPage',
      component: MainPage,
    },
    {
      path: '/signup',
      name: 'SignupPage',
      component: SignupPage,
    },
    {
      path: '/webpage',
      name: 'Webpage',
      component: WebPage,
    },
  ],
});

// **라우터 가드 사용**
import { 
  authGuard 
} from '@/api/authGuardService';  // 라우터 가드 임포트
router.beforeEach(authGuard);  // authGuard 함수 호출

export default router;

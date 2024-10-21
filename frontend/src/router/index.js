import Vue from 'vue';
import Router from 'vue-router';
import HelloWorld from '@/components/HelloWorld.vue';
import LoginPage from '@/components/LoginPage.vue';
import MainPage from '@/components/MainPage.vue';
import SignupPage from '@/components/SignupPage.vue';


Vue.use(Router);

const router = new Router({
  mode: 'history',  // URL 깔끔하게 유지
  routes: [
    {
      path: '/campaign',
      name: 'HelloWorld',
      component: HelloWorld,
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
  ],
});

// **라우터 가드 사용**
import { 
  authGuard 
} from '@/api/authGuardService';  // 라우터 가드 임포트
router.beforeEach(authGuard);  // authGuard 함수 호출

export default router;

import Vue from 'vue';
import Router from 'vue-router';
import CampaignPage from '@/components/CampaignPage.vue';
import LoginPage from '@/components/LoginPage.vue';
import MainPage from '@/components/MainPage.vue';
import SignupPage from '@/components/SignupPage.vue';
import WebPage from '@/components/WebPage.vue';
import CampaignListPage from '@/components/CampaignListPage.vue';
Vue.use(Router);

const router = new Router({
  mode: 'history', // URL에서 해시(#)를 제거하기 위해 history 모드 사용
  routes: [
    {
      path: '/webpage',
      name: 'WebPage',
      component: WebPage,
    },
    {
      path: '/CampaignPage',
      name: 'CampaignPage',
      component: CampaignPage,
      meta: { requiresAuth: true }, // 인증이 필요한 페이지로 설정
    },
    {
      path: '/',
      name: 'LoginPage',
      component: LoginPage,
    },
    {
      path: '/main',
      name: 'MainPage',
      component: MainPage,
      meta: { requiresAuth: true }, // 인증이 필요한 페이지로 설정
    },
    {
      path: '/signup',
      name: 'SignupPage',
      component: SignupPage,
    },
    {
      path :'/CampaignListPage',
      name : "CampaignListPage",
      component : CampaignListPage,
    }
  ],
});

// 라우터 가드 추가
router.beforeEach((to, from, next) => {
  const isLoggedIn = !!localStorage.getItem('token'); // 로그인 상태를 확인

  if (to.meta.requiresAuth && !isLoggedIn) {
    // 인증이 필요한 페이지인데 로그인되지 않은 경우
    next('/'); // 로그인 페이지로 리디렉션
  } else if (to.path === '/' && isLoggedIn) {
    // 로그인 상태에서 로그인 페이지로 이동하려고 하면
    next('/CampaignPage'); // 캠페인 페이지로 리디렉션
  } else {
    next(); // 그 외의 경우는 정상적으로 진행
  }
});

export default router;

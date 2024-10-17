import Vue from 'vue';
import VueRouter from 'vue-router';
import MainPage from '../components/MainPage.vue';   
import LoginPage from '../components/LoginPage.vue';
import SignupPage from '@/components/SignupPage.vue';

// VueRouter를 사용하도록 Vue에 등록
Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: MainPage,  // MainPage를 기본 경로로 사용
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginPage,
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignupPage,
  }
];

const router = new VueRouter({
  mode: 'history',  // history 모드를 사용하여 브라우저 히스토리를 관리
  base: process.env.BASE_URL,
  routes,
});

export default router;


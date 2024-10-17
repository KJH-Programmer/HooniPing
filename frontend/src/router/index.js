import Vue from 'vue';
import Router from 'vue-router';
import HelloWorld from '@/components/HelloWorld.vue';
import LoginPage from '@/components/LoginPage.vue';
import MainPage from '@/components/MainPage.vue';
import SignupPage from '@/components/SignupPage.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld,  // 기본 경로는 HelloWorld
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage,   // /login 경로는 LoginPage
    },
    {
      path: '/main',
      name: 'MainPage',
      component: MainPage,    // /main 경로는 MainPage
    },
    {
      path: '/signup',
      name: 'SignupPage',
      component: SignupPage,  // /signup 경로는 SignupPage
    },
  ],
});



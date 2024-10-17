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
      component: HelloWorld, 
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage,  
    },
    {
      path: '/main',
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



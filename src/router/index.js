import Vue from 'vue';
import Router from 'vue-router';
import CampaignPage from '@/components/CampaignPage.vue';
import LoginPage from '@/components/LoginPage.vue';
import MainPage from '@/components/MainPage.vue';
import SignupPage from '@/components/SignupPage.vue';
import WebPage from '@/components/WebPage.vue';  
Vue.use(Router);

export default new Router({
  routes: [
    { 
      path: '/webpage',
      name: 'WebPage',     
      component: WebPage,    
    },
    { 
      path: '/campaignpage',
      name: 'CampaignPage',
      component: CampaignPage,  
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
    },
    {
      path: '/signup',
      name: 'SignupPage',
      component: SignupPage,  
    },
  ],
});
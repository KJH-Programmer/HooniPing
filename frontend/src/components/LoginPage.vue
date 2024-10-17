<template>
    <div class="relative h-screen bg-gray-100">
        
      <!-- 로고를 페이지 왼쪽 상단에 고정 -->
      <router-link to="/">
        <img
          :src="require('@/assets/HooniPing.jpg')"
          alt="Logo"
          class="absolute top-0 left-0 w-16 cursor-pointer z-50"
        />
      </router-link>
      <!-- 중앙에 로그인 박스 -->
      <div class="flex items-center justify-center h-full">
        <div class="bg-white p-8 rounded-md shadow-md w-full max-w-sm">
          <h2 class="text-2xl font-bold mb-6 text-center">로그인</h2>
          <form @submit.prevent="submitLogin">
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2">아이디</label>
              <input v-model="user_id" type="text" placeholder="아이디 입력" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2">비밀번호</label>
              <input v-model="password" type="password" placeholder="비밀번호 입력" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
            </div>
            <button type="submit" class="w-full bg-blue-500 text-white py-2 px-4 rounded-md">로그인</button>
          </form>
        </div>
      </div>
  
      <!-- 회원가입 버튼을 페이지 하단에 고정 -->
      <button 
        class="absolute bottom-5 bg-white-500 text-black py-2 px-4 rounded-md"
        
        @click="goToSignup">
        회원가입
      </button>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        user_id: '',  // 아이디 입력 값
        password: ''  // 비밀번호 입력 값
      };
    },
    methods: {
      async submitLogin() {
        try {
          // 백엔드 로그인 API로 요청 보내기
          const response = await axios.post('http://localhost:8080/api/login', {
            user_id: this.user_id,   // 백엔드로 전송되는 데이터에서 user_id로 변경
            password: this.password  //'http://localhost:8080/api/login' 에 백앤드 api 입력
          });
  
          // 성공적으로 로그인한 경우 처리 (토큰 저장 및 사용자 아이디 저장)
          console.log('로그인 성공:', response.data);
          // 예: localStorage에 토큰 저장
          localStorage.setItem('token', response.data.token);
          localStorage.setItem('user_id', response.data.user_id);
  
          // 로그인 성공 후 홈 페이지로 이동
          this.$router.push('/');
        } catch (error) {
          // 로그인 실패 시 처리
          console.error('로그인 실패:', error);
          alert('로그인에 실패했습니다. 다시 시도해주세요.');
        }
      },
      goToSignup() {
        this.$router.push('/signup');  // 회원가입 페이지로 이동
      }
    }
  };
  </script>
  
  <style scoped>
  /* 필요시 추가 스타일 */
  </style>
  
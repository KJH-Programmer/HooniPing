<template>
    <div class="relative w-full min-h-screen bg-[#f0f3fb] pt-[60px] lg:px-[122px] md:px-[60px] pb-[120px] xs:px-5">
      <!-- 로그인 여부에 따른 상단 UI 변경-->
      <div class="absolute top-5 right-5 flex space-x-4">
        <template v-if="!isLoggedIn">
          <!-- 로그인 및 회원가입 버튼 -->
          <button 
            class="bg-blue-500 text-white px-4 py-2 rounded-md font-bold"
            @click="goToLogin">
            로그인
          </button>
          <button 
            class="bg-blue-500 text-white px-4 py-2 rounded-md font-bold"
            @click="goToSignup">
            회원가입
          </button>
        </template>
        <template v-else>
          <!-- 사용자 아이디와 로그아웃 버튼 -->
           <span class="text-gray-700 font-bold">환영합니다, {{  user_id }}님!</span>
           <button
            class="bg-red-500 text-white px-4 py-2 rounded-md font-bold"
            @click="logout">
            로그아웃
           </button>    
        </template>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        user_id: '',// 사용자 아이디 저장
        isLoggedIn: false //로그인 여부
        
      };
    },
    mounted() {
      //로그인 여부 확인 (localStorage에 토큰 또는 사용자 정보가 있는지 확인)
      const token = localStorage.getItem('token');
      const storedUserid = localStorage.getItem('userId');

      if (token && storedUserid) {
        this.isLoggedIn = true;
        this.user_id = storedUserid; // 저장된 사용자 아이디 불러오기
      }
    },
    methods: {
      goToLogin() {
        this.$router.push('/login');  // 로그인 페이지로 이동
      },
      goToSignup() {
        this.$router.push('/signup'); // 회원가입 페이지로 이동
      },
      logout() {
        // 로그아웃 처리 : localStorage 에서 토큰과 사용자 이름 삭제
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        this.isLoggedIn = false; //로그인 상태 false 로 설정
        this.user_id = '';
        this.$router.push('/login'); //로그아웃 후 메인 페이지로 이동
      }
    }
  };
  </script>
  
  <style scoped>
  /* 필요시 추가 스타일 */
  </style>
  
<template>
    <div class="relative w-full min-h-screen bg-[#f0f3fb] pt-[60px] lg:px-[122px] md:px-[60px] pb-[120px] xs:px-5">
      <!-- 로그인 및 회원가입 버튼 -->
      <div class="absolute top-5 right-5 flex space-x-4">
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
      </div>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      goToLogin() {
        this.$router.push('/login');  // 로그인 페이지로 이동
      },
      goToSignup() {
        // 회원가입 페이지가 필요할 경우, 추가 구현 가능
        this.$router.push('/signup');
      }
    }
  }
  </script>
  
  <style scoped>
  /* 필요시 추가 스타일 */
  </style>
  
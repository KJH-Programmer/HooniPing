<template>
    <div class="signup-container">
      <h2 class="text-xl font-bold text-center mb-4">회원가입</h2>
      <form @submit.prevent="handleSubmit">
        <div class="mb-4">
          <label class="block mb-1 " for="user_id">아이디</label>
          <input v-model="formData.user_id" type="text" id="user_id" class="border rounded w-full px-2 py-1" required />
        </div>
  
        <div class="mb-4">
          <label class="block mb-1" for="password">비밀번호</label>
          <input v-model="formData.password" type="password" id="password" class="border rounded w-full px-2 py-1" required />
        </div>
  
        <div class="mb-4">
          <label class="block mb-1" for="confirmPassword">비밀번호 재확인</label>
          <input v-model="formData.confirmPassword" type="password" id="confirmPassword" class="border rounded w-full px-2 py-1" required />
        </div>
  
        <div class="mb-4">
          <label class="block mb-1" for="name">이름</label>
          <input v-model="formData.name" type="text" id="name" class="border rounded w-full px-2 py-1" required />
        </div>
  
        <div class="mb-4">
          <label class="block mb-1" for="email">이메일</label>
          <input v-model="formData.email" type="email" id="email" class="border rounded w-full px-2 py-1" required />
        </div>
  
        <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded">회원가입</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        formData: {
          user_id: '',
          password: '',
          confirmPassword: '',
          name: '',
          email: ''
        }
      };
    },
    methods: {
      async handleSubmit() {
        if (this.formData.password !== this.formData.confirmPassword) {
          alert('비밀번호가 일치하지 않습니다.');
          return;
        }
        
        const payload = {
          user_id: this.formData.user_id,
          password: this.formData.password,
          name: this.formData.name,
          email: this.formData.email,
        };
  
        try {
          const response = await axios.post('백엔드_API_주소', payload);
  
          if (response.status === 200) {
            alert('회원가입이 완료되었습니다.');
          } else {
            alert('회원가입에 실패했습니다.');
          }
        } catch (error) {
          console.error('Error during signup:', error);
          alert('서버 오류가 발생했습니다.');
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .signup-container {
    max-width: 400px;
    margin: 50px auto;;
    padding: 1rem;
    background-color: #f9f9f9;
    border-radius: 8px;
  }
  </style>
  
  
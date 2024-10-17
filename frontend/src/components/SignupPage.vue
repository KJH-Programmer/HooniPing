<template>
    <div class="signup-container">
      <h2 class="text-xl font-bold text-center mb-4">회원가입</h2>
      <form @submit.prevent="handleSubmit">
        <div class="mb-4">
          <label class="block mb-1 " for="user_id">아이디</label>

          <input 
            v-model="formData.user_id" 
            type="text" 
            id="user_id" 
            class="border rounded w-full px-2 py-1" 
            required 
          />
          <button
            type="button"
            class="bg-gray-500 text-black px-2 py-1 mt-2 rounded border-1 border-black"
            @click="checkUserId">
            아이디 중복 확인
          </button>
        </div>
  
        <div class="mb-4">
          <label class="block mb-1" for="password">비밀번호</label>
          <input 
            v-model="formData.password" 
            @input="validatePasswordLength"
            type="password" 
            id="password" 
            class="border rounded w-full px-2 py-1" 
            :placeholder="'비밀번호는 6자 이상, 14자 미만으로 입력하세요.'"
            required 
          />
          <span v-if="errors.password" class="text-red-500">{{  errors.password }}</span>
        </div>
  
        <div class="mb-4">
          <label class="block mb-1" for="confirmPassword">비밀번호 재확인</label>
          <input 
            v-model="formData.confirmPassword" 
            type="password" 
            id="confirmPassword" 
            class="border rounded w-full px-2 py-1" 
            required 
          />
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
        },
        errors: {
          user_id:'',
          password: ''
        },
        successMessage: '' // 성공 메세지
      };
    },
    methods: {
      //아이디 중복 체크 버튼 클릭시 서버에 요청
      async checkUserId() {
        if (this.formData.user_id === '') {
          this.errors.user_id = '아이디를 입력해 주세요.';
          return;
        }

        try {
          const response = await axios.post('백엔드_api_주소/check-user-id', {
            user_id: this.formData.user_id
          });

          // 아이디가 중복이면 exists= true , 중복이아니면 false exists 부분은 백엔드에서 설정한 응답 변수로 변경
          if (response.data.exists) {
            this.errors.user_id = '이미 존재하는 아이디입니다.';
            this.successMessage = ''; // 성공 메세지 초기화
          } else {
            this.errors.user_id = ''; // 중복되지 않으면 오류 메세지 제거
            this.successMessage = '사용 가능한 아이디입니다.';
          }
        } catch (error) {
          console.error('아이디 중복 체크 오류:', error);
          this.errors.user_id = '아이디를 확인하는 중 오류가 발생했습니다.';
        }
      },

      // 비밀번호 자릿수 검증 (6자 이상, 14자 미만)
      validatePasswordLength() {
        const password = this.formData.password;
        const passwordLength = password.length;

        if (password === '') {
          this.errors.password = ''; //비밀번혹 ㅏ공백일 때 오류 메세지 없음
        } else if (passwordLength < 6 || passwordLength >= 14) {
          this.errors.password = '비밀번호는 6자리 이상, 14자리 미만이어야합니다.';
        } else {
          this.errors.password = ''; //조건에 맞으면 에러 메세지 삭제
        }
      },
      async handleSubmit() {
        if (this.formData.password !== this.formData.confirmPassword) {
          alert('비밀번호가 일치하지 않습니다.');
          return;
        }

        if (this.errors.password) {
          alert('입력된 비밀번호에 오류가 있습니다.');
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
          console.error('회원가입 오류:', error);
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
  
  
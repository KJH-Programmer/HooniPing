<template>
  <div class="container">
    <div class="form-wrapper">

      <h2 class="form-title">회원가입</h2> <!-- 회원가입 제목 -->

      <form @submit.prevent="handleSubmit"> <!-- 폼 제출 시 리로드 방지 -->
        <div class="input-field">
          <label for="user_id">아이디</label> <!-- 아이디 입력 레이블 -->
          <input
            v-model="formData.user_id"
            type="text"
            id="user_id"
            placeholder="아이디 입력"
            required
          />
          <button
            type="button"
            class="check-button"
            @click="checkUserId">
            아이디 중복 확인
          </button>
          <span v-if="errors.user_id" class="text-red-500">{{ errors.user_id }}</span>
          <span v-if="successMessage" class="text-green-500">{{ successMessage }}</span>
        </div>

        <div class="input-field">
          <label for="password">비밀번호</label> <!-- 비밀번호 입력 레이블 -->
          <input
            v-model="formData.password"
            @input="validatePasswordLength"
            type="password"
            id="password"
            placeholder="비밀번호는 6자 이상, 14자 미만으로 입력하세요."
            required
          />
          <span v-if="errors.password" class="text-red-500">{{ errors.password }}</span>
        </div>

        <div class="input-field">
          <label for="confirmPassword">비밀번호 재확인</label> <!-- 비밀번호 재확인 레이블 -->
          <input
            v-model="formData.confirmPassword"
            type="password"
            id="confirmPassword"
            placeholder="비밀번호 재입력"
            required
          />
        </div>

        <div class="input-field">
          <label for="name">이름</label> <!-- 이름 입력 레이블 -->
          <input
            v-model="formData.name"
            type="text"
            id="name"
            placeholder="이름 입력"
            required
          />
        </div>

        <div class="input-field">
          <label for="email">이메일</label> <!-- 이메일 입력 레이블 -->
          <input
            v-model="formData.email"
            type="email"
            id="email"
            placeholder="이메일 입력"
            required
          />
        </div>

        <button type="submit">회원가입</button> <!-- 회원가입 버튼 -->
      </form>

    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Axios 라이브러리 임포트

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
        user_id: '',
        password: ''
      },
      successMessage: '' // 성공 메세지
    };
  },
  methods: {
    async checkUserId() {
      if (this.formData.user_id === '') {
        this.errors.user_id = '아이디를 입력해 주세요.';
        return;
      }

      try {
        const response = await axios.post('백엔드_api_주소/check-user-id', {
          user_id: this.formData.user_id
        });

        if (response.data.exists) {
          this.errors.user_id = '이미 존재하는 아이디입니다.';
          this.successMessage = ''; // 성공 메세지 초기화
        } else {
          this.errors.user_id = ''; // 오류 메세지 제거
          this.successMessage = '사용 가능한 아이디입니다.';
        }
      } catch (error) {
        console.error('아이디 중복 체크 오류:', error);
        this.errors.user_id = '아이디를 확인하는 중 오류가 발생했습니다.';
      }
    },
    validatePasswordLength() {
      const password = this.formData.password;
      const passwordLength = password.length;

      if (password === '') {
        this.errors.password = '';
      } else if (passwordLength < 6 || passwordLength >= 14) {
        this.errors.password = '비밀번호는 6자리 이상, 14자리 미만이어야 합니다.';
      } else {
        this.errors.password = '';
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
        email: this.formData.email
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
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f3fb;
}

.form-wrapper {
  width: 100%;
  max-width: 400px;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 10px;
}

.input-field {
  margin-bottom: 15px;
}

input {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  font-family: inherit;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #36996e;
}

.check-button {
  margin-top: 5px;
  background-color: #42b983; /* gray로 수정 */
  color: black;
  border: 1px solid black;
  border-radius: 5px;
  cursor: pointer;
}

.check-button:hover {
  background-color: #ccc;
}
</style>
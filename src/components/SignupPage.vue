<template>
  <div class="container">
    <div class="form-wrapper">
      <h2 class="form-title">회원가입</h2>

      <!-- 에러 메시지 -->
      <span v-if="errors.general" class="error-message">{{ errors.general }}</span>

      <!-- 성공 메시지 -->
      <span v-if="successMessage" class="success-message">{{ successMessage }}</span>

      <form @submit.prevent="handleSubmit">
        <!-- 아이디 입력 필드 -->
        <div class="input-field">
          <label for="userId">아이디</label>
          <input
            v-model="formData.userId"
            type="text"
            id="userId"
            placeholder="아이디 입력"
            required
          />
        </div>

        <!-- 비밀번호 입력 필드 -->
        <div class="input-field">
          <label for="password">비밀번호</label>
          <input
            v-model="formData.password"
            @input="validatePasswordLength"
            type="password"
            id="password"
            placeholder="비밀번호는 6자 이상, 14자 미만으로 입력하세요."
            required minlength="6" maxlength="14"
          />
          <span v-if="errors.password" class="text-red-500">{{ errors.password }}</span>
        </div>

        <!-- 비밀번호 재확인 필드 -->
        <div class="input-field">
          <label for="confirmPassword">비밀번호 재확인</label>
          <input
            v-model="formData.confirmPassword"
            type="password"
            id="confirmPassword"
            placeholder="비밀번호 재입력"
            required
          />
        </div>

        <!-- 이름 입력 필드 -->
        <div class="input-field">
          <label for="name">이름</label>
          <input
            v-model="formData.name"
            type="text"
            id="name"
            placeholder="이름 입력"
            required
          />
        </div>

        <button type="submit">회원가입</button>
      </form>
    </div>
  </div>
</template>

<script>
import { registerUser } from '@/api/UserService.js'; // 회원가입 API 호출

export default {
  data() {
    return {
      formData: {
        userId: '',
        password: '',
        confirmPassword: '',
        name: '',
        email: ''
      },
      errors: {
        userId: '',
        password: '',
        general: ''
      },
      successMessage: ''
    };
  },
  methods: {
    getInitialFormData() {
      return { userId: '', password: '', confirmPassword: '', name: '', email: '' };
    },
    validatePasswordLength() {
      const length = this.formData.password.length;
      this.errors.password = length < 6 || length >= 14
        ? '비밀번호는 6자리 이상, 14자리 미만이어야 합니다.'
        : '';
    },
    showError(field, message) {
      this.errors[field] = message;
    },
    async handleSubmit() {
      if (this.formData.password !== this.formData.confirmPassword) {
        return this.showError('password', '비밀번호가 일치하지 않습니다.');
      }

      if (this.errors.password) {
        alert('입력된 비밀번호에 오류가 있습니다.');
        return;
      }

      const payload = {
        userId: this.formData.userId,
        userPassword: this.formData.password,
        userName: this.formData.name
      };

      try {
        const response = await registerUser(payload);
        this.handleResponse(response);
      } catch (error) {
        this.showError('general', '서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
      }
    },
    handleResponse(data) {
      if (data === 'Available') {
        this.successMessage = '회원가입이 완료되었습니다.';
        this.errors.general = '';
        this.resetForm();
        this.$router.push('/');
      } else if (data === 'Unavailable') {
        this.showError('general', '이미 사용 중인 아이디입니다');
      }
    },
    resetForm() {
      this.formData = this.getInitialFormData();
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
  background-color: #42b983;
  color: black;
  border: 1px solid black;
  border-radius: 5px;
  cursor: pointer;
}

.check-button:hover {
  background-color: #ccc;
}

.error-message {
  margin-bottom: 15px;
  color: #000000;
  text-align: center;
  font-weight: bold;
}

.success-message {
  color: green;
  text-align: center;
  font-weight: bold;
  margin-bottom: 15px;
}
</style>
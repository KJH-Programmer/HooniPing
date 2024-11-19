<template>
  <div class="main">
    <!-- Header -->
    <header class="header">
      <div class="logo">대충 로고</div>
      <div class="welcome">WELCOME!</div>
    </header>

    <!-- Content -->
    <div class="content">
      <!-- Left Side Text -->
      <div class="left-text">
        <h1>대충 </h1>
        <h1>소개하는</h1>
        <h1>있어보이는 말</h1>
        <p>대충 지금 시작하라는 말!</p>
      </div>

      <!-- Sign Up Form -->
      <div class="signup-form">
        <h2>SIGN UP</h2>

        <span v-if="errors.general" class="error-message">{{ errors.general }}</span>
        <span v-if="successMessage" class="success-message">{{ successMessage }}</span>

        <form @submit.prevent="handleSubmit">
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

          <div class="input-field">
            <label for="password">비밀번호</label>
            <input
              v-model="formData.password"
              @input="validatePasswordLength"
              type="password"
              id="password"
              placeholder="비밀번호 입력 (6~13자)"
              required minlength="6" maxlength="13"
            />
            <span v-if="errors.password" class="text-red-500">{{ errors.password }}</span>
          </div>

          <div class="input-field">
            <label for="confirmPassword">비밀번호 확인</label>
            <input
              v-model="formData.confirmPassword"
              type="password"
              id="confirmPassword"
              placeholder="비밀번호 재입력"
              required
            />
          </div>

          <div class="input-field">
            <label for="name">이름</label>
            <input
              v-model="formData.name"
              type="text"
              id="name"
              placeholder="이름을 입력해주세요"
              required
            />
          </div>

          <div class="signup-button">
            <button type="submit">가입하기</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Footer -->
    <footer class="footer">
      <p>©Hooniping, 대충라이센스어쩌구</p>
      <p>문의 : hooniping@gmail.com</p>
    </footer>
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
        if(response === "success"){
          this.resetForm();
          await this.$router.push('/');
        } else if (response === "Unavailable"){
          this.showError('general', '이미 사용 중인 아이디입니다');
        }
      } catch (error) {
        this.showError('general', '서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
      }
    },
    resetForm() {
      this.formData = this.getInitialFormData();
    }
  }
};
</script>

<style scoped>
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
}

/* Main Container */
.main {
  position: relative;
  width: 100vw;
  height: 100vh;
  background: #FFFFFF;
  font-family: 'Inter', sans-serif;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

/* Header */
.header {
  width: 100%;
  height: 100px;
  background: #F5F5F5;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 43px;
}

.logo {
  font-size: 32px;
  color: #000000;
}

.welcome {
  font-size: 20px;
  color: #000000;
}

/* Content */
.content {
  display: flex;
  padding-top: 50px;
  padding-left: 70px;
}

/* Left Side Text */
.left-text {
  flex: 1;
}

.left-text h1 {
  font-size: 80px;
  line-height: 90px;
  color: #000000;
  margin: 0;
}

.left-text h1 + h1 {
  margin-top: 10px;
}

.left-text p {
  font-size: 30px;
  line-height: 36px;
  color: #000000;
  margin-top: 20px;
  margin-bottom: 30px;
}

/* Sign Up Form */
.signup-form {
  width: 370px;
  margin-left: auto;
  margin-right: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.signup-form h2 {
  font-size: 24px;
  color: #000000;
  text-align: center;
}

.input-field {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.input-field label {
  font-size: 14px;
  color: #000000;
  margin-bottom: 5px;
}

.input-field input {
  width: 400px;
  padding: 20px;
  border: none;
  background: #D9D9D9;
  font-size: 15px;
}

.input-field input::placeholder {
  color: black;
  opacity: 1;
}

.signup-button {
  margin-top: 20px;
  background: #ABABAB;
  height: 58px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 400px;
  margin-bottom: 70px; 
}

.signup-button button {
  font-size: 15px;
  color: #000000;
  background: none;
  border: none;
  cursor: pointer;
}

/* Footer */
.footer {
  width: 100%;
  height: 127px;
  background: #F5F5F5;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  padding-left: 27px;
}

.footer p {
  font-size: 16px;
  color: #747474;
  margin: 0;
  line-height: 1.5;
}
</style>

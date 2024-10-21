<template>
  <div class="container"> <!-- 전체 화면을 감싸는 컨테이너 -->
    <div class="form-wrapper"> <!-- 로고와 폼을 감싸는 래퍼 -->

      <router-link to="/"> <!-- 로고를 클릭하면 홈(/)으로 이동 -->
        <img 
          :src="require('@/assets/HooniPing.jpg')" 
          alt="Logo" 
          class="logo"
        />
      </router-link>

      <div class="form"> <!-- 로그인 폼을 감싸는 div -->
        <h2 class="form-title">로그인</h2> <!-- 로그인 제목 -->

        <form @submit.prevent="submitLogin"> <!-- 로그인 폼 -->
          <div class="input-field">
            <label for="userId">아이디</label>
            <input 
              v-model="userId" 
              type="text" 
              id="userId" 
              placeholder="아이디 입력"
              required
            />
          </div>

          <div class="input-field">
            <label for="userPassword">비밀번호</label>
            <input 
              v-model="userPassword" 
              type="password" 
              id="userPassword" 
              placeholder="비밀번호 입력"
              required
            />
          </div>

          <button type="submit">로그인</button> <!-- 로그인 버튼 -->

          <p v-if="errors.general" class="error-message">{{ errors.general }}</p> <!-- 오류 메시지 -->
        </form>
      </div>

      <button class="signup-button" @click="goToSignup">회원가입</button> <!-- 회원가입 버튼 -->

    </div>
  </div>
</template>

<script>
import { loginUser } from '@/api/LoginService'; // 분리된 로그인 서비스 함수 임포트

export default {
  data() {
    return {
      userId: '', // 사용자 입력 아이디
      userPassword: '', // 사용자 입력 비밀번호
      errors: { general: '' }, // 오류 메시지
    };
  },
  methods: {
    /**
     * 로그인 처리 함수: 로그인 API 호출 및 응답 처리
     */
    async submitLogin() {
      if (!this.userId || !this.userPassword) {
        this.errors.general = '아이디와 비밀번호를 모두 입력해주세요.';
        return;
      }

      try {
        const data = await loginUser(this.userId, this.userPassword); // 로그인 API 호출

        localStorage.setItem('token', data.token); // 토큰 저장
        localStorage.setItem('userId', data.userId); // 사용자 아이디 저장

        this.errors.general = ''; // 오류 메시지 초기화
        this.$router.push('/'); // 메인 페이지로 이동
      } catch (error) {
        this.errors.general = '로그인에 실패했습니다. 다시 시도해주세요.'; // 오류 메시지 표시
      }
    },

    /**
     * 회원가입 페이지로 이동하는 함수
     */
    goToSignup() {
      this.$router.push('/signup'); // 회원가입 페이지로 이동
    },
  },
};
</script>

<style scoped>
/* CSS 스타일 정의 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f3fb;
}
.form-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  width: 100%;
  max-width: 400px;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.logo {
  position: absolute;
  top: 20px;
  left: 20px;
  width: 80px;
  cursor: pointer;
  z-index: 10;
}
.form {
  width: 100%;
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
.signup-button {
  background-color: transparent;
  color: #42b983;
  border: none;
  cursor: pointer;
  text-decoration: underline;
}
.signup-button:hover {
  background-color: transparent;
  color: #42b983;
}
.error-message {
  margin-top: 10px;
  color: red;
  text-align: center;
  font-weight: bold;
}
</style>

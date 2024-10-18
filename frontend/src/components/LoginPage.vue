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

        <!-- 로그인 폼 -->
        <form @submit.prevent="submitLogin"> 
          <div class="input-field">
            <label for="user_id">아이디</label> <!-- 사용자 아이디 입력 -->
            <input 
              v-model="user_id" 
              type="text" 
              id="user_id" 
              placeholder="아이디 입력"
              required
            />
          </div>

          <div class="input-field">
            <label for="password">비밀번호</label> <!-- 비밀번호 입력 -->
            <input 
              v-model="password" 
              type="password" 
              id="password" 
              placeholder="비밀번호 입력"
              required
            />
          </div>

          <button type="submit">로그인</button> <!-- 로그인 버튼 -->

          <!-- 오류 메시지 -->
          <p v-if="errors.general" class="error-message">{{ errors.general }}</p>
        </form>
      </div>

      <!-- 회원가입 버튼 -->
      <button class="signup-button" @click="goToSignup">회원가입</button>

    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Axios 라이브러리로 백엔드와 통신

export default {
  data() {
    return {
      user_id: '', // 사용자가 입력한 아이디 저장
      password: '', // 사용자가 입력한 비밀번호 저장
      errors: { general: '' }, // 오류 메시지 저장 객체
    };
  },
  methods: {
    /**
     * 로그인 처리 함수: 사용자가 입력한 아이디와 비밀번호를 백엔드로 전달합니다.
     * 백엔드 응답에 따라 localStorage에 토큰 및 사용자 아이디를 저장하거나 오류 메시지를 표시합니다.
     */
    async submitLogin() {
      // 입력 검증: 아이디와 비밀번호가 모두 입력되었는지 확인
      if (!this.user_id || !this.password) {
        this.errors.general = '아이디와 비밀번호를 모두 입력해주세요.';
        return;
      }

      try {
        // 백엔드에 로그인 요청 전송
        const response = await axios.post('http://localhost:8080/api/user/login', {
          user_id: this.user_id, // 입력된 아이디 전달
          password: this.password, // 입력된 비밀번호 전달
        });

        // 로그인 성공 시 처리: 서버 응답이 성공적이고 'Not User'가 아닐 경우
        if (response.status === 200 && response.data !== 'Not User') {
          // 서버로부터 받은 토큰과 사용자 아이디를 localStorage에 저장
          localStorage.setItem('token', response.data.token);
          localStorage.setItem('user_id', response.data.user_id);

          this.errors.general = ''; // 오류 메시지 초기화
          this.$router.push('/'); // 로그인 성공 시 메인 페이지로 이동
        } else {
          // 로그인 실패 시 오류 메시지 표시
          this.errors.general = '아이디 또는 비밀번호가 일치하지 않습니다.';
        }
      } catch (error) {
        // 서버와 통신 실패 또는 오류 발생 시 처리
        console.error('로그인 실패:', error);
        this.errors.general = '서버 오류가 발생했습니다. 다시 시도해주세요.';
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
/* 컨테이너: 화면의 중앙에 정렬 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f3fb;
}

/* 래퍼: 로고와 폼을 감싸는 박스 */
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

/* 로고 위치 및 스타일 */
.logo {
  position: absolute;
  top: 20px;
  left: 20px;
  width: 80px;
  cursor: pointer;
  z-index: 10;
}

/* 폼 스타일 */
.form {
  width: 100%;
}

/* 폼 제목 스타일 */
.form-title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 10px;
}

/* 입력 필드 스타일 */
.input-field {
  margin-bottom: 15px;
}

/* 인풋 및 텍스트 입력 필드 스타일 */
input {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

/* 버튼 기본 스타일 */
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

/* 버튼 호버 시 스타일 */
button:hover {
  background-color: #36996e;
}

/* 회원가입 버튼 스타일 */
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

/* 오류 메시지 스타일 */
.error-message {
  margin-top: 10px;
  color: red;
  text-align: center;
  font-weight: bold;
}
</style>

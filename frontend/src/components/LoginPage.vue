<template>
  <div class="container"> <!-- 전체 화면을 감싸는 컨테이너 -->
    <div class="form-wrapper"> <!-- 로고와 폼을 감싸는 래퍼 -->

      <!-- 로고를 클릭하면 홈(/)으로 이동 -->
      <router-link to="/">
        <img
            :src="require('@/assets/HooniPing.jpg')"
            alt="Logo"
            class="logo"
        />
      </router-link>

      <div class="form"> <!-- 로그인 폼을 감싸는 div -->
        <h2 class="form-title">로그인</h2> <!-- 로그인 제목 -->

        <!-- 로그인 폼 -->
        <form @submit.prevent="submitLogin"> <!-- submit 시 페이지 리로드 방지 및 로그인 함수 호출 -->
          <div class="input-field">
            <label for="user_id">아이디</label> <!-- 아이디 입력 레이블 -->
            <input
                v-model="user_id"
                type="text"
                id="user_id"
                placeholder="아이디 입력"
            />
          </div>

          <div class="input-field">
            <label for="password">비밀번호</label> <!-- 비밀번호 입력 레이블 -->
            <input
                v-model="password"
                type="password"
                id="password"
                placeholder="비밀번호 입력"
            />
          </div>

          <button type="submit">로그인</button> <!-- 로그인 버튼 -->
        </form>
      </div>

      <!-- 회원가입 버튼 -->
      <button class="signup-button" @click="goToSignup">회원가입</button>

    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Axios 라이브러리 임포트

export default {
  data() {
    return {
      user_id: '', // 사용자가 입력한 아이디를 저장하는 변수
      password: '' // 사용자가 입력한 비밀번호를 저장하는 변수
    };
  },
  methods: {
    // 로그인 처리 함수
    async submitLogin() {
      try {
        // 서버로 로그인 요청을 보냄
        const response = await axios.post('http://localhost:8080/api/user/login', {
          userId: this.user_id, // 입력된 아이디 전송
          userPassword: this.password // 입력된 비밀번호 전송
        });

        // 로그인 성공 시, 서버 응답에서 받은 토큰과 사용자 아이디를 localStorage에 저장
        localStorage.setItem('token', response.data); // 토큰 저장
        localStorage.setItem('userId', this.user_id); // 사용자 아이디 저장
        console.log(localStorage.getItem('token'));
        console.log(response);
        // 로그인 성공 후 홈(/)으로 이동
        this.$router.push('/');
      } catch (error) {
        // 로그인 실패 시 에러 로그를 출력하고 알림 표시
        console.error('로그인 실패:', error);
        alert('로그인에 실패했습니다. 다시 시도해주세요.');
      }
    },
    // 회원가입 페이지로 이동하는 함수
    goToSignup() {
      this.$router.push('/signup'); // 회원가입 경로로 이동
    }
  }
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

/* 로고를 화면 왼쪽 상단에 고정 */
.logo {
  position: absolute; /* 절대 위치 지정 */
  top: 20px; /* 상단 여백 */
  left: 20px; /* 왼쪽 여백 */
  width: 80px;
  cursor: pointer;
  z-index: 10; /* 다른 요소 위에 표시 */
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
  font-family: inherit;
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
/* 회원가입 버튼에 호버 스타일을 제거 */
.signup-button:hover {
  background-color: transparent;
  color: #42b983;
}

</style>
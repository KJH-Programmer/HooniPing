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
        <h1>마케팅 콘텐츠,</h1>
        <h1>이젠 AI로 </h1>
        <h1>간편하게.</h1>
        <p>지금 바로 시작해보세요.</p>
      </div>

      <!-- Login Form -->
      <div class="login-form">
        <h2>LOGIN</h2>

        <div class="input-field">
          <input v-model="userId" type="text" placeholder="아이디 입력">
        </div>

        <div class="input-field">
          <input v-model="password" type="password" placeholder="비밀번호 입력">
        </div>

        <div class="login-button">
          <button @click="submitLogin">로그인</button>
        </div>

        <a href="#" class="signup" @click="goToSignup">회원가입</a>
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
import { Login } from '@/api/loginService';

export default {
  data() {
    return {
      userId: '', // 사용자가 입력한 아이디
      password: '' // 사용자가 입력한 비밀번호
    };
  },
  methods: {
    // 로그인 처리 함수
    async submitLogin() {
      try {
        // loginService를 사용해 로그인 요청 수행
        const response = await Login(this.userId, this.password);

        // 로그인 성공 시, 서버 응답에서 받은 토큰과 사용자 아이디를 localStorage에 저장
        sessionStorage.setItem('token', response.data);
        sessionStorage.setItem('userId', this.userId);

        // 로그인 성공 후 메인 페이지로 이동
        this.$router.push('/CampaignListPage');
      } catch (error) {
        // 로그인 실패 시 상태 코드 확인
        if (error.response && error.response.status === 401) {
          alert('아이디 또는 비밀번호가 잘못되었습니다.'); // 401 에러 메시지
        } else {
          console.error('로그인 실패:', error);
          alert('로그인에 실패했습니다. 다시 시도해주세요.');
        }
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
/* 전체 화면의 기본 여백 제거 */
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
  flex: 1; /* 왼쪽 영역을 더 차지하게 함 */
}

.left-text h1 {
  font-size: 96px;
  line-height: 116px;
  color: #000000;
  margin: 0; /* 두 줄의 간격을 최소화 */
}

.left-text h1 + h1 {
  margin-top: 10px; /* 두 번째 줄과 첫 번째 줄 사이의 간격을 추가 */
}

.left-text p {
  font-size: 40px;
  line-height: 48px;
  color: #000000;
  margin-top: 20px;
  margin-bottom: 30px;  
}

/* Login Form */
.login-form {
  width: 370px;
  margin-left: auto; /* 오른쪽 끝으로 정렬 */
  margin-right: 150px; /* 오른쪽 여백 추가 */
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-form h2 {
  font-size: 24px;
  color: #000000;
  text-align: center;
}

.input-field {
  margin-top: 20px;
  background: #D9D9D9;
  height: 61px;
  display: flex;
  align-items: center;
  width: 100%;
}

.input-field input {
  width: 100%;
  height: 100%;
  padding: 10px;
  border: none;
  background: #D9D9D9;
  font-size: 15px;
}

.input-field input::placeholder {
  color: black; 
  opacity: 1; 
}

.login-button {
  margin-top: 20px;
  background: #ABABAB;
  height: 58px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.login-button button {
  font-size: 15px;
  color: #000000;
  background: none;
  border: none;
  cursor: pointer;
}

.signup {
  display: block;
  text-align: center;
  margin-top: 20px;
  font-size: 15px;
  color: #000000;
  text-decoration: underline;
}

/* Footer */
.footer {
  width: 100%;
  height: 127px;
  background: #F5F5F5;
  display: flex;
  flex-direction: column; /* 세로 방향으로 배치 */
  align-items: flex-start; /* 왼쪽 정렬 */
  justify-content: center;
  padding-left: 27px;
}

.footer p {
  font-size: 16px;
  color: #747474;
  margin: 0;
  line-height: 1.5; /* 줄 간격 조정 */
}
</style>

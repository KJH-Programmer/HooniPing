<template>
  <div class="container">
    <!-- 로그인 여부에 따른 상단 UI -->
    <div class="header">
      <template v-if="!isLoggedIn">
        <button class="button" @click="goToLogin">로그인</button>
        <button class="button" @click="goToSignup">회원가입</button>
      </template>
      <template v-else>
        <span class="welcome-message">환영합니다, {{ user_id }}님!</span>
        <button class="logout-button" @click="logout">로그아웃</button>
      </template>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user_id: '', // 사용자 아이디 저장
      isLoggedIn: false, // 로그인 여부
    };
  },
  mounted() {
    const token = localStorage.getItem('token') || '';
    const storedUserid = localStorage.getItem('user_id') || '';

    if (token && storedUserid) {
      this.isLoggedIn = true;
      this.user_id = storedUserid;
    }
  },
  methods: {
    goToLogin() {
      this.$router.push('/login');
    },
    goToSignup() {
      this.$router.push('/signup');
    },
    logout() {
      localStorage.removeItem('token');
      localStorage.removeItem('user_id');
      this.isLoggedIn = false;
      this.user_id = '';
      this.$router.push('/');
    },
  },
};
</script>

<style scoped>
/* 전체 화면 컨테이너 */
.container {
  position: relative;
  width: 100%;
  min-height: 100vh;
  background-color: #f0f3fb;
  padding-top: 60px;
  padding-bottom: 120px;
  padding-left: 60px;
  padding-right: 60px;
}

/* 상단 헤더 */
.header {
  position: absolute;
  top: 20px;
  right: 20px;
  display: flex;
  gap: 10px;
}

/* 버튼 스타일 */
.button {
  background-color: #42b983;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s;
}

.button:hover {
  background-color: #36996e;
}

/* 로그아웃 버튼 스타일 */
.logout-button {
  background-color: #ff4d4d;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s;
}

.logout-button:hover {
  background-color: #cc0000;
}

/* 환영 메시지 스타일 */
.welcome-message {
  color: #333;
  font-weight: bold;
}
</style>

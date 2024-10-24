<template>
  <div :style="containerStyle">
    <div class="header-wrapper">
      <!-- 로그인되지 않은 상태에서 로그인/회원가입 버튼 표시 -->
      <div v-if="!isLoggedIn" class="button-group">
        <button class="login-button" @click="goToLogin">로그인</button>
        <button class="signup-button" @click="goToSignup">회원가입</button>
      </div>

      <!-- 로그인된 상태에서 환영 메시지와 로그아웃 버튼 표시 -->
      <div v-else class="welcome-wrapper">
        <span class="welcome-message">환영합니다, {{ userId }}님!</span>
        <button class="logout-button" @click="handleLogout">로그아웃</button>
      </div>
    </div>

    <!-- 로그인된 상태에서 WebPage 표시 -->
    <div v-if="isLoggedIn" class="webpage-wrapper">
      <WebPage />
    </div>
  </div>
</template>

<script>
import { getLoginStatus, performLogout } from '@/api/authService.js';
import WebPage from '@/components/WebPage.vue';

export default {
  components: { WebPage },
  data() {
    return {
      userId: '',
      isLoggedIn: false,
    };
  },
  mounted() {
    const { isLoggedIn, userId } = getLoginStatus();
    this.isLoggedIn = isLoggedIn;
    this.userId = userId;
  },
  computed: {
    containerStyle() {
      return {
        backgroundColor: this.isLoggedIn ? '#F0F0F0' : '#f0f3fb',
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
      };
    },
  },
  methods: {
    goToLogin() {
      this.$router.push('/');
    },
    goToSignup() {
      this.$router.push('/signup');
    },
    handleLogout() {
      performLogout();
      this.isLoggedIn = false;
      this.userId = '';
      this.$router.push('/');
    },
  },
};
</script>

<style scoped>
/* 헤더 영역 */
.header-wrapper {
  width: 100%;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: flex-end; /* 오른쪽 정렬 */
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 10;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  gap: 10px;
}

/* 로그인 및 회원가입 버튼 */
.login-button,
.signup-button {
  background-color: #42b983;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.login-button:hover,
.signup-button:hover {
  background-color: #36996e;
}

/* 환영 메시지와 로그아웃 버튼 */
.welcome-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
}

.welcome-message {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.logout-button {
  background-color: #f56565;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.logout-button:hover {
  background-color: #e53e3e;
}

/* WebPage 표시 영역 */
.webpage-wrapper {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 40px;
}

.webpage-wrapper > * {
  width: 80%;
  max-width: 1200px;
}
</style>

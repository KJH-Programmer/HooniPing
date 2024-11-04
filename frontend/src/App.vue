<template>
  <div id="app">
  <div class="app-container">
    <!-- 헤더: 로그인 상태일 때만 표시 -->
    <div v-if="isLoggedIn" class="header-wrapper">
      <div class="welcome-wrapper">
        <span class="welcome-message">환영합니다, {{ userId }}님!</span>
        <button class="logout-button" @click="handleLogout">로그아웃</button>
      </div>
    </div>

    <!-- 페이지 콘텐츠 -->
    <router-view />

    <!-- 푸터: 항상 표시 -->
    <AppFooter />
  </div>
  </div>
</template>

<script>
import AppFooter from '@/components/AppFooter.vue'; // Footer 컴포넌트 import
import { getLoginStatus, performLogout } from '@/api/authService.js';

export default {
  components: {
    AppFooter, // Footer 컴포넌트 등록
  },
  data() {
    return {
      userId: '',
      isLoggedIn: false,
    };
  },
  mounted() {
    this.updateLoginStatus();
  },
  methods: {
    updateLoginStatus() {
      const { isLoggedIn, userId } = getLoginStatus();
      this.isLoggedIn = isLoggedIn;
      this.userId = userId;
    },
    handleLogout() {
      performLogout();
      this.isLoggedIn = false;
      this.userId = '';

      // 현재 경로가 '/'이 아닐 때만 이동
      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
    },
  },
  watch: {
    // 라우트 변경을 감지하고 로그인 상태 업데이트
    $route() {
      this.updateLoginStatus();
    },
  },
};
</script>

<style scoped>
/* 전체 스타일은 동일하게 유지 */
.app-container {
  display: flex;
  flex-direction: column; /* 세로 방향으로 정렬 */
  min-height: 100vh;
  background-color: #f0f3fb;
  justify-content: space-between; /* 헤더와 푸터 사이 공간을 확보 */
}
.header-wrapper {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 10px;
  background-color: #fff;
  border-bottom: 1px solid #ddd;
}
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
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.logout-button:hover {
  background-color: #e53e3e;
}
</style>

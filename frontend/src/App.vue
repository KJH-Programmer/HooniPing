<template>
  <div id="app">
    <div class="app-container">
      <!-- 헤더: 로그인 상태일 때만 표시 -->
      <div v-if="isLoggedIn" class="header-wrapper">
        <div class="logo-wrapper">
          <!-- 이미지 추가 및 클릭 이벤트 연결 (로그인 상태일 때만 표시) -->
          <img
            src="@/assets/KakaoTalk_20240926_155821857.jpg"
            alt="Logo"
            class="logo-image"
            @click="goToCampaignListPage"
          />
        </div>
        <div class="welcome-wrapper">
          <span class="welcome-message">WELCOME, {{userId}}!&emsp;&emsp;</span>
          <img src="@/assets/clock.png" height="20" width="20"/>
          <span class="welcome-message">{{ timeRemaining }}</span>
          <button class="extend-button" @click="extendLogin">연장</button>
          <button class="logout-button" @click="handleLogout">LOGOUT</button>
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
import {extendLogin} from "@/api/loginService";
import jwt_decode from 'jsonwebtoken';

export default {
  components: {
    AppFooter, // Footer 컴포넌트 등록
  },
  data() {
    return {
      userId: '',
      isLoggedIn: false,
      timeRemaining : '',
      intervalId: null,
    };
  },
  mounted() {
    this.updateLoginStatus();
    this.startTokenTimer();
  },
  beforeDestroy() {
    clearInterval(this.intervalId);
  },
  methods: {
    async extendLogin(){
      const response = await extendLogin()
      if(response != null){
        sessionStorage.setItem('token', response);
      }
    },
    updateLoginStatus() {
      const { isLoggedIn, userId } = getLoginStatus();
      this.isLoggedIn = isLoggedIn;
      this.userId = userId;

      if(isLoggedIn){
        this.startTokenTimer();
      }else{
        clearInterval(this.intervalId);
        this.timeRemaining = '';
      }
    },
    handleLogout() {
      performLogout();
      this.isLoggedIn = false;
      this.userId = '';
      clearInterval(this.intervalId);
      this.timeRemaining = '';

      // 현재 경로가 '/'이 아닐 때만 이동
      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
    },
    goToCampaignListPage() {
      // 현재 경로가 "/campaignlistpage" 인지 확인
      if (this.$route.path !== '/campaignlistpage') {
        this.$router.push('/campaignlistpage'); // 여기에서 'pust'를 'push'로 수정
      }
    },
    startTokenTimer() {
      // 기존 타이머가 있다면 정리
      if (this.intervalId) {
        clearInterval(this.intervalId);
      }

      // 1초마다 남은 시간을 계산하는 타이머 설정
      this.intervalId = setInterval(() => {
        const token = sessionStorage.getItem('token');
        if (token) {
          const decoded = jwt_decode.decode(token);
          if (decoded && decoded.exp) {
            const currentTime = Math.floor(Date.now() / 1000);
            const timeLeft = decoded.exp - currentTime;
            if (timeLeft > 0) {
              const minutes = Math.floor(timeLeft / 60).toString().padStart(2, '0');
              const seconds = (timeLeft % 60).toString().padStart(2, '0');
              this.timeRemaining = `${minutes}:${seconds}`;
            } else {
              this.timeRemaining = '00:00';
              this.handleLogout();
            }
          }
        }
      }, 1000);
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
  flex-direction: column;
  min-height: 100vh;
  background-color: #eaeaea;
  justify-content: space-between;
}
.header-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #eaeaea;
  border-bottom: 1px solid #ddd;
}
.logo-wrapper {
  display: flex;
  align-items: center;
}
.logo-image {
  width: 50px; /* 이미지 크기 조정 */
  height: 50px;
  cursor: pointer; /* 클릭 가능한 커서 */
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
  background-color: #a4a4a4;
  color: #000000;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.logout-button:hover {
  background-color: #e53e3e;
}

.extend-button {
  background-color: #a4a4a4;
  color: #000000;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>

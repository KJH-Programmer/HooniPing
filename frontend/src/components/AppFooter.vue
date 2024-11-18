<!-- src/components/AppFooter.vue -->
<template>
  <div class="footer">
    <div class="content-wrapper">
      <img :src="require('@/assets/KakaoTalk_20240926_155821857.jpg')" alt="Logo" class="footer-logo" />
      <div class="text-wrapper">
        <p class="footer-title">@Hooniping 김지훈, 강어진, 김지현, 이수민, 한재준</p>
        <p class="footer-team">Hooniping git hub : https://github.com/KJH-Programmer/HooniPing</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AppFooter',
};
</script>

<style scoped>
/* 로컬 글꼴 정의 */
@font-face {
  font-family: 'RIXTteokbokkiM';
  src: url('@/assets/fonts/RIX떡볶이M.TTF') format('truetype');
  font-weight: normal;
  font-style: normal;
}

.footer {
  display: flex;
  justify-content: left; /* 가운데 정렬 */
  align-items: center;
  padding: 20px;
  background-color: #eaeaea;
  border-top: 1px solid #ddd;
}

.content-wrapper {
  display: flex;
  align-items: center;
  gap: 20px;
}

.footer-logo {
  width: 70px;
  margin-right: 20px;
}

.text-wrapper {
  display: flex;
  flex-direction: column; /* 세로로 정렬 */
  align-items: center;
}

.footer-title {
  font-size: 20px; /* "후니핑" 이름의 글씨 크기 */
  color: #333;
}

.footer-team {
  font-size: 15px; /* 팀원 이름의 글씨 크기 */
  color: #666;
}
</style>

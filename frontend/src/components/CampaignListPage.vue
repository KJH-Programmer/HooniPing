<template>
  <div class="page-container">
    <!-- 왼쪽 섹션: 슬라이드 및 생성하기 버튼 -->
    <div class="carousel-container">
      <div class="carousel-wrapper">
        <div class="carousel" :style="{ transform: `translateX(-${currentIndex * 100}%)`, transition: 'transform 0.5s ease-in-out' }">
          <div
              v-for="(slide, slideIndex) in paginatedItems"
              :key="slideIndex"
              class="carousel-item"
          >
            <div v-for="(item, itemIndex) in slide"
                 :key="itemIndex"
                 class="carousel-content"
                 @click="navigateToDetail(item.campaignId)">
              <h1>{{ item.product }}</h1>
              <h2 v-for="(keyword, index) in item.keywords.split(',')" :key="index"> - {{ keyword }}</h2>
            </div>
          </div>
        </div>
      </div>
      <div class="carousel-buttons">
        <button class="carousel-button prev" @click="prevSlide">〈</button>
        <button class="carousel-button next" @click="nextSlide">〉</button>
      </div>
      <button class="create-button" @click="createItem">생성하기</button>
    </div>

    <!-- 오른쪽 섹션: 상세 정보 -->
    <div class="detail-view" v-if="selectedItem">
      <h1>캠페인 정보</h1>
      <p><strong>주제 : </strong> {{ selectedItem.product }}</p>
      <p><strong>키워드 : </strong> {{ selectedItem.keywords }}</p>
      <p><strong>브랜드 : </strong> {{ selectedItem.brand }}</p>
      <p><strong>모델 : </strong> {{ selectedItem.brand_model }}</p>
      <p><strong>어조 : </strong> {{ selectedItem.tone }}</p>
      <p><strong>특장점 : </strong> {{ selectedItem.features }}</p>
      <p><strong>광고문구 : </strong> {{ selectedItem.ad_text }}</p>
      <p><strong>이미지 : </strong> {{ selectedItem.image_url }}</p>

      <!-- 버튼 컨테이너: 수정/삭제 버튼을 나란히 배치 -->
      <div class="button-container">
        <button class="edit-button" @click="editItem(selectedItem)">수정하기</button>
        <button class="delete-button" @click="deleteItem(selectedItem.campaignId, selectedItem.userId)">삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
import { GetCampaignList, DeleteCampaign } from "@/api/CampaignService";

export default {
  data() {
    return {
      items: [], // 카드 아이템의 데이터
      currentIndex: 0,
      selectedItem: null, // 선택된 아이템을 저장할 변수
    };
  },
  computed: {
    paginatedItems() {
      // 3개씩 아이템을 슬라이드로 나눔
      const chunkSize = 2;
      const slides = [];
      for (let i = 0; i < this.items.length; i += chunkSize) {
        slides.push(this.items.slice(i, i + chunkSize));
      }
      return slides;
    },
  },
  methods: {
    // 캠페인 리스트 가져오기
    async GetCampaignList() {
      const token = localStorage.getItem("token");
      const userId = localStorage.getItem("userId");

      try {
        const response = await GetCampaignList(token, userId);
        this.items = response;
      } catch (error) {
        console.error("캠페인 목록을 가져오는 중 오류:", error);
      }
    },
    nextSlide() {
      if (this.currentIndex < this.paginatedItems.length - 1) {
        this.currentIndex++;
      } else {
        this.currentIndex = 0; // 마지막 슬라이드이면 처음으로 돌아감
      }
    },
    prevSlide() {
      if (this.currentIndex > 0) {
        this.currentIndex--;
      } else {
        this.currentIndex = this.paginatedItems.length - 1; // 첫 번째 슬라이드이면 마지막으로 돌아감
      }
    },
    createItem() {
      this.$router.push("/CampaignPage");
    },
    editItem(item) {
      // 수정 로직 구현 또는 수정 페이지로 이동
      alert(`수정하기 버튼이 클릭되었습니다. Item: ${item.product}`);
      // 캠페인 페이지로 이동
      this.$router.push("/CampaignPage")
    },
    deleteItem(campaignId, userId) {
      DeleteCampaign(campaignId, userId);
    },
    navigateToDetail(campaignId) {
      // 아이템의 상세 정보 출력하기
      this.selectedItem = this.items.find(item => item.campaignId === campaignId);
    },
  },
  created() {
    this.GetCampaignList();
  },
};
</script>

<style scoped>
.page-container {
  display: flex; /* 가로 정렬 */
  width: 100%;
  height: 100vh; /* 화면 전체 높이 */
}

.carousel-container {
  flex: 1; /* 왼쪽 섹션 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  padding: 20px;
}

.carousel-wrapper {
  width: 100%;
  overflow: hidden;
  margin-bottom: 10px; /* 슬라이드와 버튼 사이의 간격 */
}

.carousel {
  display: flex;
  transition: transform 0.5s ease-in-out;
  width: 100%;
}

.carousel-item {
  flex: 0 0 100%;
  display: flex;
  justify-content: space-around;
  padding: 20px;
}

.carousel-content {
  flex: 0 0 45%;
  height: 450px;
  box-shadow: 5px 5px 20px rgba(0, 0, 0, 0.4);
  border-radius: 15px;
  background-color: white;
  padding: 20px;
  overflow: auto;
  cursor: pointer;
}

.carousel-buttons {
  display: flex;
  justify-content: space-between;
  width: 100px; /* 버튼의 간격 조정 */
  margin-bottom: 20px; /* create-button과의 간격 */
}

.carousel-content h1 {
  font-size: 40px;
  font-weight: bold;
  margin: 0 0 30px;
}

.carousel-content h2 {
  font-size: 20px;
  margin: 0 0 30px;
}

.carousel-button {
  border: none;
  background: none;
  font-size: 2rem;
  cursor: pointer;
}

.create-button {
  background-color: #42b983;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.button-container {
  display: flex;
  justify-content: space-between;
  width: 100%; /* 버튼이 서로 나란히 배치되도록 설정 */
  margin-top: 20px;
}

.edit-button {
  background-color: #1390af;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.delete-button {
  background-color: #ba0d0d;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.detail-view {
  flex: 0.5; /* 오른쪽 섹션 크기 조정 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 20px;
  box-shadow: 5px 5px 20px rgba(0, 0, 0, 0.4);
  border-radius: 15px;
  background-color: #f9f9f9;
  margin: auto 30px; /* 상하좌우 마진 조정 */
  height: 700px; /* 높이 조정 */
}

.detail-view h1 {
  font-size: 40px;
  font-weight: bold;
  margin: 0 0 30px;
}
</style>

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

      <template v-if="isEditing">
        <!-- 수정 모드 -->
        <p><strong>주제 <br> </strong><input v-model="selectedItem.product" /></p>
        <p><strong>키워드 <br> </strong><input v-model="selectedItem.keywords" /></p>
        <p><strong>브랜드 <br> </strong><input v-model="selectedItem.brand" /></p>
        <p><strong>모델 <br> </strong><input v-model="selectedItem.brand_model" /></p>
        <p><strong>어조 <br> </strong><input v-model="selectedItem.tone" /></p>
        <p><strong>특장점 <br> </strong><input v-model="selectedItem.features" /></p>
        <!-- 광고문구 : hooniping 단위로 쪼개서 각 부분 수정 가능 -->
        <p><strong>광고문구 </strong></p>
        <ol>
          <li v-for="(textPart, index) in editableAdTextParts" :key="index">
            <input v-model="editableAdTextParts[index]" class="ad-text-input" />
          </li>
        </ol>
        <p><strong>이미지 </strong> <input v-model="selectedItem.image_url" /></p>
        <div class="button-container">
          <button class="save-button" @click="saveEdit">저장</button>
          <button class="cancel-button" @click="cancelEdit">취소</button>
        </div>
      </template>

      <template v-else>
        <!-- 보기 모드 -->
        <p><strong>주제 <br> </strong> - {{ selectedItem.product }}</p>
        <p><strong>키워드 <br> </strong> - {{ selectedItem.keywords }}</p>
        <p><strong>브랜드 <br> </strong> - {{ selectedItem.brand }}</p>
        <p><strong>모델 <br> </strong> - {{ selectedItem.brand_model }}</p>
        <p><strong>어조 <br> </strong> - {{ selectedItem.tone }}</p>
        <p><strong>특장점 <br> </strong> - {{ selectedItem.features }}</p>
        <!-- 광고문구 : hooniping 단위로 쪼개서 출력 -->
        <p><strong>광고문구 </strong></p>
        <ol>
          <li v-for="(textPart, index) in splitAdTextFiltered" :key="index"> - {{ textPart }}</li>
        </ol>
        <p><strong>이미지 </strong> <img :src="imageUrl" alt="이미지 설명" /></p>
        <div class="button-container">
          <button class="edit-button" @click="editItem">수정</button>
          <button class="delete-button" @click="deleteItem(selectedItem.userId, selectedItem.campaignId)">삭제</button>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import {GetCampaignList,DeleteCampaign,UpdateCampaign} from "@/api/CampaignService";

export default {
  data() {
    return {
      items: [], // 카드 아이템의 데이터
      currentIndex: 0,
      selectedItem: null, // 선택된 아이템을 저장할 변수
      isEditing: false,
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
    splitAdText(){
      return this.selectedItem && this.selectedItem.ad_text ? this.selectedItem.ad_text.split("hooniping") : [];
    },
    splitAdTextFiltered() {
      return this.splitAdText.filter(textPart => textPart.trim() !== "");
    },
    // 수정 모드용 필터링된 배열 추가
    editableAdTextPartsFiltered() {
      return this.editableAdTextParts.filter(textPart => textPart.trim() !== "");
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
    editItem() {
      this.isEditing = true;
      this.editableAdTextParts = this.splitAdText.filter(textPart => textPart.trim() !== "");
    },
    async saveEdit() {
      this.isEditing = false;
      // 저장 로직을 추가할 수 있습니다 (예: API 호출로 서버에 수정 내용 전달)
      console.log("수정된 내용:", this.selectedItem);
      const token = localStorage.getItem('token');
      this.selectedItem.ad_text = this.editableAdTextParts.filter(textPart => textPart.trim() !== "").join("hooniping");
      const response = UpdateCampaign(token,this.selectedItem);
      console.log(response);
    },
    cancelEdit() {
      this.isEditing = false;
      // 수정 취소 시에 원래 내용을 복원하는 로직을 추가할 수 있습니다.
    },
    async deleteItem(userId, campaignId) {
      const token = localStorage.getItem('token');
      if (window.confirm("캠페인을 정말로 삭제하시겠습니까?")) {
        try {
          const response = await DeleteCampaign(token, userId, campaignId);
          console.log("Response 객체:", response);

          // 응답이 204라면 items 배열 업데이트
          if (response && response.status === 204) {

            // 새로운 배열 생성 후 Vue.set으로 items 갱신
            const updatedItems = this.items.filter(item => item.campaignId !== campaignId);
            this.$set(this, 'items', updatedItems);

            // 선택된 항목이 삭제된 항목이라면, 선택된 항목 초기화
            if (this.selectedItem && this.selectedItem.campaignId === campaignId) {
              this.selectedItem = null;
            }
            console.log("삭제 후 items:", this.items);
          } else {
            console.log("삭제 요청 응답이 예상과 다릅니다:", response);
          }
        } catch (error) {
          console.error("삭제 중 오류 발생:", error);
          alert("캠페인 삭제에 실패했습니다.");
        }
      }
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
  justify-content: flex-end; /* 오른쪽 정렬 */
  gap: 10px; /* 버튼 사이의 간격 */
  margin-top: auto; /* 하단에 위치 */
}
.save-button,
.cancel-button,
.edit-button,
.delete-button {
  background-color: #36996e; /* 기본 수정 버튼 색상 */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-button,
.delete-button {
  background-color: #ba0d0d; /* 삭제 버튼 색상 */
}

.save-button,
.edit-button {
  background-color: #42b983; /* 수정 버튼 색상 */
}

.detail-view {
  flex: 1.5;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 20px;
  box-shadow: 5px 5px 20px rgba(0, 0, 0, 0.4);
  border-radius: 15px;
  background-color: #f9f9f9;
  margin: auto 30px;
  height: 700px;
  overflow-y: auto; /* 세로 스크롤 활성화 */
}

.detail-view h1 {
  font-size: 40px;
  font-weight: bold;
  margin: 0 0 30px;
}

input, .ad-text-input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  line-height: 1.5;
  color: #333;
  background-color: #f9f9f9;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input:focus, .ad-text-input:focus {
  border-color: #42b983;
  box-shadow: 0 0 5px rgba(66, 185, 131, 0.5);
  outline: none;
}
</style>
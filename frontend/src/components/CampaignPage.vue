<template>
  <div class="container">
    <div class="form-wrapper">
      <!-- 폼 섹션 -->
      <div class="form section">
        <div class="input-field">
          <label for="brand">브랜드명</label>
          <input
            id="brand"
            v-model="brand"
            placeholder="브랜드명을 입력하세요."
            class="product-input"
          />
        </div>
        
        <div class="input-field">
          <label for="model">모델 이름</label>
          <input
            id="model"
            v-model="brand_model"
            placeholder="모델 이름을 입력하세요."
            class="product-input"
          />
        </div>

        <div class="input-field">
          <label for="tone">말투</label>
          <select id="tone" v-model="tone" class="product-input">
            <option disabled value="">말투를 선택하세요.</option>
            <option value="~습니다">~습니다</option>
            <option value="~해요">~해요</option>
            <option value="반말">반말</option>
            <option value="럭키비키체">럭키비키체</option>
            <option value="신조어">신조어</option>
          </select>
        </div>

        <div class="input-field">
          <label for="product">제품명</label>
          <input
            id="product"
            v-model="product"
            placeholder="제품명을 입력하세요."
            class="product-input"
          />
        </div>

        <div class="input-field">
          <label for="features">제품 설명</label>
          <input
            id="features"
            v-model="features"
            placeholder="제품 설명을 입력하세요."
            class="product-input1"
          />
        </div>

        <button class="product-button" @click="addProduct">추천 키워드 생성</button>

        <div class="input-field keyword-section">
          <label for="keywords">키워드</label>
          <div class="keyword-wrapper">
            <button
              v-for="(keyword, index) in keywords"
              :key="index"
              :class="['keyword-button', { selected: selectedKeywords.includes(keyword) }]"
              @click="toggleKeyword(keyword)"
            >
              {{ keyword }}
            </button>
          </div>
        </div>
        <button class="product-button" @click="generateRecommendation">추천 내용 생성하기</button>
      </div>
      
      <div id="recommendationLoadingContainer" v-if="isGeneratingRecommendation">
        <span id="loadingText">추천 내용 생성 중...</span>
        <div id="progressBar">
          <div id="progress" :style="{ width: recommendationLoadingPercentage + '%' }"></div>
        </div>
        <span id="percentage">{{ recommendationLoadingPercentage }}%</span>
      </div>

      <!-- 광고 문구 섹션 -->
      <div class="form section">
        <div class="input-field1">
          <label for="description3">광고 문구</label>
          <textarea v-model="sourceText" @input="resizeTextarea" rows="20"></textarea>
        </div>
      </div>

      <!-- 이미지 생성 섹션 -->
      <div class="form section">
        <div class="input-field">
          <label for="preview">이미지 요구사항</label>
          <textarea v-model="prompt" @input="resizeTextarea" rows="5" placeholder="보고 싶은 이미지를 설명해주세요!"></textarea>
        </div>
        
        <div class="button-container">
          <button class="product-button" @click="startLoading">이미지 생성하기</button>
        </div>
        
        <!-- 이미지 로딩 상태 -->
        <div id="loadingContainer" v-if="isLoading">
          <span id="loadingText">이미지 생성 중...</span>
          <div id="progressBar">
            <div id="progress" :style="{ width: loadingImagePercentage + '%' }"></div>
          </div>
          <span id="percentage">{{ loadingImagePercentage }}%</span>
        </div>

        <!-- 생성된 이미지 표시 -->
        <div class="image-container" v-if="imageUrl && !isLoading">
          <img :src="imageUrl" alt="Generated Image" class="generated-image" />
        </div>

        <button class="product-button" @click="save">저장하기</button>
      </div>
    </div>
  </div>
</template>


<script>
import {
  ExtractKeyword,
  GenerateAdText,
  onlyImage
} from '@/api/GptService';
import {
  GetNewCampaignId,
  SaveCampaign
} from '@/api/CampaignService';

export default {
  name: 'CampaignPage',
  data() {
    return {
      brand: '',
      brand_model: '',
      product: '',
      tone: '',
      features: '',
      keywords: [],
      selectedKeywords: [],
      sourceText: '',
      prompt: '',
      imageUrl: null,
      isLoading: false,
      loadingPercentage: 0,
      loadingImagePercentage: 0,
      isGeneratingRecommendation: false, // 추천 내용 생성 로딩 상태
      recommendationLoadingPercentage: 0 // 추천 내용 생성 로딩 퍼센트
    };
  },
  methods: {
    async addProduct() {
      try {
        console.log('추가된 제품명:', this.product);

        // 제품명을 기반으로 키워드를 추출
        const response = await ExtractKeyword(this.product);
        console.log('');
        //서버로부터 받은 키워드를 keywords 배열에 할당
        this.keywords = response.keywords;
        console.log('키워드 추출 성공:', this.keywords);
      } catch (error) {
        console.error('키워드 추출 오류:', error);
      }
    },
    // 광고 문구 생성
    async generateRecommendation() {
      // 추천 내용 생성 로딩 상태 설정
      this.recommendationLoadingPercentage = 0;
      this.isGeneratingRecommendation = true;
      this.increaseRecommendationLoading();
      
      try {
        const keywords = this.selectedKeywords.join(', ');
        const ad_text = await GenerateAdText(this.product, this.brand, this.tone, this.brand_model, this.features, keywords);

        const adTexts = ad_text.data.split("\n")
          .map(text => text.replace("hooniping", "").trim())
          .filter(text => text !== "");

        this.sourceText = adTexts.map((text, index) => `${index + 1}.\n${text}`).join("\n\n");
      } catch (error) {
        console.error('광고 생성 오류:', error);
      } finally {
        this.isGeneratingRecommendation = false;
      }
    },
    async createImage() {

      try {
        this.imageUrl = await onlyImage(this.prompt);
      } catch (error) {
        console.error("이미지 생성 오류:", error);
      }
    },


    // 로딩 진행률 증가 - 문구 생성용
    increaseRecommendationLoading() {
      if (this.recommendationLoadingPercentage < 100) {
        this.recommendationLoadingPercentage += 1;
        setTimeout(this.increaseRecommendationLoading, 150);
      } else {
        this.isGeneratingRecommendation = false;
      }
    },

    // 이미지 생성 + 로딩
    async startLoading() {
      this.loadingImagePercentage = 0;
      this.isLoading = true;
      const interval = setInterval(() => {
        if (this.loadingImagePercentage < 100) {
          this.loadingImagePercentage += 1;
        }
      }, 200);
      try {
        this.imageUrl = await onlyImage(this.prompt);
      } catch (error) {
        console.error('이미지 업데이트 실패:', error);
      } finally {
        clearInterval(interval);
        this.loadingImagePercentage = 100;
        setTimeout(() => {
          this.isLoading = false;
          this.progress = 0;
        }, 100);
      }
    },

    async save() {
      const userId = sessionStorage.getItem('userId');
      const newCampaignId = await GetNewCampaignId(userId);

      try {
        // 백엔드에서 우선 newCampaignId 받아옴
        const campaignData = {
          campaignId: newCampaignId, // 백엔드에서 받은 newCampaignId
          product: this.product,
          keywords: this.selectedKeywords.join(','),
          features: this.features,
          tone: this.tone,
          brand: this.brand,
          brand_model: this.brand_model,
          ad_text: this.sourceText,
          image_url: this.imageUrl
        }

        // 백엔드에서 받은 newCampaignId와 저장할 정보를 -> 백엔드로 전송
        const response = await SaveCampaign(userId, campaignData);
        this.$router.push('/CampaignListPage');
        console.log('SaveCampaign response:', response);
        console.log('캠페인 저장 성공(updatedAdText)');
        } catch (error) {
          console.log('캠페인 저장 실패:', error);
        }
    },
    resizeTextarea(event) {
      const textarea = event.target;
      textarea.style.height = 'auto';
      textarea.style.height = textarea.scrollHeight + 'px';
    },
    moveText(selectedText) {
      this.destinationText = selectedText;
    },
    toggleKeyword(keyword) {
      if (this.selectedKeywords.includes(keyword)) {
        // 이미 선택된 키워드면 배열에서 제거
        this.selectedKeywords = this.selectedKeywords.filter(item => item !== keyword);
      } else {
        // 선택되지 않은 키워드면 배열에 추가
        this.selectedKeywords.push(keyword);
      }
      console.log('선택된 키워드들:', this.selectedKeywords);
    }
  }
};
</script>

<style scoped>
textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: none;
  overflow-y: hidden;
  font-family: inherit;
  font-size: 14px;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; 
}

.form-wrapper {
  display: flex; 
  justify-content: space-between;
  width: 90%;
}

.form {
  width: 48%; 
  border-radius: 8px;
}

.input-field {
  margin-bottom: 20px; 
}

.input-field1 {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.input-field label {
  display: block;
  margin-bottom: 6px; 
}

.keyword-section {
  margin-top: 10px; 
}

.keyword-wrapper {
  display: flex;
  flex-wrap: wrap;
  gap: 8px; 
  max-width: 600px;
  margin-top: 8px;
}

.product-input {
  width: 100%;
  padding: 10px; 
  border: 1px solid #ccc;
  border-radius: 5px;
  font-family: inherit;
  font-size: 14px;
}


.product-input1 {
  width: 100%;
  padding: 10px; 
  border: 1px solid #ccc;
  border-radius: 5px;
  font-family: inherit;
  font-size: 14px;
}

.product-button {
  width: 100%;
  padding: 12px; 
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 8px;
}

.button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 8px;
}

.keyword-button {
  padding: 5px 8px; 
  background-color: transparent; 
  border: 1px solid #ccc;
  border-radius: 20px;
  font-family: inherit;
  font-size: 13px; 
  color: black; 
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.keyword-button.selected {
  background-color: #42b983;
  color: white;
}

#generateButton {
  padding: 10px 20px;
  font-size: 16px;
}

#loadingContainer {
  display: flex;
  position: fixed;
  top: 50%;
  right: 0%;
  transform: translate(-40%, -30%);
  width: 300px;
  text-align: center;
  background-color: white;
  border: 1px solid #ccc;
  padding: 20px;
  z-index: 1000;
}

#progressBar {
  width: 100%;
  background-color: #f3f3f3;
  height: 10px;
  margin-top: 10px;
  position: relative;
}

#progress {
  height: 100%;
  width: 0;
  background-color: #4caf50;
  transition: width 0.2s;
}

#loadingText {
  font-size: 14px;
  color: #333;
}

#percentage {
  margin-top: 10px;
  font-size: 14px;
}

#recommendationLoadingContainer {
  display: flex;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 300px;
  text-align: center;
  background-color: white;
  border: 1px solid #ccc;
  padding: 20px;
  z-index: 1000;
}

</style>
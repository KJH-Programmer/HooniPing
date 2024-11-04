<template>
  <div class="container">
    <div class="form-wrapper">
      <div class="form section">

        <!-- 브랜드명 입력 -->
        <div class="input-field">
          <label for="brand">브랜드명</label>
          <input 
            id="brand" 
            v-model="brand" 
            placeholder="브랜드명을 입력하세요." 
            class="product-input"
          />
        </div>

        <!-- 모델 이름 입력 -->
        <div class="input-field">
          <label for="model">모델 이름</label>
          <input 
            id="model" 
            v-model="brand_model" 
            placeholder="모델 이름을 입력하세요." 
            class="product-input"
          />
        </div>

        <!-- 말투 선택 드롭다운 -->
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

        <!-- 제품명 입력 -->
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
            class="product-input"
          />
        </div>

        <button class="product-button" @click="addProduct">제품 입력하기</button>
        
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
        <button @click="generateRecommendation">추천 내용 생성하기</button>
      </div>

      <div class="form section">
        <div class="input-field">
          <label for="description2">내용</label>
          <textarea v-model="sourceText" @click="moveText" placeholder="Type here and click to move text"></textarea>
        </div>
      </div>

      <div class="form section">
        <div class="input-field">
          <label for="preview">미리보기</label>
          <textarea v-model="destinationText" placeholder="Text will appear here"></textarea>
        </div>
        <div class="button-container">
          <button @click="save">저장</button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { 
  ExtractKeyword,
  GenerateAdText 
} from '@/api/GptService';
import {
  SaveCampaign
} from '@/api/CampaignService';

export default {
  name: 'CampaignPage',
  data() {
    return {
      brand: '',     // 브랜드명 입력란 바인딩
      brand_model: '',     // 모델 이름 입력란 바인딩
      product: '',   // 제품명 입력란 바인딩
      tone: '',          // 말투 선택 바인딩
      features: '',
      description2: '',
      preview: '',
      keywords: [],
      selectedKeywords: [],
      sourceText: '', 
      destinationText: '' 
    };
  },
  methods: {
    async addProduct() {
      try {
        console.log('추가된 제품명:', this.product);
        
        // 제품명을 기반으로 키워드를 추출
        const response = await ExtractKeyword(this.product);

        //서버로부터 받은 키워드를 keywords 배열에 할당
        this.keywords = response.data.keywords;
        console.log('키워드 추출 성공:', this.keywords);
      } catch (error) {
        console.error('키워드 추출 오류:', error);
      }
    },
    async generateRecommendation() {
      try {
          console.log('문구 생성에 사용될 키워드:', this.selectedKeywords);
          console.log('문구 생성에 사용될 product:', this.product);
          console.log('문구 생성에 사용될 brand:', this.brand);
          console.log('문구 생성에 사용될 features:', this.features);
          console.log('문구 생성에 사용될 brand_model:', this.brand_model);
          console.log('문구 생성에 사용될 tone:', this.tone);
          const token = localStorage.getItem("token");
          const keywords = this.selectedKeywords.join(', '); // 쉼표로 구분된 문자열로 변환
          // 문구 생성
          const ad_text = await GenerateAdText(token, this.product, this.brand, this.tone, this.brand_model, this.features, keywords);
          
          console.log('생성된 광고문구:', ad_text.data);

          this.sourceText = ad_text.data;
          
        } catch (error) {
          console.error('광고 생성 오류:', error);
        }
    },
    async save() {
      try {
          const campaignData = {
            product: this.product,
            keywords: this.selectedKeywords,
            features: this.features,
            tone: this.tone,
            brand: this.brand,
            brand_model: this.brand_model,
            ad_text: this.preview
          }
  
          // 로컬 스토리지에서 userId 가져오기
          const userId = localStorage.getItem('userId');
          // 저장
          const response = await SaveCampaign(userId, campaignData);

          console.log('SaveCampaign response:', response);
          console.log('캠페인 저장 성공:', this.preview);
        } catch (error) {
          console.log('캠페인 저장 실패:', error);
        }
    },
    resizeTextarea(event) {
      const textarea = event.target;
      textarea.style.height = 'auto'; 
      textarea.style.height = textarea.scrollHeight + 'px'; 
    },
    moveText() {
      this.destinationText = this.sourceText;
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

.section {
  background-color: #f7f7f7; 
  padding: 13px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); 
}

.form {
  width: 48%; 
  border-radius: 8px;
}

.input-field {
  margin-bottom: 20px; 
}

.input-field label {
  display: block;
  margin-bottom: 6px; /* 레이블과 입력 칸 사이의 여백 */
}

.keyword-section {
  margin-top: 10px; /* 여백 추가 */
}

.keyword-wrapper {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  max-width: 600px;
  margin-top: 10px;
}

.product-input {
  width: 100%; 
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-family: inherit;
  font-size: 14px;
}

.product-button {
  width: 100%; 
  padding: 10px; 
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px; /* 간격을 미리보기와 저장 버튼 간격과 동일하게 설정 */
}

.button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px; /* 버튼과 위 요소 사이 간격 */
}

.keyword-button {
  padding: 5px 10px;
  background-color: transparent; 
  border: 1px solid #ccc;
  border-radius: 20px;
  font-family: inherit;
  font-size: 14px;
  color: black; 
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  width: calc(25% - 10px); /* 한 줄에 4개 들어가도록 크기 조정 */
  box-sizing: border-box;
}

.keyword-button.selected {
  background-color: #42b983; 
  color: white; 
}

.keyword-button:hover {
  background-color: #d3d3d3;
}

textarea {
  width: 100%;
  padding: 10px; 
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-family: inherit;
  font-size: inherit;
  height: 130px; 
  overflow: hidden;
  resize: none;
}

.large-textarea {
  height: 120px; 
}

button {
  width: 100%;
  padding: 12px; 
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #36996e;
}
</style>
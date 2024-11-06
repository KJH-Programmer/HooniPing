<template>
  <div class="container">
    <div class="form-wrapper">
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
        <button class="product-button" @click="generateRecommendation">추천 내용 생성하기</button>
      </div>

      <div class="form section">
        <div class="input-field1">
          <label for="description3">광고 문구</label>
          <textarea v-model="sourceText" @input="resizeTextarea" rows="20"></textarea>
        </div>
      </div>

      <div class="form section">
        <div class="input-field">
          <label for="preview">이미지 요구사항</label>
          <textarea v-model="prompt" @input="resizeTextarea" rows="5" placeholder="프롬프트 입력"></textarea>
        </div>
        <div class="button-container">
          <button class="product-button" @click="createImage">이미지 생성하기</button>
        </div>
        <div class="image-container" v-if="imageUrl">
          <img :src="imageUrl" alt="Generated Image" class="generated-image" />
          <button class="product-button" @click="save">저장하기</button>
        </div>
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
      brand: '',     // 브랜드명 입력란 바인딩
      brand_model: '',     // 모델 이름 입력란 바인딩
      product: '',   // 제품명 입력란 바인딩
      tone: '',          // 말투 선택 바인딩
      features: '',
      description2: '',
      preview: '',
      keywords: [],
      selectedKeywords: [],
      sourceText1: '',
      sourceText2: '',
      sourceText3: '',
      sourceText: '',
      destinationText: '',
      
      prompt: "귀여운 시골 강아지 사진",
      imageUrl: null
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
          const token = localStorage.getItem("token");
          const keywords = this.selectedKeywords.join(', '); // 쉼표로 구분된 문자열로 변환
          // 문구 생성
          const ad_text = await GenerateAdText(token, this.product, this.brand, this.tone, this.brand_model, this.features, keywords);

          console.log('생성된 광고문구:', ad_text.data);

          // "hooniping"과 "\n"을 제거하고 각 광고 문구를 변수에 할당
          const adTexts = ad_text.data.split("\n").map(text => text.replace("hooniping", "").trim()).filter(text => text !== "");

          // 각 광고 문구를 sourceText 변수에 할당
          this.sourceText1 = adTexts[0] || ""; // 첫 번째 광고 문구
          this.sourceText2 = adTexts[1] || ""; // 두 번째 광고 문구
          this.sourceText3 = adTexts[2] || ""; // 세 번째 광고 문구
          console.log('sourceText1:', this.sourceText1);
          console.log('sourceText2:', this.sourceText2);
          console.log('sourceText3:', this.sourceText3);

          this.sourceText = "1.\n" + this.sourceText1 + "\n\n2.\n" + this.sourceText2 + "\n\n3.\n" + this.sourceText3 + "\n";
          

        } catch (error) {
          console.error('광고 생성 오류:', error);
        }
    },
    async createImage() {
      const token = localStorage.getItem('token');
      
      try {
        this.imageUrl = await onlyImage(token, this.prompt);
      } catch (error) {
        console.error("이미지 생성 오류:", error);
      }
    },
    async save() {
      const token = localStorage.getItem('token');
      const userId = localStorage.getItem('userId');
      const newCampaignId = await GetNewCampaignId(token, userId);

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
        const response = await SaveCampaign(token, userId, campaignData);

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
  resize: none; /* 사용자 크기 조절 비활성화 */
  overflow-y: hidden; /* 스크롤바 감추기 */
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
</style>

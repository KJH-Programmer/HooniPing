<template>
  <div class="container">
    <div class="form-wrapper">
      <div class="form section">


        <div class="input-field">
          <label for="brand">브랜드명</label>
          <input 
            id="brand" 
            v-model="brandName" 
            placeholder="브랜드명을 입력하세요." 
            class="product-input"
          />
        </div>


        <div class="input-field">
          <label for="model">모델 이름</label>
          <input 
            id="model" 
            v-model="modelName" 
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
            v-model="productName" 
            placeholder="제품명을 입력하세요." 
            class="product-input"
          />
        </div>


        <div class="input-field">
          <label for="productDescription">제품 설명</label>
          <input 
            id="productDescription" 
            v-model="productDescription" 
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
export default {
  name: 'CampaignPage',
  data() {
    return {
      brandName: '',    
      modelName: '',    
      productName: '',  
      productDescription: '', 
      tone: '',          
      description: '',
      description2: '',
      preview: '',
      keywords: ['무료 체험', '한정 판매', '특별 제공', '신규 가입', '쿠폰 혜택', '시즌 세일', '무료 배송', '파격 할인'],
      selectedKeywords: [],
      sourceText: '', 
      destinationText: '' 
    };
  },
  methods: {
    addProduct() {
      console.log('추가된 브랜드명:', this.brandName);
      console.log('추가된 모델 이름:', this.modelName);
      console.log('추가된 제품명:', this.productName);
      console.log('제품 설명:', this.productDescription);
      console.log('선택된 말투:', this.tone);
    },
    generateRecommendation() {
      console.log('추천 내용 생성:', this.selectedKeywords);
    },
    save() {
      console.log('저장:', this.preview);
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
        
        this.selectedKeywords = this.selectedKeywords.filter(item => item !== keyword);
      } else {
        
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


.form {
  width: 48%; 
  border-radius: 8px;
}

.input-field {
  margin-bottom: 20px; 
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
  padding: 8px; 
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
  width: calc(25% - 8px); 
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

<template>
  <div class="container">
    <div class="form-wrapper">
      <div class="form section">

        <!-- 브랜드명 입력 -->
        <div class="input-field">
          <label for="brand">브랜드명</label>
          <input 
            id="brand" 
            v-model="brandName" 
            placeholder="브랜드명을 입력하세요." 
            class="product-input"
          />
        </div>

        <!-- 모델 이름 입력 -->
        <div class="input-field">
          <label for="model">모델 이름</label>
          <input 
            id="model" 
            v-model="modelName" 
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
            v-model="productName" 
            placeholder="제품명을 입력하세요." 
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
          <textarea id="description2" v-model="description2" placeholder="내용을 입력하세요." @input="resizeTextarea($event)" class="large-textarea"></textarea>
        </div>
      </div>

      <div class="form section">
        <div class="input-field">
          <label for="preview">미리보기</label>
          <textarea id="preview" v-model="preview" :placeholder="description2" @input="resizeTextarea($event)" class="large-textarea"></textarea>
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
      brandName: '',     // 브랜드명 입력란 바인딩
      modelName: '',     // 모델 이름 입력란 바인딩
      productName: '',   // 제품명 입력란 바인딩
      tone: '',          // 말투 선택 바인딩
      description: '',
      description2: '',
      preview: '',
      keywords: ['무료 체험', '한정 판매', '특별 제공', '신규 가입', '쿠폰 혜택', '시즌 세일', '무료 배송', '파격 할인'],
      selectedKeywords: [] // 여러 개 선택 가능하도록 배열로 변경
    };
  },
  methods: {
    addProduct() {
      console.log('추가된 브랜드명:', this.brandName);
      console.log('추가된 모델 이름:', this.modelName);
      console.log('추가된 제품명:', this.productName);
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
  width: 50%; 
  border-radius: 10px;
}

.input-field {
  margin-bottom: 20px; 
}

.input-field label {
  display: block;
  margin-bottom: 8px; /* 레이블과 입력 칸 사이의 여백 */
}

.keyword-section {
  margin-top: 30px; /* 여백 추가 */
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
  padding: 15px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-family: inherit;
  font-size: inherit;
  height: 150px; 
  overflow: hidden; 
  resize: none; 
}

.large-textarea {
  height: 240px; 
}

button {
  width: 100%; 
  padding: 15px; 
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

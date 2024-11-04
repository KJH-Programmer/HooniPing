<template>
  <div class="container">
    <div class="form-wrapper">
      <!-- 제품 입력 폼 -->
      <div class="form">
        <div class="input-field">
          <label for="product">제품명</label>
          <input 
            id="product" 
            v-model="brand" 
            placeholder="제품명을 입력하세요" 
            class="product-input"
          />
        </div>
        <button class="product-button" @click="addProduct">제품 생성하기</button>
        
        <!-- 키워드 폼 -->
        <div class="input-field keyword-section">
          <label for="brand">키워드</label>
          <!-- 광고와 관련된 키워드 버튼들 -->
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

      <div class="form">
        <div class="input-field">
          <label for="description2">내용</label>
          <textarea id="description2" v-model="description2" placeholder="내용을 입력하세요" @input="resizeTextarea($event)"></textarea>
        </div>
      </div>

      <div class="form">
        <div class="input-field">
          <label for="preview">미리보기</label>
          <textarea id="preview" v-model="preview" :placeholder="description2" @input="resizeTextarea($event)"></textarea>
        </div>
        <button @click="save">저장</button>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: 'CampaignPage',
  data() {
    return {
      brand: '',
      description: '',
      description2: '',
      preview: '',
      keywords: ['무료 체험', '한정 판매', '특별 제공', '신규 가입', '쿠폰 혜택', '시즌 세일', '무료 배송', '파격 할인', '베스트 상품'],
      selectedKeywords: [] // 여러 개 선택 가능하도록 배열로 변경
    };
  },
  methods: {
    addProduct() {
      console.log('추가된 제품명:', this.brand);
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

.form {
  width: 50%; 
  padding: 30px; 
  border-radius: 10px;
}

.input-field {
  margin-bottom: 20px; 
}

.keyword-section {
  margin-top: 30px; 
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
  margin-top: 10px;
}

.product-button:hover {
  background-color: #36996e;
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

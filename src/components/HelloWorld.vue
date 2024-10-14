<template>
  <div class="container">
    <div class="form-wrapper">

      
      <div class="form">
        <hr>
        <div class="input-field">
          <label for="brand">키워드</label>
          <input type="text" id="brand" v-model="brand" placeholder="키워드 입력">
        </div>
        <div class="input-field">
          <label for="description">설명</label>
          <textarea id="description" v-model="description" placeholder="설명을 입력하세요"></textarea>
        </div>
        <button @click="generateRecommendation">추천 내용 생성하기</button>
      </div>

      
      <div class="form">
        <hr>
        <div class="input-field">
          <label for="brand2">제목</label>
          <input type="text" id="brand2" v-model="brand2" placeholder="제목 입력">
        </div>
        <div class="input-field">
          <label for="description2">내용</label>
          <textarea id="description2" v-model="description2" placeholder="내용을 입력하세요"></textarea>
        </div>
      </div>

      
      <div class="form">
        <hr>
        <div class="input-field">
          <label for="preview">미리보기</label>
          <!-- 두 번째 블록의 description2를 placeholder로 설정 -->
          <textarea id="preview" v-model="preview" :placeholder="description2"></textarea>
        </div>
        <button @click="save">저장</button>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      brand: '',
      description: '',
      brand2: '',
      description2: '',
      preview: ''
    };
  },
  methods: {
    generateRecommendation() {
      console.log('추천 내용 생성:', this.brand, this.description);
    },
    save() {
      console.log('저장:', this.preview);
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 40vh; 
}

.form-wrapper {
  display: flex; 
  justify-content: space-between;
  width: 90%;
}

.form {
  width: 30%; 
  padding: 20px;
  border-radius: 10px;
}

.input-field {
  margin-bottom: 15px;
}

input, textarea {
  width: 100%; 
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-family: inherit;
  font-size: inherit;
}

textarea {
  height: 100px; 
}

button {
  width: 100%; 
  padding: 10px;
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

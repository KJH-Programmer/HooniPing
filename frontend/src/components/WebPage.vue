<template>
  <div class="container">
    <div class="row">
      <div class="column">
        <h3>제품 입력</h3>
        <input type="text" class="input-box" placeholder="제품을 입력하세요." />
        <button class="btn-green" @click="generateKeywords">키워드 생성하기</button>
      </div>

      <div class="column">
        <h3>키워드 칸</h3>
        <div class="keyword-box">
        </div>
        <button class="btn-green" @click="generateContent">문구 생성하기</button>
      </div>

      <div class="column">
        <h3>생성된 문구 콘텐츠</h3>
        <div class="content-box">
        </div>
      </div>
    </div>

    <hr class="divider" />
    <div class="row">
      <div class="column">
        <h3>톤앤매너</h3>
        <div class="tone-box">
        </div>
      </div>

      <div class="column">
        <h3>브랜드명</h3>
        <input type="text" class="input-box" placeholder="브랜드명을 입력하세요." />
        <button class="btn-green" @click="generateBrandName">생성하기</button>
      </div>

      <div class="column">
        <h3>생성된 이미지 콘텐츠</h3>
        <div class="image-box">
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.row {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80%;
  margin-bottom: 40px;
}

.column {
  width: 30%;
  margin: 0 20px;
  text-align: left;
}

h3 {
  margin-bottom: 10px;
}

.input-box {
  width: 93%;
  padding: 12px 15px;
  border: 1px solid #ccc;
  border-radius: 10px; 
  margin-bottom: 20px;
  font-size: 16px;
}

textarea {
  width: 100%;
  height: 100px;
  padding: 12px 15px;
  border: 1px solid #ccc;
  border-radius: 10px; 
  font-size: 16px;
  margin-bottom: 20px;
}

.btn-green {
  width: 93%;
  padding: 15px;
  background-color: #D9D9D9;
  color: black; 
  border: none;
  border-radius: 10px; 
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px; 
}

.btn-green:hover {  /*마우스 올렸을 때*/
  background-color: #d9d9d9c2;
}

.keyword-box, .tone-box, .content-box, .image-box {
  width: 93%;
  height: 150px;
  border: 1px solid #ccc;
  border-radius: 10px; 
  margin-top: 15px;
}

.divider {
  width: 90%;
  border: 0.5px solid lightgray;
  margin: 20px 0;
}
</style>


<script>
export default {
  name: "MyWebpage",
  methods: {
    generateKeywords() {
      console.log('키워드 생성 버튼 클릭됨');
    },
    generateContent() {
      console.log('문구 생성 버튼 클릭됨');
    },
    generateBrandName() {
      console.log('브랜드명 생성 버튼 클릭됨');
    }
  }
}
</script>
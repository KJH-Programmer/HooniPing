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
          <textarea id="description" v-model="description" placeholder="설명을 입력하세요" @input="resizeTextarea($event)"></textarea>
        </div>
        <button @click="getCampaign">추천 내용 생성하기</button>
      </div>


      <div class="form">
        <hr>
        <div class="input-field">
          <label for="brand2">제목</label>
          <input type="text" id="brand2" v-model="brand2" placeholder="제목 입력">
        </div>
        <div class="input-field">
          <label for="description2">내용</label>
          <textarea id="description2" v-model="description2" placeholder="내용을 입력하세요" @input="resizeTextarea($event)"></textarea>
        </div>
      </div>

      <div class="form">
        <hr>
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
import {
  GenerateAnswer
} from '@/api/GptService';

import {
  GetCampaign
} from '@/api/CampaignService';


export default {
  name: 'HelloWorld',
  data() {
    return {
      brand: '',  // brand
      description: '',  // product
      brand2: '', // keyword1
      description2: '', // ad_text
      preview: '',
      userId: 'hooniping',
      campaignId: 1,
    };
  },
  methods: {

    // 캠페인 내용 가져오기
    async getCampaign() {
      try {
        // 로컬 스토리지에서 토큰 가져오기
        const token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJob29uaXBpbmciLCJ1c2VyTmFtZSI6Ikhvb25pcGluZyIsImlhdCI6MTcyOTQ3NTEwOCwiZXhwIjoxNzI5NDc2OTA4fQ.GEkOL2asdM8iYpptoxMlLBH4lXNig_SOOv0bPNih4Bw";
        // const token = localStorage.getItem('token');

        // GetCampaign 함수 호출
        const campaign = await GetCampaign(token, this.userId, this.campaignId);

        this.brand = campaign[0].brand;
        this.description = campaign[0].keyword3;
        this.brand2 = campaign[0].keyword1;
        this.description2 = campaign[0].keyword2;

        
        console.log('캠페인 내용 가져오기 성공:', campaign);
      } catch (error) {
        console.error('캠페인 내용 가져오기 중 오류:', error);
      }
    },

    // // 캠페인 생성
    // async createCampaign() {
    //   try {
    //     // 로컬 스토리지에서 토큰 가져오기
    //     const token = localStorage.getItem('token');
        
    //     // 캠페인 생성 데이터
    //     const campaignData = {
    //       brand: this.brand,
    //       product: this.description,
    //       keyword1: this.brand2,
    //       ad_text: this.description2,
    //     };

    //     // 캠페인 생성 함수 호출
    //     const response = await CreateCampaign(token, campaignData);

    //     console.log('추천 내용 생성:', this.brand, this.description);
    //   } catch (error) {
    //     console.error('추천 내용 생성 중 오류 발생:', error);
    //   }
    // },

    // // 캠페인 수정
    // async updateCampaign(campaignId) {
    //   try {
    //     const token = localStorage.getItem('token');

    //     // 수정할 데이터
    //     const updatedData = {
    //       brand: this.brand,
    //       product: this.description,
    //       keyword1: this.brand2,
    //       ad_text: this.description2,
    //     };

    //     // 캠페인 수정 함수 호출
    //     const response = await UpdateCampaign(token, campaignId, updatedData);

    //     console.log('캠페인 수정 성공:', response);
    //   } catch (error) {
    //     console.error('캠페인 수정 중 오류:', error);
    //   }
    // },

    save() {
      try {
        console.log('저장:', this.preview);
      } catch (error) {
        console.error('저장 중 오류 발생:', error);
      }
    },

    async generateAnswer() {
      const response = await GenerateAnswer(this.description);
      this.description2 = response.data;
    },

    resizeTextarea(event) {
      try {
        const textarea = event.target;
        textarea.style.height = 'auto'; // 초기화
        textarea.style.height = textarea.scrollHeight + 'px'; // 내용에 맞게 높이 조정
      } catch (error) {
        console.error('Textarea 리사이즈 중 오류 발생:', error);
      }
    },
  },
  mounted(){
    this.getCampaign();
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

hr {
  display: none; 
}

textarea {
  height: auto; /* 기본 높이 설정 */
  overflow: hidden; /* 스크롤 막기 */
  resize: none; 
}


</style>
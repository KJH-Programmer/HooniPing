<template>
  <div class="container">
    <div class="form-wrapper">
      <h2 class="form-title">회원가입</h2>

      <span v-if="errors.general" class="error-message">{{ errors.general }}</span>
      <span v-if="successMessage" class="success-message">{{ successMessage }}</span>

      <form @submit.prevent="handleSubmit">
        <div class="input-field">
          <label for="userId">아이디</label>
          <input
            v-model="formData.userId"
            type="text"
            id="userId"
            placeholder="아이디 입력"
            required
          />
        </div>

        <div class="input-field">
          <label for="password">비밀번호</label>
          <input
            v-model="formData.password"
            @input="validatePasswordLength"
            type="password"
            id="password"
            placeholder="비밀번호는 6자 이상, 14자 미만으로 입력하세요."
            required minlength="6" maxlength="14"
          />
          <span v-if="errors.password" class="text-red-500">{{ errors.password }}</span>
        </div>

        <div class="input-field">
          <label for="confirmPassword">비밀번호 재확인</label>
          <input
            v-model="formData.confirmPassword"
            type="password"
            id="confirmPassword"
            placeholder="비밀번호 재입력"
            required
          />
        </div>

        <div class="input-field">
          <label for="name">이름</label>
          <input
            v-model="formData.name"
            type="text"
            id="name"
            placeholder="이름 입력"
            required
          />
        </div>

        <button type="submit">회원가입</button>
      </form>
    </div>
  </div>
</template>

<script>
import { registerUser } from '@/api/UserService.js'; // 회원가입 API 호출

export default {
  data() {
    return {
      formData: {
        userId: '',
        password: '',
        confirmPassword: '',
        name: '',
        email: ''
      },
      errors: {
        userId: '',
        password: '',
        general: ''
      },
      successMessage: ''
    };
  },
  methods: {
    async handleSubmit() {
      console.log('비밀번호 확인:', this.formData.password, this.formData.confirmPassword);
      if (this.formData.password !== this.formData.confirmPassword) {
        this.showError('password', '비밀번호가 일치하지 않습니다.');
        return;
      }

      console.log('비밀번호 길이 확인:', this.errors.password);
      if (this.errors.password) {
        alert('입력된 비밀번호에 오류가 있습니다.');
        return;
      }

      const payload = {
        userId: this.formData.userId,
        userPassword: this.formData.password,
        userName: this.formData.name
      };

      console.log('전송할 데이터 payload:', payload);

      try {
        const response = await registerUser(payload);
        console.log('전체 응답 객체:', response);  // 전체 응답 로그
        
        // 응답이 문자열로 바로 반환되는 경우 처리
        if (typeof response === 'string') {
          console.log('응답 문자열:', response);  // 응답이 문자열일 때 로그
          this.handleResponse(response.trim());  // 문자열의 공백 제거 후 처리
        } else if (response && response.data) {
          console.log('응답 데이터 (data):', response.data);  // 응답 데이터 로그
          this.handleResponse(response.data.trim());  // 정상적인 데이터 처리
        } else {
          console.warn('응답이 비어 있거나 정의되지 않았습니다.');
          this.showError('general', '서버에서 유효한 응답을 받지 못했습니다.');
        }
      } catch (error) {
        console.error('서버 통신 오류:', error);  // 오류 로그
        this.showError('general', '서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
      }
    },
    handleResponse(data) {
    console.log('handleResponse 실행, 응답 데이터:', data);  // 응답 데이터 로그

    if (data === 'success') {  // 서버가 success 문자열을 반환하는 경우
      console.log('회원가입 성공 메시지 설정');
      this.successMessage = '회원가입이 완료되었습니다.';
      this.errors.general = '';

      // UI 갱신 후 2초 대기 후 페이지 이동
      setTimeout(() => {
        console.log('메인 페이지로 이동');
        this.resetForm();  // 페이지 이동 후 폼 초기화
        this.$router.push('/');  // 메인 페이지로 이동
      }, 2000);  // 2초 동안 성공 메시지 표시
    } else if (data === 'Unavailable') {
      console.log('이미 사용 중인 아이디');
      this.showError('general', '이미 사용 중인 아이디입니다.');
    } else {
      console.warn('알 수 없는 응답:', data);
      this.showError('general', '알 수 없는 응답입니다. 다시 시도해주세요.');
    }
  },
    resetForm() {
      console.log('폼 초기화');
      this.formData = {
        userId: '',
        password: '',
        confirmPassword: '',
        name: '',
        email: ''
      };
      this.successMessage = '';  // 성공 메시지 초기화
    },
    showError(field, message) {
      console.log(`오류 메시지 설정 - ${field}: ${message}`);
      this.errors[field] = message;
    },
    validatePasswordLength() {
      const length = this.formData.password.length;
      console.log('비밀번호 길이:', length);
      this.errors.password =
        length < 6 || length >= 14
          ? '비밀번호는 6자리 이상, 14자리 미만이어야 합니다.'
          : '';
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
  background-color: #f0f3fb;
}

.form-wrapper {
  width: 100%;
  max-width: 400px;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 10px;
}

.input-field {
  margin-bottom: 15px;
}

input {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #36996e;
}

.check-button {
  margin-top: 5px;
  background-color: #42b983;
  color: black;
  border: 1px solid black;
  border-radius: 5px;
  cursor: pointer;
}

.check-button:hover {
  background-color: #ccc;
}

.error-message {
  margin-bottom: 15px;
  color: #000000;
  text-align: center;
  font-weight: bold;
}

.success-message {
  margin-bottom: 15px;
  color: #42b983;
  text-align: center;
  font-weight: bold;
}
</style>

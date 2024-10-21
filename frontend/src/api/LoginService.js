import axios from 'axios'; // Axios 라이브러리로 백엔드와 통신

const API_URL = 'http://localhost:8080/api/user'; // 백엔드 API URL

// 로그인 함수: 백엔드로 로그인 요청 전송
export const loginUser = async (userId, userPassword) => {
  try {
    const response = await axios.post(`${API_URL}/login`, {
      userId, // 사용자 ID 전송
      userPassword, // 비밀번호 전송
    });

    return response.data; // 성공 시 백엔드 응답 반환
  } catch (error) {
    console.error('로그인 오류:', error); // 오류 로깅
    throw error; // 오류 던지기 (Vue 컴포넌트에서 처리)
  }
};

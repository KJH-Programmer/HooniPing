import axios from 'axios';

// 회원가입 API 호출
export const registerUser = async (payload) => {
  try {
    const response = await axios.post('http://localhost:8080/api/user/create', payload);
    return response.data; // 응답 데이터 반환
  } catch (error) {
    console.error('회원가입 오류:', error);
    throw new Error('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
  }
};

import axios from 'axios';

const API_URL = 'http://localhost:8080/api/gpt';

// 질문-대답(GPT 테스트)
export const GenerateAnswer = async (question) => {
    return await axios.post(API_URL, {
        question: question
    });
};

// 키워드 추출
export const ExtractKeyword = async (product) => {
    return await axios.post(`${API_URL}/keyword`, {
        product: product
    });
};

//==================== 문구 ======================
// 광고 문구 출력
export const GenerateAdText = async (token,product, brand, tone, brand_model, features, keywords) => {
    return await axios.post(`${API_URL}/adtext`, {
        product: product,
        brand: brand,
        features: features,
        tone: tone,
        brand_model: brand_model,
        keywords: keywords
    }, 
    {
        headers: {
            Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
        }
    });
}

// //==================== 이미지 =======================
// // image 생성 (> s3 버킷에 저장 > 이미지 url 반환)
// export const GenerateAdImage = async (prompt, userId, campaignId) => {
//     return await axios.post(`${API_URL}/image`, {
//         prompt: prompt,
//         userId: userId,
//         campaignId: campaignId
//     })
// }

// // image 버킷에서 삭제
// export const DeleteAdImage = async (prompt, userId, campaignId) => {
//     return await axios.post(`${API_URL}/image-dlt`, {
//         fileName: fileName
//     })
// }
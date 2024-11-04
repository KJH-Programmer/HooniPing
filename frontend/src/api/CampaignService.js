import axios from 'axios';

// 백엔드 캠페인 API
const API_URL = 'http://localhost:8080/api/campaign';

// 캠페인 내용 가져오기
export const GetCampaign = async (token, userId, campaignId) => {
    try {
      const response = await axios.post(`${API_URL}/content`,
        {
          userId: userId,
          campaignId: campaignId
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          }
        }
      );
      console.log("response:", response);
      return response.data;
    } catch (error) {
      console.error('캠페인 내용 가져오기 중 오류:', error);
      throw error;
    }
  };

// 캠페인 내용 가져오기
export const GetCampaignList = async (token, userId) => {
    try {
        const response = await axios.post(`${API_URL}/user-campaign`,
            {
                userId: userId,
            },
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            }
        );
        console.log("response:", response);
        return response.data;
    } catch (error) {
        console.error('캠페인 내용 가져오기 중 오류:', error);
        throw error;
    }
};

// 캠페인 내용 가져오기
export const DeleteCampaign = async (token, userId, campaignId) => {
    try {
        const response = await axios.post(`${API_URL}/delete`,
            {
                userId: userId,
                campaignId: campaignId
            },
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            }
        );
        console.log("response:", response);
        return response.data;
    } catch (error) {
        console.error('캠페인 내용 가져오기 중 오류:', error);
        throw error;
    }
};

// // 캠페인 생성
// export const CreateCampaign = async (token, campaignData) => {
//     try {
//         const response = await axios.post(API_URL, campaignData, {
//             headers: {
//                 Authorization: `Bearer ${token}`,
//             },
//         });
//         return response.data;
//     } catch (error) {
//         console.error('캠페인 생성 중 오류:', error);
//         throw error;
//     }
// };

// // 특정 캠페인(campaignId) 수정
// export const UpdateCampaign = async (token, campaignId, updatedData) => {
//     try {
//         const response = await axios.put(`${API_URL}${campaignId}`, updatedData, {
//             headers: {
//                 Authorization: `Bearer ${token}`,
//             },
//         });
//         return response.data;
//     } catch (error) {
//         console.error('캠페인 내용 수정 중 오류:', error);
//         throw error;
//     }
// };


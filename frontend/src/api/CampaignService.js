import axios from 'axios';

// 백엔드 캠페인 API
const API_URL = 'http://localhost:8080/api/campaign';

//========================< 특정 사용자의 캠페인 >=============================
// userId의 모든 캠페인 조회
export const GetAllCampaigns = async (token, userId) => {
  try {
    const response = await axios.get(`${API_URL}/user-campaign`,
      {
        userId: userId
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
    console.error('모든 캠페인 조회 중 오류:', error);
    throw error;
  }
}

// =====================< 특정 캠페인 >=========================
// 캠페인 내용 조회
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

// newCampaignId 받아오기
export const GetNewCampaignId = async (token, userId) => {
  try {
    const response = await axios.post(`${API_URL}/content/new-campaignId`, {
        userId: userId,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
  } catch (error) {
      console.error('새 campaignId 받아오는 중 오류:', error);
      throw error;
  }
}

// 캠페인 저장
export const SaveCampaign = async (token, userId, campaignData) => {
    try {
        const response = await axios.post(`${API_URL}/content/save`,{
            userId: userId,
            ...campaignData
          },
          {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('캠페인 저장(생성) 중 오류:', error);
        throw error;
    }
};

// 기존 캠페인 업데이트
export const UpdateCampaign = async (token, updatedData) => {
    try {
        const response = await axios.put(`${API_URL}/content/update`,
          {
            campaignId: campaignId,
            updatedData: updatedData,
          },
          {
            headers: {
                Authorization: `Bearer ${token}`,
            },
          }
        );
        return response.data;
    } catch (error) {
        console.error('캠페인 업데이트 중 오류:', error);
        throw error;
    }
};

// userId, campaignId로 캠페인 삭제
export const DeleteCampaign = async (token, userId, campaignId ) => {
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
        return response;
    } catch (error) {
        console.error('캠페인 삭제중 오류:', error);
        throw error;
    }
};

// userId, product로 캠페인 검색
export const SearchCampaign = async (token, userId, product) => {
  try {
      const response = await axios.put(`${API_URL}/search`,
        {
          userId: userId,
          product: product
        },
        {
          headers: {
              Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data;
  } catch (error) {
      console.error('캠페인 검색 중 오류:', error);
      throw error;
  }
};
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/campaign';

export const GetCampaign = async (campaignId) => {
    return await axios.post(API_URL, {
        question: question
    });
};


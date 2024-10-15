import axios from 'axios';

const API_URL = 'http://localhost:8080/api/gpt';

export const GenerateAnswer = async (question) => {
    return await axios.post(API_URL, {
        question: question
    });
};


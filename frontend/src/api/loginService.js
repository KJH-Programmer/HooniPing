import axios from 'axios';

const API_URL = 'http://localhost:8080/api/user';

export const Login = async (userId, userPassword) => {
    return await axios.post(API_URL + "/login", {
        userId: userId,
        userPassword : userPassword
    });
};


import axios from 'axios';

//const API_URL = `http://${process.env.VUE_APP_API_URL}/api/user`;
const API_URL = `http://localhost:8080/api/user`;
export const Login = async (userId, userPassword) => {
    return await axios.post(API_URL + "/login", {
        userId: userId,
        userPassword : userPassword
    });
};


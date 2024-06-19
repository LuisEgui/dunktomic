import axios from 'axios'

export const http = () => {
    const headers = {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    };
  
    return axios.create({
      baseURL: 'http://localhost:8081',
      headers,
      timeout: 10000,
    });
  };

http().get('/actuator/health')
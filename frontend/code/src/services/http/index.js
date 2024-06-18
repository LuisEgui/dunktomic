import axios from 'axios'

export const http = () => {
    const headers = {
      Accept: 'application/json',
    }
  
    return axios.create({
      baseURL: 'http://localhost:8081',
      headers,
    })
  }

http().get('/actuator/health')
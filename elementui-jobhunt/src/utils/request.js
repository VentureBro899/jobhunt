import axios from 'axios'

export default axios.create({
  // baseURL: 'http://server.daditg.top/',
  baseURL: 'http://localhost:8080/',
  crossDomain: true,
  withCredentials: true
})

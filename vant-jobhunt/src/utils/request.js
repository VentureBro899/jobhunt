import axios from 'axios'

export default axios.create({
  baseURL: 'http://server.daditg.top/',
  // baseURL: 'http://localhost:8080/',
  // 跨域请求一定要配以下两个参数，否则无法携带cookie发送到后端，导致每次请求的session不一致
  crossDomain: true,
  withCredentials: true
})

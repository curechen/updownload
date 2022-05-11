import axios from 'axios'

// axios.defaults.withCredentials = true

export function request(config) {
  // 创建实例
  const instance = axios.create({
    baseURL: '/api',
    timeout: 5000,
    withCredentials: true
  })

  // 请求拦截
  instance.interceptors.request.use(config => {
    return config
  }, err => {
    console.log(err);
  })

  // 响应拦截
  instance.interceptors.response.use(res => {
    return res.data
  }, err => {
    console.log(err);
  })

  // 发送真正的网络请求
  return instance(config)
}
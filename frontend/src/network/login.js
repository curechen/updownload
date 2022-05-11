import { request } from './request'

// 通过手机号的方式登录
export function accountLogin(username, password) {
  return request({
    url: '/user/login',
    method: 'get',
    params: {
      username: username,
      password: password
    }
  })
}

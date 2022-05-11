import { request } from './request'

// export function register(username, password, name) {
//   return request({
//     url: '/user/register',
//     method: 'post',
//     data: {
//       username: '',
//       password,
//       name: name
//     }
//   })
// }

export function userRegister(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}
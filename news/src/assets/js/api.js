import Axios from 'axios'

//Axios.defaults.withCredentials = true;

export function Post(url, params) {
  return new Promise((resolve, reject) => {
    Axios.post(url, params).then(res => {
      if (res.status === 200) {
        resolve(res.data)
      } else {
        reject({ code: 400, errmsg: '网络出现问题' })
      }
    })
  })
}

export function Get(url, params) {
  return new Promise((resolve, reject) => {
    Axios.get(url, params).then(res => {
      if (res.status === 200) {
        resolve(res.data)
      } else {
        reject({ code: 400, errmsg: '网络出现问题' })
      }
    })
  })
}

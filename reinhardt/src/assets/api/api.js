import Axios from "axios";

Axios.defaults.withCredentials = true;

export function Post(url, params) {
  return new Promise((resolve, reject) => {
    Axios.post(url, params).then(res => {
      resolve(data);
    });
  });
}

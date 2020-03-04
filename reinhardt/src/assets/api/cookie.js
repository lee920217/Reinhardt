import { addDays } from "date-fns";

let setCookies = function(k, v) {
  const currTime = new Date();
  const cookieExpireTime = addDays(currTime, 30);
  document.cookie = `${k}=${v};expires=${cookieExpireTime}`;
};

let getCookies = function(k) {
  let cookies = document.cookie;
  if (cookies.length > 0) {
    if (cookies.indexOf(k) > -1) {
      let cookieArr = cookies.split(";");
      let formatCookieArr = [];
      for (let i = 0; i < cookieArr.length; i++) {
        if (cookieArr[i].indexOf(k) > -1) {
          return cookieArr[i].split("=")[1];
        }
      }
    }
  }
  return "None";
};

export { setCookies, getCookies };

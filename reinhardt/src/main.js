import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import IScrollView from "vue-iscroll-view";
import IScroll from "iscroll";
import qs from "qs";
import axios from "axios";
import VueCookies from "vue-cookies";
import Highcharts from "highcharts";
import HighchartsNoData from "highcharts/modules/no-data-to-display";
import mapInit from "highcharts/modules/map";
import HighchartsVue from "highcharts-vue";
import UKMapData from "./assets/data/ukgeo.json";
import PerfectScrollbar from "vue2-perfect-scrollbar";
import "vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css";

import { getLocation } from "./assets/api/distance.js";
import { setCookies, getCookies } from "./assets/api/cookie.js";
import { Get } from "./assets/api/api.js";
import { exportAddress } from "./assets/api/setting.js";
/**
 * 地图初始化
 */
HighchartsNoData(Highcharts);
mapInit(Highcharts);
Highcharts.maps["countries/gb/gb-all"] = UKMapData;
Vue.use(IScrollView, IScroll);
Vue.use(PerfectScrollbar);
Vue.use(VueCookies);
Vue.use(HighchartsVue);
Vue.config.productionTip = false;

Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;

/**
 * 获取用户经纬度，计算与目标的距离
 * 根据经纬度，计算出城市
 * 预选定义CurrentCity 避免在请求过程中取不到报错
 */
Vue.prototype.$currentCity = "";
getLocation().then(res => {
  Vue.prototype.$latitude = res.coords.latitude;
  Vue.prototype.$longtitude = res.coords.longitude;

  Get(`${exportAddress.postcodesio}postcodes`, {
    params: {
      lon: res.coords.longitude,
      lat: res.coords.latitude
    }
  }).then(res => {
    if (res.status == 200 && res.result) {
      Vue.prototype.$currentCity = res.result[0].nuts;
    }
  });
});
Vue.prototype.$cookieConfirm = true;
let cookieConfirm = getCookies("confirmCookie");
if (cookieConfirm == "None") {
  Vue.prototype.$cookieConfirm = false;
} else {
  if (cookieConfirm) {
    Vue.prototype.$cookieConfirm = true;
  } else {
    Vue.prototype.$cookieConfirm = false;
  }
}

Vue.prototype.$acceptCookie = function() {
  setCookies("confirmCookie", true);
};

/**
 * 初始化用户信息
 */

Vue.prototype.$userId = -1;
Vue.prototype.$userName = "";
Vue.prototype.$uuid = -1;
Vue.prototype.$gender = 1;
Vue.prototype.$email = "";

/**
 * 根据用户信息更新用户数据
 */

let cookieCheck = getCookies("uuid");
if (cookieCheck != "None") {
  Vue.prototype.$userId = getCookies("userId");
  Vue.prototype.$userName = getCookies("userName");
  Vue.prototype.$uuid = getCookies("uuid");
  Vue.prototype.$gender = getCookies("gender");
  Vue.prototype.$email = getCookies("email");
}

/**
 * 登录用户
 */

Vue.prototype.$addUser = function(id, name, uuid, gender, email) {
  Vue.prototype.$userId = id;
  setCookies("userId", id);
  Vue.prototype.$userName = name;
  setCookies("userName", name);
  Vue.prototype.$uuid = uuid;
  setCookies("uuid", uuid);
  Vue.prototype.$gender = gender;
  setCookies("gender", gender);
  Vue.prototype.$email = email;
  setCookies("email", email);
};

/**
 * 用户登出
 */

Vue.prototype.$logout = function() {
  Vue.prototype.$userId = "";
  setCookies("userId", "", -1);
  Vue.prototype.$userName = "";
  setCookies("userName", "", -1);
  Vue.prototype.$uuid = uuid;
  setCookies("uuid", "", -1);
  Vue.prototype.$gender = "";
  setCookies("gender", "", -1);
  Vue.prototype.$email = "";
  setCookies("email", "", -1);
};

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");

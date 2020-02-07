import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import IScrollView from "vue-iscroll-view";
import IScroll from "iscroll";
import qs from "qs";
import axios from "axios";

Vue.use(IScrollView, IScroll);
Vue.config.productionTip = false;
Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;
Vue.prototype.$userId = -1;
Vue.prototype.$userName = "";
Vue.prototype.$uuid = -1;
Vue.prototype.$addUser = function(id, name, uuid) {
  Vue.prototype.$userId = id;
  Vue.prototype.$userName = name;
  Vue.prototype.$uuid = uuid;
};

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");

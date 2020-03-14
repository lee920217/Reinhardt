import Vue from 'vue'
import App from './App.vue'
import qs from 'qs'
import axios from 'axios'
import './plugins/element.js'

import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

Vue.use(VueQuillEditor)

Vue.prototype.$axios = axios
Vue.prototype.$qs = qs

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')

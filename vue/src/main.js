import Vue from 'vue'
import App from './App.vue'
import router from './router'
import uploader from 'vue-simple-uploader'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'


Vue.config.productionTip = false
Vue.prototype.$axios=axios
axios.defaults.baseURL='/api'
Vue.use(router)
Vue.use(ElementUI)
Vue.use(uploader)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
// window.addEventListener('popstate',function(){
//   this.history.pushState(null,null,this.document.URL)
// })
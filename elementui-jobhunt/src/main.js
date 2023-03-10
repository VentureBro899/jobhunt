import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import * as echarts from 'echarts'
import request from '@/utils/request.js'
Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.prototype.$echarts = echarts
Vue.prototype.$request = request
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

Vue.config.errorHandler = function(err, vm, info) {
  if (err) { ElementUI.Message.error('网络异常') }
}

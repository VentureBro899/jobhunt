import Vue from 'vue'
import App from './App.vue'
import router from './router'
// awesome图标库
import 'font-awesome/css/font-awesome.min.css'

import {
  Tabbar, TabbarItem, Swipe, SwipeItem, Icon, Col, Row, Image as VanImage,
  Grid, GridItem, Divider, Button, Search, Tag, NavBar, Cell, CellGroup,
  Form, Field, Picker, Popup, Checkbox, CheckboxGroup, Uploader, Toast,
  Dialog, Lazyload, Skeleton, List
} from 'vant'

// 全局组件
import Header from '@/components/Header.vue'
import Loading from '@/components/LoadingPage.vue'

import request from '@/utils/request.js'
Vue.prototype.$request = request
Vue.use(VanImage)
Vue.use(Swipe)
Vue.use(SwipeItem)
Vue.use(Tabbar)
Vue.use(TabbarItem)
Vue.use(Icon)
Vue.use(Col)
Vue.use(Row)
Vue.use(Grid)
Vue.use(GridItem)
Vue.use(Divider)
Vue.use(Button)
Vue.use(Search)
Vue.use(Tag)
Vue.use(NavBar)
Vue.use(Cell)
Vue.use(CellGroup)
Vue.use(Form)
Vue.use(Field)
Vue.use(Picker)
Vue.use(Popup)
Vue.use(Checkbox)
Vue.use(CheckboxGroup)
Vue.use(Uploader)
Vue.use(Toast)
Vue.use(Dialog)
Vue.use(Lazyload)
Vue.use(Skeleton)
Vue.use(List)
Vue.config.productionTip = false
Vue.component('Header', Header)
Vue.component('Loading', Loading)
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

Vue.config.errorHandler = function(err, vm, info) {
  if (err) {
    Toast('网络异常')
  }
}

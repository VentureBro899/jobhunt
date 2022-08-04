import Vue from 'vue'
import VueRouter from 'vue-router'
import { Toast } from 'vant'
import HomeView from '../views/HomeView.vue'
import UserView from '@/views/UserView.vue'
import JobDetailsView from '@/views/JobDetailsView.vue'
import SortView from '@/views/SortView.vue'
import MessageView from '@/views/MessageView.vue'
import UserJobAddView from '@/views/UserJobAddView.vue'
import UserJobUpdView from '@/views/UserJobUpdate.vue'
import UserJobListView from '@/views/UserJobListView.vue'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import MypacketView from '@/views/MyPacketView.vue'
import MysettingView from '@/views/MySettingView.vue'
import SpecificUserJob from '@/views/SpecificUserJob.vue'

Vue.use(Toast)

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: HomeView
  },
  {
    path: '/sort',
    component: SortView
  },
  {
    path: '/message',
    component: MessageView
  },
  {
    path: '/user',
    component: UserView
  },
  {
    path: '/login',
    component: LoginView
  },
  {
    path: '/register',
    component: RegisterView
  },
  {
    path: '/mine/jobadd',
    component: UserJobAddView
  },
  {
    path: '/mine/jobupd/:id',
    component: UserJobUpdView
  },
  {
    path: '/mine/joblist',
    component: UserJobListView
  },
  {
    path: '/mine/setting',
    component: MysettingView
  },
  {
    path: '/mine/packet',
    component: MypacketView
  },
  {
    path: '/jobDetails/:uid/:id',
    component: JobDetailsView
  },
  {
    path: '/userjobs/:uid',
    component: SpecificUserJob
  }
]

const router = new VueRouter({
  routes,
  base: '/master/'
})

router.beforeEach((to, from, next) => {
  if (localStorage.getItem('id') !== null) { // 已登录,除登录注册页面，其他页面直接放行
    if (to.path.indexOf('register') !== -1 || to.path.indexOf('login') !== -1) { next('/user') } else { next() }
  } else { // 未登录
    if (to.path.indexOf('mine') === -1) { // 非登录才能访问的页面直接放行
      next()
    } else { // 登录才能访问的页面跳转到登陆页面
      Toast('您还未登录，请先登录')
      next('/login')
    }
  }
})

export default router

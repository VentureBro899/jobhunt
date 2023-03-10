import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [

  {
    path: '/userList',
    component: () => import('@/views/user/UserListView.vue')
  },
  {
    path: '/userUpdate/:uid',
    component: () => import('@/views/user/UserUpdateView.vue')
  },
  {
    path: '/joblist',
    component: () => import('@/views/job/JobListView.vue')
  },
  {
    path: '/jobupdate/:id',
    component: () => import('@/views/job/JobUpdateView.vue')
  },
  {
    path: '/pptlist',
    component: () => import('@/views/ppt/PPTListView.vue')
  },
  {
    path: '/pptupdate/:id',
    component: () => import('@/views/ppt/PPTUpdateView.vue')
  },
  {
    path: '/pptadd',
    component: () => import('@/views/ppt/PPTAddVIew.vue')
  },
  {
    path: '/notice',
    component: () => import('@/views/notice/NoticeUpdateView.vue')
  },
  {
    path: '/general',
    component: () => import('@/views/GeneralView.vue')
  },
  {
    path: '/404',
    component: () => import('@/views/right/404.vue')
  },
  {
    path: '/noright',
    component: () => import('@/views/right/noright.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const uid = localStorage.getItem('id')
  if (to.path.indexOf('noright') !== -1 || to.path.indexOf('404') !== -1) {
    next()
    return
  }
  if (uid != null && uid === '10000') { // 已登录
    next()
  } else { // 未登录
    next('/noright')
  }
})

export default router

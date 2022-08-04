<template>
  <div>
    <div class="logincheck">
      <van-image round width="100px" height="100px" :src="avatar" />
      <h3 v-if="islogined">欢迎你,{{username}}</h3>
      <div v-else class="unlogin">
        <van-button type="primary" to="/login">登录</van-button>
        <van-button type="danger" to="/register">注册</van-button>
      </div>
    </div>
    <van-grid clickable :column-num="2">
      <van-grid-item icon="newspaper-o" text="我发布的" to="/mine/joblist" />
      <van-grid-item icon="completed" text="签到" @click="sign" />
      <van-grid-item icon="upgrade" text="发布帖子" to="/mine/jobadd" />
      <!-- <van-grid-item icon="paid" text="我的钱包" to="/mine/packet" /> -->
      <van-grid-item icon="setting-o" text="账号管理" to="/mine/setting" />
      <van-grid-item icon="service-o" text="联系客服" to="/" />
      <van-grid-item v-if="islogined" icon="close" text="退出登录" @click="logout" />
    </van-grid>
  </div>
</template>

<script>
export default {
  data() {
    return {
      islogined: localStorage.getItem('id') !== null,
      username: localStorage.getItem('username'),
      avatar: localStorage.getItem('avatar')
    }
  },
  methods: {
    logout() {
      this.$dialog
        .confirm({
          title: '提示',
          message: '确定要退出吗'
        })
        .then(() => {
          this.$request
            .post('/user/logout')
            .then(() => {
              this.$toast('已退出')
              this.islogined = false
              localStorage.clear()
              this.avatar = ''
              this.username = ''
            })
            .cathch(() => {
              this.$toast.fail('网络异常')
            })
        })
        .catch(() => {
          this.$toast('已取消操作')
        })
    },
    sign() {
      if (localStorage.getItem('id') !== null) {
        this.$request.post('/user/sign').then(res => {
          if (res.data.code === 20000) {
            this.$toast.success('签到成功，余额+1')
          } else {
            this.$toast.success('你今天已签到，请勿重复签到')
          }
        })
      } else {
        this.$toast('您还未登录，请先登录')
        this.$router.replace('/login')
      }
    }
  },
  created() {
    document.title = '个人中心'
    // 判断用户登录状态
    this.$request
      .get('/user/islogin')
      .then(res => {
        if (res.data.code !== 20000) {
          localStorage.clear()
          this.islogined = false
          this.avatar = ''
          this.username = ''
        }
      })
      .catch(() => {
        localStorage.clear()
        this.islogined = false
        this.avatar = ''
        this.username = ''
      })
  }
}
</script>

<style lang="less" scoped>
.logincheck {
  height: 200px;
  background: #39a9ed;
  padding: 15px 5px;
  display: -webkit-flex;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  h3 {
    color: #f5f5f5;
    font-weight: normal;
  }
  .unlogin {
    .van-button {
      margin-left: 10px;
      width: 80px;
    }
  }
}
</style>

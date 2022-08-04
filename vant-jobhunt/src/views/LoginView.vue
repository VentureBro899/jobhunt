<template>
  <div class="login">
    <Header title="用户登录"></Header>
    <h1>用户登录</h1>
    <van-form @submit="onSubmit">
      <van-field v-model="username" name="账户" label="账户" placeholder="手机号/邮箱" :rules="[{ required: true, message: '请填写你的昵称' }]" />
      <van-field v-model="password" type="password" name="密码" label="密码" placeholder="输入你的密码" :rules="[{ required: true, message: '密码必须为6-20位英文字母、数字或者符号（除空格），且字母、数字和标点符号至少包含两种',pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){8,20}$/}]" />
      <div class="tip">没有账号?<a @click="switchPage">去注册</a></div>
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit">登录</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    async onSubmit(values) {
      const { data: res } = await this.$request.post(
        '/user/login',
        { account: this.username, password: this.password },
        {
          // 修改请求头
          headers: {
            'Content-Type': 'multipart/form-data'
          },
          withCredentials: true
        }
      )
      if (res.code === 20000) {
        this.$toast.success('登录成功')
        localStorage.setItem('id', res.data.id)
        localStorage.setItem('username', res.data.username)
        localStorage.setItem('avatar', res.data.avatar)
        setTimeout(() => {
          this.$router.replace('/user')
        }, 1000)
      } else {
        this.$toast.fail('登录失败，用户名或密码错误')
      }
    },
    switchPage() {
      this.$router.push('register')
    }
  },
  created() {
    console.log(document.cookie)
  }
}
</script>

<style lang="less">
h1 {
  text-align: center;
  color: #39a9ed;
}

.tip {
  margin: 10px;
  a {
    color: #39a9ed !important;
  }
  font-size: 12px;
}
</style>

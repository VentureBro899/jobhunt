<template>
  <div class="register">
    <Header title="用户注册"></Header>
    <h1>用户注册</h1>
    <van-form @submit="onSubmit">
      <van-field name="uploader" label="头像" :rules="[{ required: true, message: '请先上传头像' }]">
        <template #input>
          <van-uploader v-model="uploader" max-count=1 :max-size="2 * 1024 * 1024" :after-read="afterRead" @oversize="onOversize" />
        </template>
      </van-field>
      <van-field v-model="username" name="你的昵称" label="你的昵称" placeholder="输入你的昵称" :rules="[{ required: true, message: '请填写你的昵称' }]" />
      <van-field v-model="telephone" type="telephone" name="你的手机号" label="你的手机号" placeholder="输入你的手机号"
        :rules="[{ required: true, message: '请填写正确的手机号',pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/ }]" />
      <van-field v-model="email" type="email" name="你的邮箱" label="你的邮箱" placeholder="输入你的邮箱"
        :rules="[{ required: true, message: '请填写正确的邮箱',pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/  }]" />
      <van-field v-model="password" type="password" name="你的密码" label="密码" placeholder="输入你的密码"
        :rules="[{ required: true, message: '密码必须为6-20位英文字母、数字或者符号（除空格），且字母、数字和标点符号至少包含两种',pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){8,20}$/}]" />
      <div class="tip">已有账号?<a @click="switchPage">去登录</a></div>
      <div style="margin: 16px;">
        <van-button v-if="!success" round block type="info" native-type="submit">注册</van-button>
        <van-button v-if="success" round block type="info" to="/login">去登陆</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      telephone: '',
      email: '',
      success: false, // 注册是否成功
      uploader: []
    }
  },
  methods: {
    async onSubmit(values) {
      const { data: res } = await this.$request.post(
        '/user/register',
        { username: this.username, password: this.password, phone: this.telephone, email: this.email, avatar: this.uploader[0].url },
        {
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        }
      )
      if (res.code === 20000) {
        this.success = true
        this.$toast.success('注册成功')
      } else {
        this.$toast.fail(res.message)
      }
    },
    switchPage() {
      this.$router.push('login')
    },
    afterRead(fileObj, detail) {
      fileObj.status = 'uploading'
      fileObj.message = '上传中'
      const upfile = new FormData()
      upfile.append('uploadFile', fileObj.file)
      this.$request
        .post('/user/upload', upfile, {
          headers: {
            // 传文件要用multipart/form-data形式
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(res => {
          if (res.data.code === 20000) {
            fileObj.status = 'done'
            fileObj.message = '上传成功'
            // 一张图片直接存uploader
            this.uploader = [{ url: res.data.data }]
          } else if (res.data.code === 40003) {
            this.$toast.fail('只支持jpg png gif格式的图片')
            fileObj.status = 'failed'
            fileObj.message = '格式错误'
          } else {
            fileObj.status = 'failed'
            fileObj.message = '上传失败'
          }
        })
        .catch(() => {
          fileObj.status = 'failed'
          fileObj.message = '服务繁忙'
        })
    },
    onOversize(fileObj) {
      this.$toast.success('图片大小不能大于2MB')
    }
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
    color: #39a9ed;
  }
}
</style>

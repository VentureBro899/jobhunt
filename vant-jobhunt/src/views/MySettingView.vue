<template>
  <div class="private">
    <Header title="我的信息"></Header>
    <van-form @submit="onSubmit">
      <van-uploader v-model="uploader" max-count=1 :max-size="2 * 1024 * 1024" :after-read="afterRead" @oversize="onOversize" />
      <van-cell-group>
        <van-field v-model="myinfo.id" label="标识" readonly placeholder="加载中..." />
        <van-field v-model="myinfo.username" label="用户名" placeholder="加载中..." :rules="[{required,message:'用户名不能为空，且长度为2-20',pattern: /^[\u4e00-\u9fa5\w]{2,10}$/}]" />
        <van-field v-model="myinfo.email" label="邮箱" readonly placeholder="加载中..." />
        <van-field v-model="myinfo.phone" label="手机" readonly placeholder="加载中..." />
        <van-field v-model="myinfo.balance" label="余额" readonly placeholder="加载中..." />
        <div class="repass">
          <van-field v-model="password" label="身份验证" placeholder="请输入原密码进行身份验证" type="password" :rules="[{ required: true, message: '密码必须为6-20位英文字母、数字或者符号（除空格），且字母、数字和标点符号至少包含两种',pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){8,20}$/}]" />
          <span class="tip">密码修改,留空则不修改：</span>
          <van-field v-model="newPassword" label="新密码" placeholder="请输入新密码" type="password" :rules="[{ required: false, message: '密码必须为6-20位英文字母、数字或者符号（除空格），且字母、数字和标点符号至少包含两种',pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){0,20}$/}]" />
          <van-field v-model="confirmPassword" label="确认新密码" placeholder="请再次输入新密码" type="password" :rules="[{ required: false, message: '密码必须为6-20位英文字母、数字或者符号（除空格），且字母、数字和标点符号至少包含两种',pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){0,20}$/}]" />
          <van-button plain hairline type="info">提交修改</van-button>
        </div>
      </van-cell-group>
    </van-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      myinfo: {
        id: 0,
        username: '',
        phone: '',
        email: '',
        avatar: '',
        balance: 0
      },
      password: null,
      newPassword: null,
      confirmPassword: null,
      // uploader必须为一个数组
      uploader: []
    }
  },
  methods: {
    onSubmit() {
      if (this.uploader[0] === undefined) {
        this.$toast.fail('请上传头像')
        return
      }
      if (this.newPassword !== this.confirmPassword) {
        this.$toast.fail('两次输入的新密码不一致')
        return
      }
      this.$request
        .put(
          '/user/updateInfo',
          { account: this.myinfo.phone, oldPassword: this.password, newPassword: this.newPassword, avatar: this.uploader[0].url, username: this.myinfo.username },
          {
            // 修改请求头
            headers: {
              'Content-Type': 'multipart/form-data'
            },
            withCredentials: true
          }
        )
        .then(res => {
          if (res.data.code === 30001) {
            this.$toast.fail('原密码错误，身份验证失败')
          }
          if (res.data.code === 20000) {
            this.$toast.fail('信息修改成功')
            // 修改成功后需要更新下本地的用户信息
            localStorage.setItem('username', this.myinfo.username)
            localStorage.setItem('avatar', this.uploader[0].url)
          }
        })
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
  },
  mounted() {
    this.$request.get('/user/getInfo').then(res => {
      if (res.data.code === 20000) {
        this.myinfo = res.data.data
        this.uploader = [{ url: this.myinfo.avatar }]
      }
    })
  }
}
</script>

<style lang="less">
.private {
  background: #fff;
  .van-uploader {
    margin: 10px auto;
    display: flex;
    justify-content: center;
  }
}
.repass {
  background: #fff;
  padding-bottom: 15px;
  .tip {
    margin: 10px 20px;
    color: #1989fa;
  }
  .van-button {
    margin: 10px auto;
    display: block;
  }
}
</style>

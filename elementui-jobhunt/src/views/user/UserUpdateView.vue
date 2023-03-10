<template>
  <div class="update">
    <el-form label-position="top" label-width="80px" :model="formData">
      <el-form-item label="名称">
        <el-input v-model="formData.username"></el-input>
      </el-form-item>
      <el-form-item label="手机">
        <el-input v-model="formData.phone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="formData.email"></el-input>
      </el-form-item>
      <el-form-item label="余额">
        <el-input v-model="formData.balance"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="formData.password" placeholder="留空则不修改"></el-input>
      </el-form-item>

      <el-form-item size="large">
        <el-button type="primary" @click="onSubmit">提交修改</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
export default {
  data() {
    return {
      formData: {
        username: '',
        phone: '',
        email: '',
        level: '',
        balance: '',
        password: ''
      }
    }
  },
  methods: {
    onSubmit() {
      this.$request.put('/master/user', this.formData).then(res => {
        if (res.data.code === 20000) {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
        } else if (res.data.code === 30003) {
          this.$message({
            type: 'error',
            message: res.data.msg
          })
        } else {
          this.$message({
            type: 'error',
            message: '网络异常,修改未提交'
          })
        }
      })
    }
  },
  mounted() {
    this.$request.get('/master/user/' + this.$route.params.uid).then(res => {
      if (res.data.code === 20000) {
        this.formData = res.data.data
      } else if (res.data.code === 30003) {
        this.$message({
          type: 'error',
          message: '修改失败!手机号或邮箱已被注册'
        })
      } else {
        // 找不到对应id用户则跳转到404
        this.$router.replace('/404')
      }
    })
  }
}
</script>

<style>
.update {
  width: 500px;
  padding: 20px;
}
</style>

<template>
  <div class="update">
    <el-form label-position="top" label-width="80px" :model="formData">
      <el-form-item label="id">
        <el-input v-model="formData.id"></el-input>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="formData.description"></el-input>
      </el-form-item>
      <el-form-item label="图片">
        <el-input v-model="formData.image"></el-input>
      </el-form-item>
      <el-form-item label="路由">
        <el-input v-model="formData.router" placeholder="以'/'开始"></el-input>
      </el-form-item>
      <el-checkbox v-model="formData.showing">展示</el-checkbox>
      <el-form-item size="large" class="btn">
        <el-button type="primary" @click="onSubmit">提交修改</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
export default {
  data() {
    return {
      formData: {}
    }
  },
  methods: {
    onSubmit() {
      this.$request.put('/master/ppt', this.formData).then(res => {
        if (res.data.code === 20000) {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push('/pptList')
        } else if (res.data.code === 50000) {
          this.$message({
            type: 'error',
            message: '参数异常'
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
    this.$request.get('/master/ppt/' + this.$route.params.id).then(res => {
      if (res.data.code === 20000) {
        this.formData = res.data.data
      } else if (res.data.code === 50000) {
        this.$message({
          type: 'error',
          message: '参数异常'
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
.btn {
  margin-top: 20px;
}
</style>

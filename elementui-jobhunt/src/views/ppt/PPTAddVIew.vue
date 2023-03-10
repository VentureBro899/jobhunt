<template>
  <div class="update">
    <el-form label-position="top" label-width="80px" :model="formData">
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
        <el-button type="primary" @click="onSubmit">添加</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
export default {
  data() {
    return {
      formData: {
        description: '',
        image: '',
        router: '',
        showing: true
      }
    }
  },
  methods: {
    onSubmit() {
      this.$request.post('/master/ppt', this.formData).then(res => {
        if (res.data.code === 20000) {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push('/pptList')
        } else {
          this.$message({
            type: 'error',
            message: '网络异常,修改未提交'
          })
        }
      })
    }
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

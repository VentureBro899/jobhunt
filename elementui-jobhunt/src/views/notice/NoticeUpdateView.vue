<template>
  <div>
    <el-form label-position="top" label-width="80px" :model="formData">
      <el-form-item label="内容">
        <el-input v-model="formData.content" placeholder="建议20个字以内"></el-input>
      </el-form-item>
      <el-form-item label="文本颜色">
        <el-color-picker v-model="formData.color"></el-color-picker>
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
        content: '',
        color: '#cf0bec'
      }
    }
  },
  methods: {
    onSubmit() {
      this.$request.put('/notice', this.formData).then(res => {
        if (res.data.code === 20000) {
          this.$message({
            type: 'success',
            message: '修改成功!'
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
    this.$request.get('/notice').then(res => {
      if (res.data.code === 20000 && res.data.data != null) {
        this.formData = res.data.data
      }
    })
  }
}
</script>

<style>
</style>

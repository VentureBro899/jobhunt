<template>
  <div class="update">
    <el-form label-position="top" label-width="80px" :model="formData">
      <el-form-item label="id">
        <el-input v-model="formData.id" readonly></el-input>
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="formData.cname" label="分类">
          <el-option v-for="item in options" :key="item.value" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="formData.title"></el-input>
      </el-form-item>
      <el-form-item label="浏览量">
        <el-input v-model="formData.view"></el-input>
      </el-form-item>
      <el-form-item label="点赞量">
        <el-input v-model="formData.thumb"></el-input>
      </el-form-item>
      <el-checkbox v-model="formData.topping">置顶</el-checkbox>
      <el-checkbox v-model="formData.hot">加热</el-checkbox>
      <el-checkbox v-model="formData.keeping">上架</el-checkbox>
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
      formData: {},
      options: [{ value: '微信单' }, { value: 'QQ单' }, { value: '需求发布' }, { value: '骗子曝光' }]
    }
  },
  methods: {
    onSubmit() {
      this.$request.put('/master/job', this.formData).then(res => {
        if (res.data.code === 20000) {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push('/jobList')
        } else if (res.data.code === 40003) {
          this.$message({
            type: 'error',
            message: '约束错误，修改失败'
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
    this.$request.get('/master/job/' + this.$route.params.id).then(res => {
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

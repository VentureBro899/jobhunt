<template>
  <div>
    <Header title="信息发布"></Header>
    <div class="pgtitle">
      1. 请勿发布欺诈，违反国家法律法规等信息<br>
      2. 禁止发布与广告内容不符的虚假信息<br>
      3. 网站发布信息内容与本站无关。如遇违法信息不排除报警处理<br>
    </div>
    <van-form validate-trigger="onChange" @submit="onSubmit">
      <van-field readonly clickable label="分类" :value="sort" placeholder="选择分类" @click="showPicker = true" />
      <van-popup v-model="showPicker" round position="bottom">
        <van-picker show-toolbar :columns="columns" @cancel="showPicker = false" @confirm="onConfirm" />
      </van-popup>
      <van-field v-model.trim="title" name="标题" label="标题" placeholder="请输入标题" :rules="[{required,message:'标题不能为空，且长度小于20',pattern: /^[\u4e00-\u9fa5\w]{1,20}$/}]" />
      <van-field v-model="detail" rows="8" autosize label="详情" type="textarea" placeholder="请输入详情" :rules="[{required,message:'详情不能为空，且在10到4000字之间',pattern: /^[\u4e00-\u9fa5\w\W\d\D\r\t\n\s]{10,4000}$/}]" />
      <div class=" uploader">
        <van-uploader v-model="fileList" :after-read="afterRead" :max-size="2 * 1024 * 1024" @oversize="onOversize" @delete="onDel" />
      </div>
      <van-field v-model.trim="contactor1" label="联系方式1" />
      <van-field v-model.trim="contactor2" label="联系方式2" />
      <!--  <van-field v-model.trim="contactor2" label="联系方式2" :rules="[{message:'联系方式长度必须小于30个字，不含特殊字符',pattern: /^[\u4e00-\u9fa5\w]{0,20}$/}]" /> -->
      <van-field v-show="sort=='需求发布'" v-model.number="groupnum" type="digit" label="团队人数" />
      <van-field v-model.number="price" type="digit" label="单价" />
      <div class="checkbox">
        <van-checkbox v-model="guarantee">支持担保</van-checkbox>
        <van-checkbox v-model="showing">展示</van-checkbox>
      </div>
      <div class="btng">
        <van-button type="info" :disabled="balance<1">金币支付</van-button>
        <div class="balance">我的金币:<span>{{ balance}}</span>,本次扣除:1</div>
      </div>

    </van-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      /** vant选择器需要三个参数
       * sort:选中值
       * showPicker:是否展开选项
       * columns:可选值
       */
      sort: '',
      showPicker: false,
      columns: ['QQ单', '微信单', '需求发布', '骗子曝光'],

      title: '',
      detail: '',
      fileList: [], // 选中上传文件列表
      uploadedFile: [], // 已上传待提交文件
      contactor1: '',
      contactor2: '',
      groupnum: 0,
      price: 0,
      guarantee: false,
      showing: true,

      balance: 0,
      picture: ''
    }
  },
  methods: {
    // 下拉选择
    onConfirm(value, index) {
      this.sort = value
      this.showPicker = false
    },
    // 读取本地文件完成
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
            this.uploadedFile.push({ detail: res.data.data })
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
    // 文件过大
    onOversize(fileObj) {
      this.$toast.fail('图片大小不能大于2MB')
    },

    // 删除文件后的操作
    onDel(fileOBj, detail) {
      // 将上传成功待提交的文件从数组中删除
      this.uploadedFile.splice(detail.index)
    },

    // 提交表单
    onSubmit() {
      for (const x in this.uploadedFile) {
        this.picture += Object.values(this.uploadedFile[x]) + '@@'
      }
      this.$request
        .post('/job', {
          cname: this.sort,
          title: this.title,
          detail: this.detail,
          groupnum: this.groupnum,
          guarantee: this.guarantee,
          price: this.price,
          contactor1: this.contactor1,
          contactor2: this.contactor2,
          date: this.date,
          showing: this.showing,
          picture: this.picture
        })
        .then(res => {
          if (res.data.code === 20000) {
            this.$toast.success('发布成功')
            this.$router.push('/mine/joblist')
          } else {
            this.$toast.success('发布失败')
          }
        })
    }
  },
  mounted() {
    this.$request.get('/user/getInfo').then(res => {
      if (res.data.code === 20000) {
        this.balance = res.data.data.balance
      }
    })
  }
}
</script>

<style lang="less" scoped>
.van-form {
  background: #fff;
  padding: 20px;
}
.pgtitle {
  text-align: left;
  font-size: 18px;
  padding: 30px;
  background: #fff;
  color: #39a9ed;
}
.checkbox {
  padding: 20px;
  display: flex;
  justify-content: space-evenly;
  background: #fff;
}
.btng {
  button {
    width: 200px;
    letter-spacing: 10px;
    text-indent: 10px;
  }
  text-align: center;
  .balance {
    margin-top: 5px;
    span {
      color: #d1920a;
    }
  }
}
.uploader {
  display: flex;
  justify-content: flex-end;
}
</style>

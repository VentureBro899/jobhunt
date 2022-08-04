<template>
  <div>
    <Header></Header>
    <div v-if="!loading" class="head">
      <div class="picture">
        <van-image round width="70px" height="70px" :src="userinfo.avatar">
        </van-image>
      </div>
      <div class="jobinfo">
        <h3>{{userinfo.username}}</h3>
        <div class="tip">
          <span>发布{{userTotal}}条</span>
        </div>
      </div>
    </div>
    <div v-else>
      <van-skeleton title avatar :row="3" :loading="loading">
        <div>网络异常</div>
      </van-skeleton>
    </div>
    <div class="joblist">
      <van-list v-model="jobloading" :finished="finished" finished-text="没有更多了" @load="onLoad">
        <Job style="
    margin-top: 5px;
    padding: 15px" v-for="job in jobList" :key="job.id" :id="job.id" :uid="job.uid" :title="job.title" :cname="job.cname" :avatar="job.avatar" :publisher="job.username" :detail="job.detail" :view="job.view" :date="job.date" :price="job.price" :topping="job.topping" :guarantee="job.guarantee"></Job>
      </van-list>
    </div>
  </div>
</template>

<script>
import Job from '@/components/Job.vue'
export default {
  data() {
    return {
      userinfo: {
        username: '参数错误',
        avatar: ''
      },
      userTotal: 0,
      loading: true, // 用户信息加载标志
      jobloading: true, // job列表加载标志
      finished: false,
      jobList: [],
      current: 1,
      size: 10
    }
  },
  methods: {
    onLoad() {
      this.jobloading = true
      this.current++
      this.getAll()
    },
    getAll() {
      this.$request.get(`/job/jau/${this.$route.params.uid}/${this.current}/${this.size}`).then(res => {
        this.jobloading = false
        if (res.data.data.records.length === 0) {
          this.finished = true
          return
        }
        this.jobList = [...this.jobList, ...res.data.data.records]
      })
    }
  },
  mounted() {
    // 获取用户信息
    this.$request
      .get(`/user/getinfo/${this.$route.params.uid}`)
      .then(res => {
        if (res.data.data == null) {
          this.$toast.fail('用户不存在')
          return
        }
        this.userinfo = res.data.data
        this.loading = false
      })
      .catch(() => {
        this.$toast.fail('网络异常')
      })
    // 获取用户帖子总数
    this.$request.get(`/job/getTotal/${this.$route.params.uid}`).then(res1 => {
      this.userTotal = res1.data.data
    })

    // 获取用户帖子
    this.getAll()
  },
  components: { Job }
}
</script>

<style lang="less" scoped>
.head {
  display: flex;
  justify-content: left;
  position: relative;
  background: #fff;
  margin-bottom: 15px;
  .picture {
    padding: 10px 15px;
  }

  h3 {
    font-weight: normal;
    margin: 0px;
  }
  .jobinfo {
    margin-left: 15px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    .tip {
      margin: 5px 0px;
      font-size: 12px;
      color: #7e7e83;
      span {
        margin-left: 10px;
      }

      span:nth-child(1) {
        margin-left: 0px;
      }
    }
    .fa-angle-right {
      position: absolute;
      top: 18px;
      right: 25px;
      font-size: 30px;
    }
  }
}
</style>

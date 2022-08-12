<template>
  <div class="home">
    <div class="search">
      <img src="@/assets/logo.png">
      <van-search v-model="keyword" placeholder="请输入搜索关键词" @search="onSearch" />
    </div>
    <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white" :height="150">
      <van-swipe-item v-for="item in ppt" :key="item.id">
        <div v-if="item.image=== ''">{{item.description}}</div>
        <div v-else><img class="pptimg" :src="item.image" @click="gourl(item.router)"></div>
      </van-swipe-item>
    </van-swipe>
    <div class="van-ellipsis statistic"><span class="fa fa-bell"></span>帖子总数:<h5>{{jobTotal}}</h5> 今天发布:<h5>{{jobToday}}</h5>
    </div>
    <van-row>
      <van-col span="6" class="channel" @click="sort('微信单')">
        <span class="fa fa-wechat fa-2x"></span>
        微信单
      </van-col>
      <van-col span="6" class="channel" @click="sort('QQ单')">
        <span class="fa fa-qq fa-2x"></span>
        QQ单
      </van-col>
      <van-col span="6" class="channel" @click="sort('需求发布')">
        <span class="fa fa-edit fa-2x"></span>
        需求发布
      </van-col>
      <van-col span="6" class="channel" @click="sort('骗子曝光')">
        <span class="fa fa-desktop fa-2x"></span>
        骗子曝光
      </van-col>
    </van-row>
    <div class="van-ellipsis notice" :style="'color:' + notice.color"><span class="fa fa-volume-up"></span>{{notice.content}}</div>
    <Loading :initialized="initialized"></Loading>
    <Job v-for="job in topList" :key="job.id" :id="job.id" :uid="job.uid" :title="job.title" :cname="job.cname" :avatar="job.avatar" :publisher="job.username" :detail="job.detail" :view="job.view" :date="job.date" :price="job.price" :topping="job.topping" :guarantee="job.guarantee"></Job>
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
      <Job v-for="job in jobList" :key="job.id" :id="job.id" :uid="job.uid" :title="job.title" :cname="job.cname" :avatar="job.avatar" :publisher="job.username" :detail="job.detail" :view="job.view" :date="job.date" :price="job.price" :topping="job.topping" :guarantee="job.guarantee"></Job>
    </van-list>
  </div>
</template>

<script>
import Job from '@/components/Job.vue'
export default {
  data() {
    return {
      keyword: '',
      jobList: [],
      notice: { content: '公告加载中...', color: '#cf0bec' },
      ppt: [],
      current: 1,
      size: 10,
      cname: '',
      topList: [], // 置顶的

      jobTotal: 0,
      jobToday: 0,
      initialized: false, // 页面初始化完毕则为false
      loading: true,
      finished: false,
      search: false
    }
  },
  name: 'HomeView',
  components: { Job },
  methods: {
    // 搜索时按回车触发
    onSearch() {
      this.search = true
      this.current = 0
      this.jobList = []
      this.finished = false
      this.onLoad()
    },

    onLoad() {
      this.loading = true
      this.current++
      this.getList()
    },
    async getList() {
      const url = this.search ? `/job/jau/${this.current}/${this.size}?keyword=${this.keyword}&cname=${this.cname}` : `/job/jau/${this.current}/${this.size}`
      const { data: res } = await this.$request.get(url)
      if (res.code === 20000) {
        this.initialized = true
        this.loading = false
        if (res.data.length === 0) {
          // 用数组长度为0确定已全部加载完毕
          this.finished = true
          return
        }
        this.jobList = [...this.jobList, ...res.data]
      }
    },
    sort(cname) {
      this.cname = cname
      this.search = true
      this.current = 0
      this.jobList = []
      this.finished = false
      this.getTopping()
      this.onLoad()
    },
    getTopping() {
      // 获取置顶的
      this.$request.get(`/job/jau/1/20?topping=true&keyword=${this.keyword}&cname=${this.cname}`).then(res1 => {
        if (res1.data.code === 20000) {
          this.topList = res1.data.data
        }
      })
    },
    gourl(url) {
      if (url != null && url.length > 0) {
        location.href = url
      }
    }
  },
  mounted() {
    // 获取统计数据
    this.$request.get('/job/getTotal').then(res1 => {
      if (res1.data.code === 20000) {
        this.jobTotal = res1.data.data.total
        this.jobToday = res1.data.data.today
      }
    })
    // 获取公告
    this.$request.get('/notice').then(res1 => {
      if (res1.data.code === 20000) {
        this.notice = res1.data.data
      }
    })
    // 获取ppt
    this.$request.get('/master/ppt').then(res1 => {
      if (res1.data.code === 20000) {
        this.ppt = res1.data.data.records
      }
    })

    // 获取帖子列表
    this.getTopping()
    this.getList()
  },
  created() {
    document.title = '大地推广'
  }
}
</script>
<style lang="less" scoped>
.home {
  .search {
    display: flex;
    align-items: center;
    background: #fff;
    img {
      padding: 5px;
      height: 35px;
      width: 35px;
    }
    .van-search {
      width: 100%;
      padding-left: 0px;
    }
  }
  .van-row {
    background: #fff;
    margin: 2px 0;
  }
  .statistic {
    background: #fff;
    padding: 5px 10px;
    color: #7a7a7a;
    .fa-bell {
      margin-right: 10px;
    }
    h5 {
      display: inline;
      font-size: 15px;
      color: #d1920a;
    }
  }
  .notice {
    margin: 10px 0px;
    background: #fff;
    padding: 5px 10px;
    .fa {
      margin-right: 10px;
      color: #edaf1f;
    }
  }
}
.my-swipe .van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 150px;
  text-align: center;
  background-color: #39a9ed;
}

.channel {
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px 0px;
  cursor: pointer;
}
.pptimg {
  height: 150px;
  width: 100%;
}
</style>

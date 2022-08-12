<template>

  <div class="jobdetails">
    <Header></Header>
    <div class="main" v-if="jobwu != null">
      <div class="head">
        <div class="picture">
          <van-image round width="70px" height="70px" :src="jobwu.avatar">
          </van-image>
        </div>
        <div class="jobinfo">
          <h3>{{jobwu.username}}</h3>
          <div class="tip">
            <span>{{jobwu.date}}</span>
            <span>发布{{userTotal}}条</span>
            <span class="fa fa-angle-right" @click="userjobs">

            </span>
          </div>
        </div>
      </div>
      <van-divider>
        <div class="banner" @click="makeBanner(1)">生成海报</div>
      </van-divider>
      <div class="attr">
        <van-row>
          <van-col span="24"><span>标题：</span><span>{{jobwu.title}}</span></van-col>
          <van-col span="24"><span>团队人数：</span><span>{{jobwu.groupnum}}</span></van-col>
          <van-col span="24"><span>帖子类别：</span><span>{{jobwu.cname}}</span></van-col>
          <van-col span="24"><span>是否支持担保：</span><span>{{jobwu.guarantee ? '支持' : '不支持'}}</span></van-col>
          <van-col span="24"><span>联系方式1：</span><span>{{jobwu.contactor1}}</span></van-col>
          <van-col span="24"><span>联系方式2：</span><span>{{jobwu.contactor2}}</span></van-col>
        </van-row>

      </div>
      <van-divider content-position="left">详细信息</van-divider>
      <div class="desc">
        <pre>{{jobwu.detail}}
      </pre>
        <div class="images">
          <van-image v-for="(p,index) in pictures" :key="index" width="10rem" height="10rem" :src="p" />

        </div>
      </div>
      <div class="others">
        <span class="fa fa-thumbs-o-up" :style="color" @click="thumb">{{jobwu.thumb}}</span>
        <span>{{jobwu.view}}次浏览</span>
        <span>{{jobwu.thumb}}人想做</span>
      </div>
    </div>
    <van-skeleton v-else title avatar :row="8" />
    <div class="warning">
      <span>如遇无效、虚假、诈骗信息请立即举报</span>
      <p>提示：飞机无担保，被骗不负责，担保请认准客服飞机xxx</p>
    </div>
    <div class="jobs">
      <Job v-if="random != null" :id="random.id" :uid="random.uid" :title="random.title" :cname="random.cname" :avatar="random.avatar" :publisher="random.username" :detail="random.detail" :view="random.view" :date="random.date" :price="random.price" :topping="random.topping" :guarantee="random.guarantee"></Job>
    </div>

  </div>
</template>

<script>
import Job from '@/components/Job.vue'
import Header from '@/components/Header.vue'
export default {
  data() {
    return {
      jobwu: null,
      random: null,
      pictures: [],
      userTotal: 1,
      color: '' // 赞图颜色
    }
  },
  methods: {
    makeBanner() {},
    // 点赞
    thumb() {
      this.$request.put(`/job/thumb/${this.$route.params.id}`).then(res => {
        this.jobwu.thumb++
        this.color = 'color:#39a9ed;pointer-events:none;'
      })
    },
    // 转到用户帖子列表
    userjobs() {
      this.$router.push(`/userjobs/${this.$route.params.uid}`)
    }
  },
  mounted() {
    // 获取帖子数据
    this.$request.get(`/job/getOneWUA/${this.$route.params.id}`).then(res => {
      if (res.data.code === 20000) {
        this.jobwu = res.data.data
        this.pictures = res.data.data.picture.split('@@')
        this.pictures.pop()
        document.title = this.jobwu.title + '-' + document.title
      }
    })
    // 获取用户帖子总数
    this.$request.get(`/job/getTotal/${this.$route.params.uid}`).then(res1 => {
      this.userTotal = res1.data.data
    })

    // 随机获取帖子
    this.$request.get(`/job/random/${this.$route.params.id}`).then(res2 => {
      if (res2.data.code === 30001) {
        // 异常，一般是后端找不到某随机数对应id的帖子所引起，则将random置空
        this.random = null
      } else if (res2.data.code === 20000) {
        this.random = res2.data.data
      }
    })
  },
  components: { Job, Header }
}
</script>

<style lang="less" scoped>
.jobdetails {
  padding: 0px;
  .main {
    padding: 10px;
    margin-top: 10px;
    background: #fff;
    border-bottom: 1px solid #ccc;
    .head {
      display: flex;
      justify-content: left;
      position: relative;
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
    .others {
      position: relative;
      display: flex;
      font-size: 15px;
      justify-content: right;
      color: #7e7e83;
      span {
        margin-left: 10px;
      }
      .fa-thumbs-o-up {
        position: absolute;
        left: 5px;
        top: 3px;
      }
    }
    .van-divider {
      position: relative;
      .banner {
        position: absolute;
        right: 10px;
        width: 40px;
        height: 40px;
        background: #39a9ed;
        border-radius: 25px;
        z-index: 999;
        color: #fff;
        padding: 5px;
        text-align: center;
        vertical-align: middle;
        line-height: 20px;
      }
    }
  }

  h3 {
    font-weight: normal;
    margin: 0px;
  }
  .attr .van-col {
    margin: 5px auto;
    font-size: 14px;
    color: #858383;
    font-weight: 300;
    span:nth-child(2) {
      color: #1f1616;
      font-weight: 500;
    }
  }
  span {
    margin-left: 5px;
  }
  .desc {
    pre {
      font-family: PingFangSC-Regular, sans-serif;
    }
    padding: 10px;
  }
  .warning {
    background: #fff;
    padding: 15px;
    margin: 10px 0;
    span {
      font-size: 18px;
      color: #39a9ed;
    }
    p {
      color: #7e7e83;
      font-size: 12px;
      padding: 5px;
      margin: 0px;
    }
  }
  .jobs {
    margin-top: 15px;
  }
}
</style>

<template>
  <div class="job">
    <!-- 头部开始 -->
    <van-row class="head">
      <van-col span="4" style="text-align:center">
        <van-image round width="50px" height="50px" :src="avatar">
        </van-image>
      </van-col>
      <van-col span="20">
        <van-row>
          <van-col span="22">
            <div class="van-ellipsis title"><span class="topping" v-show="topping">顶</span>{{ title }}</div>
          </van-col>
          <van-button plain hairline type="info" size="small" round :to="'/JobDetails/' + uid + '/' +id">详情</van-button>
        </van-row>
        <div class="tip">
          <span>{{ publisher}}</span>
          <span>{{ view }}次浏览</span>
          <span>{{time}}</span>
          <span>
            <van-tag plain type="primary">{{cname}}</van-tag>
          </span>
        </div>
      </van-col>
    </van-row>
    <!-- 头部结束 -->

    <!-- 详细信息收起 -->
    <div class="desc" v-if="hidden">
      <div class="van-ellipsis">
        {{detail}}
      </div>
      <div class="jobattr">
        <span v-show="price!=0">单价：{{price}}</span>
        <span v-show="guarantee">支持担保</span>
        <a class="showDetail" @click="switchDetail">展开</a>
      </div>
    </div>
    <!-- 详细信息展开 -->
    <div v-else class="desc">
      <div>
        <pre>{{detail}}</pre>
      </div>
      <div class="jobattr">
        <span v-show="price!=0">单价：{{price}}</span>
        <span v-show="guarantee">支持担保</span>
        <a class="showDetail" @click="switchDetail">收起</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      hidden: true
    }
  },
  props: {
    id: Number,
    uid: Number,
    cname: String,
    topping: Boolean, // 是否置顶
    title: String,
    publisher: String,
    detail: String,
    view: Number,
    price: Number,
    avatar: String,
    guarantee: Boolean,
    date: String
  },
  computed: {
    time() {
      const add = Date.parse(this.date) // 发布时间戳
      const now = Date.now() // 当前时间戳
      const todayDate = new Date() // 今天零点时间戳
      todayDate.setHours(0)
      todayDate.setMinutes(0)
      todayDate.setSeconds(0)
      const today = todayDate.getTime()
      const tomorrow = today + 24 * 3600 * 1000 // 明天零点时间戳
      const yesterday = today - 24 * 3600 * 1000 // 昨天零点时间戳
      if (now - add < 3600000) {
        return '刚刚'
      } else if (now - add < 7200000) {
        return '两小时'
      } else if (add < today && add > yesterday) {
        return '昨天'
      } else if (add < tomorrow && add > today) {
        return '今天'
      } else if (yesterday < tomorrow && add > today - 6 * 24 * 3600 * 1000) {
        // 本周内所以取前到六天前过
        return '本周'
      } else {
        return this.date.substring(0, 10)
      }
    }
  },
  methods: {
    // 是否展开
    switchDetail() {
      this.hidden = !this.hidden
    }
  }
}
</script>

<style lang="less">
.job {
  background: #fff;
  position: relative;
  padding: 15px 10px;
  margin: 0px;
  border: 1px solid #f8f6f6;
  text-align: left;
  padding-bottom: 15px;
  .title {
    margin: 0px;
    font-weight: normal;
    font-size: 18px;
    width: 95%;
  }

  .topping {
    position: relative;
    left: 0px;
    background: #fe4d07;
    color: #f5f5f5;
    padding: 2px 5px;
    font-size: 15px;
    top: -2px;
    margin-right: 5px;
    border-radius: 20px;
  }

  .van-button {
    position: absolute;
    right: 5px;
    top: 10px;
  }
  .head {
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
  }
  .desc {
    margin: 10px 0px;
    .showDetail {
      color: #2a58b1 !important;
      float: right;
    }
  }
  .jobattr {
    padding: 5px 0px;
    color: #7e7e83;
    span:nth-child(1) {
      margin-right: 20px;
    }
  }
}
</style>

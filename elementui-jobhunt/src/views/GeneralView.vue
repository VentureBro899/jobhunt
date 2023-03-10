<template>
  <div class="general">
    <div class="first">
      <div id="statistic">
        <div class="usertotal stabox">
          <h3>用户总数</h3>
          <span class="num">{{userCount}}</span>
        </div>
        <div class="jobtotal stabox">
          <h3>帖子总数</h3>
          <span class="num">{{jobCount}}</span>
        </div>
        <div class="alive stabox">
          <h3>活跃数</h3>
          <span class="num">{{aliveCount}}</span>
        </div>
      </div>
      <div id="jobsort"></div>
    </div>
    <div class="second">
      <div id="usertrend"></div>
      <div id="jobpost"></div>

    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      jobCount: 0,
      userCount: 0,
      aliveCount: 0,

      // 用户数量变化图
      userTrendOption: {
        color: '#409eff',
        title: {
          text: '近七天注册用户数',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'line',
            smooth: true
          }
        ]
      },
      // job类型百分比图
      jobSortOption: {
        title: {
          text: '文章类型占比',
          subtext: '用户已发布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '发布类型占比',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: 'Search Engine' },
              { value: 735, name: 'Direct' },
              { value: 580, name: 'Email' },
              { value: 484, name: 'Union Ads' },
              { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      // 近七天帖子发布数量
      jobPostOption: {
        color: '#409eff',
        title: {
          text: '近七天帖子发布数',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'bar'
          }
        ]
      }
    }
  },
  mounted() {
    this.$request.get('/statistic/usertotal').then(res => {
      this.userCount = res.data.data
    })

    this.$request.get('/statistic/jobtotal').then(res => {
      this.jobCount = res.data.data
    })

    this.$request.get('/statistic/useralive').then(res => {
      this.aliveCount = res.data.data
    })
    this.$request.get('/statistic/usertrend').then(res => {
      const arr = res.data.data
      arr.forEach(item => {
        this.userTrendOption.xAxis.data.push(Object.keys(item)[0].substring(5))
        this.userTrendOption.series[0].data.push(item[Object.keys(item)])
      })
      this.userTrendOption.xAxis.data.reverse()
      this.userTrendOption.series[0].data.reverse()
      const usertrenddom = document.getElementById('usertrend')
      const usertrendChart = this.$echarts.init(usertrenddom)
      usertrendChart.setOption(this.userTrendOption)
    })

    this.$request.get('/statistic/jobsort').then(res => {
      const arr = res.data.data
      this.jobSortOption.series[0].data = arr
      const jobsortdom = document.getElementById('jobsort')
      const jobsortChart = this.$echarts.init(jobsortdom)
      jobsortChart.setOption(this.jobSortOption)
    })

    this.$request.get('/statistic/jobpost').then(res => {
      const arr = res.data.data
      arr.forEach(item => {
        this.jobPostOption.xAxis.data.push(Object.keys(item)[0].substring(5))
        this.jobPostOption.series[0].data.push(item[Object.keys(item)])
      })
      this.jobPostOption.xAxis.data.reverse()
      this.jobPostOption.series[0].data.reverse()
      const jobpostdom = document.getElementById('jobpost')
      const jobpostChart = this.$echarts.init(jobpostdom)
      jobpostChart.setOption(this.jobPostOption)
    })
  }
}
</script>

<style lang="less">
.general {
  min-width: 1200px;
}
#jobsort {
  width: 35%;
  height: 350px;
}
#jobpost {
  width: 50%;
  height: 350px;
}

#usertrend {
  width: 50%;
  height: 350px;
}
.first {
  display: flex;
  justify-content: flex-start;
}

.second {
  display: flex;
  justify-content: flex-start;
}
#statistic {
  width: 700px;
  padding: 0px 00px;
  display: flex;
  flex-wrap: wrap;
  .stabox {
    margin-left: 15px;
    width: 250px;
    height: 150px;
    color: #fff;
    padding: 0 20px;
    .num {
      font-size: 35px;
    }
  }
  .usertotal {
    background: #1dda44;
  }
  .jobtotal {
    background: #a400ff;
  }
  .alive {
    background: #ff00bc;
  }
}
</style>

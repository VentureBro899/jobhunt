<template>
  <div>
    <Header title="我发布的"></Header>
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
      <van-cell-group>
        <van-cell v-for="job in jobList" :key="job.id" :title="job.title" is-link :to="'jobupd/' + job.id" :label="'发布于' + job.date != null ? job.date : ''" />
      </van-cell-group>
    </van-list>
  </div>
</template>

<script>
export default {
  data() {
    return {
      jobList: [],
      current: 0,
      size: 20,
      loading: false,
      finished: false
    }
  },
  methods: {
    onLoad() {
      this.loading = true
      this.current++
      this.getList()
    },
    getList() {
      this.$request(`/job/${this.current}/${this.size}?user=true`).then(res => {
        if (res.data.code === 20000) {
          this.loading = false
          if (res.data.data.length === 0) {
            this.finished = true
            return
          }
          this.jobList = [...this.jobList, ...res.data.data]
        }
      })
    }
  }
}
</script>

<style>
</style>

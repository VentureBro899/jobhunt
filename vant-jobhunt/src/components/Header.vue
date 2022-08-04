<template>
  <div>
    <van-nav-bar fixed :title="title" left-text="返回" left-arrow @click-left="onClickLeft" />
    <div class="block"></div>
  </div>

</template>

<script>
export default {
  props: {
    title: {
      default: ''
    }
  },
  methods: {
    onClickLeft() {
      this.$router.back()
    }
  }
}
</script>

<style>
.block {
  height: 46px;
  margin-bottom: 10px;
}
</style>

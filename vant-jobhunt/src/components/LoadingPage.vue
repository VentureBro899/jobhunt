<template>
  <van-skeleton title avatar :row="3" :loading="!initialized" />
</template>

<script>
export default {
  props: {
    initialized: {
      type: Boolean,
      default: true,
      require: true
    }
  }
}
</script>

<style>
</style>

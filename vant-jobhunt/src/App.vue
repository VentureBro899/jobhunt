<template>
  <div id="app">
    <router-view :key="$route.fullPath" />
    <van-tabbar v-model="active" :border="true" route>
      <van-tabbar-item icon="home-o" to="/">首页</van-tabbar-item>
      <van-tabbar-item icon="apps-o" to="/sort">分类</van-tabbar-item>
      <van-tabbar-item class="fa fa-plus-square fa-4x" to="/mine/jobadd"></van-tabbar-item>
      <van-tabbar-item icon="envelop-o" to="/message">站内信</van-tabbar-item>
      <van-tabbar-item icon="friends-o" to="/user">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      key: '',
      active: 0
    }
  },
  created() {}
}
</script>

<style lang="less">
#app {
  font-family: "Tahoma,Helvetica,Arial,'宋体',sans-serif";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  padding-bottom: 60px;
  a {
    color: #000406;
  }
}

pre {
  font-family: "Tahoma,Helvetica,Arial,'宋体',sans-serif";
  margin: 0;
  white-space: pre-wrap; /*css-3*/
  white-space: -moz-pre-wrap; /*Mozilla,since1999*/
  white-space: -pre-wrap; /*Opera4-6*/
  white-space: -o-pre-wrap; /*Opera7*/
  word-wrap: break-word; /*InternetExplorer5.5+*/
}

.van-tabbar-item:nth-child(3) {
  position: absolute;
  width: 45px;
  height: 45px;
  top: -10px;
  left: 50%;
  transform: translate(-50%, 0);
  color: #39a9ed;
  z-index: 99;
  background: #fff;
  text-align: center;
  border: 5px solid #dec1314d;
  border-radius: 30px;
  overflow: hidden;
  display: flex;
}

.van-tabbar-item:nth-child(2) {
  left: -20px;
  position: relative;
}
.van-tabbar-item:nth-child(4) {
  left: 20px;
  position: relative;
}
</style>

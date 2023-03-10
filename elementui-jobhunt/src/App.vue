<template>
  <div id="app">
    <el-container>
      <el-header height="80px">
        <div class="pageheader">Venture 后台管理系统</div>
        <div class="admin" @click="logout()">
          <el-avatar :src="avatar" title="点击登出"></el-avatar>
        </div>
      </el-header>
      <el-container>
        <el-aside width="250px">

          <el-menu default-active="2" class="el-menu-vertical-demo" router background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="4" route="/general">
              <i class="el-icon-setting"></i>
              <span slot="title">网站总览</span>
            </el-menu-item>
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-user"></i>
                <span>用户管理</span>
              </template>
              <el-menu-item index="1-1" route="/userlist">
                <span slot="title">用户列表</span>
              </el-menu-item>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-box"></i>
                <span>帖子管理</span>
              </template>
              <el-menu-item index="2-2" route="/joblist">
                <span slot="title">帖子列表</span>
              </el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title">
                <i class="el-icon-microphone"></i>
                <span>PPT管理</span>
              </template>
              <el-menu-item index="3-1" route="/pptadd">
                <span slot="title">添加PPT</span>
              </el-menu-item>
              <el-menu-item index="3-2" route="/pptlist">
                <span slot="title">PPT列表</span>
              </el-menu-item>
            </el-submenu>
            <el-submenu index="4">
              <template slot="title">
                <i class="el-icon-user"></i>
                <span>公告管理</span>
              </template>
              <el-menu-item index="4-1" route="/notice">
                <span slot="title">修改公告</span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <div class="rightview">
            <div class="viewpage">
              <router-view></router-view>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      avatar: localStorage.getItem('avatar')
    }
  },
  methods: {
    logout() {
      this.$confirm('是否退出登录', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$request.post('/user/logout').then(() => {
            this.$message({
              type: 'success',
              message: '已退出!'
            })
            localStorage.clear()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          })
        })
    }
  }
}
</script>
<style lang="less">
.viewpage {
  padding: 20px;
  background: #fff;
}
.el-header {
  background: #545c64;
  line-height: 80px;
  padding: 5px 30px;
  text-align: left;
  color: #409eff;
  font-size: 30px;
  margin: 0px;
}

.el-aside {
  min-height: 800px;
  max-height: 2000px;
  background: #545c64;
}
.pageheader {
  float: left;
  cursor: default;
}
.admin {
  padding: 8px;
  line-height: 80px;
  float: right;
  cursor: pointer;
}
</style>

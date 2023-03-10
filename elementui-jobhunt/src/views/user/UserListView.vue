<template>
  <div class="userList">
    <div class="search">
      <el-input placeholder="请输入关键字搜索" v-model="key" clearable> </el-input>
      <el-button @click="search()">搜索</el-button>
      <el-button @click="clear()">清空搜索关键字</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="id" width="100"> </el-table-column>
      <el-table-column prop="username" label="昵称" width="150"> </el-table-column>
      <el-table-column prop="phone" label="手机"> </el-table-column>
      <el-table-column prop="email" label="邮箱"> </el-table-column>
      <el-table-column prop="balance" label="余额"> </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="primary" @click="handleDelete(scope.$index, scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="block">
      <el-button @click="batchdel()">删除选中</el-button>
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="current" :page-sizes="[1, 5, 10, 20]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="total"> </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      current: 1,
      size: 10,
      total: 1,
      tableData: [],
      multipleSelection: [],
      key: ''
    }
  },

  methods: {
    // 全选或全不选
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    // 单选改变
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 修改信息，跳转页面
    handleEdit(index, coldata) {
      this.$router.push(`/userUpdate/${coldata.id}`)
    },
    // 删除用户
    handleDelete(index, id) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          const { data } = await this.$request.delete('/master/user/' + id)
          this.current -= 1
          this.selectall()
          if (data.code === 20000) {
            if (typeof id === 'object') {
              const pageAfterDel = Math.ceil((this.total - id.length) / this.size) // 删除后总页数
              this.current = pageAfterDel < this.current ? pageAfterDel : this.current // 删除后总页数小于当前页数则将当前页置为总的页数
              console.log(pageAfterDel)
            } else {
              const pageAfterDelForInt = Math.ceil((this.total - 1) / this.size) // 删除后总页数
              this.current = pageAfterDelForInt < this.current ? pageAfterDelForInt : this.current
              console.log(pageAfterDelForInt)
            }
            this.selectall()
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 获取用户信息
    selectall(key) {
      this.$request.get(`/master/user/${this.current}/${this.size}/${key === undefined ? '' : key}`).then(res => {
        this.tableData = res.data.data.records
        this.total = res.data.data.total
      })
    },
    // 每页条数改变时触发 选择一页显示多少行
    handleSizeChange(val) {
      this.current = 1
      this.size = val
      this.selectall(this.key)
    },
    // 当前页改变时触发 跳转其他页
    handleCurrentChange(val) {
      this.current = val
      this.selectall(this.key)
    },
    // 搜索则页码要为1
    search() {
      this.current = 1
      this.selectall(this.key)
    },
    // 清空搜索关键字
    clear() {
      this.key = ''
      this.current = 1
      this.selectall()
    },
    // 批量删除
    batchdel() {
      const ids = []
      this.multipleSelection.forEach(item => {
        ids.push(item.id)
      })
      this.handleDelete(1, ids)
    }
  },
  mounted() {
    this.selectall()
  }
}
</script>

<style lang="less" scoped>
.userList {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  .block {
    margin-top: 20px;
    margin-top: 20px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
  .search {
    display: flex;
    button {
      margin-left: 5px;
    }
  }
}
</style>

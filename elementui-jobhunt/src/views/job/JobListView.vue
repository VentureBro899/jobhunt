<template>
  <div class="jobList">
    <div class="search">
      <el-select v-model="cname" placeholder="未指定分类" clearable label="选择分类">
        <el-option v-for="item in options" :key="item.value" :value="item.value">
        </el-option>
      </el-select>
      <el-checkbox v-model="topping" label="置顶" border></el-checkbox>
      <el-checkbox v-model="keeping" label="上架" border></el-checkbox>
      <el-input placeholder="标题" v-model="title" clearable> </el-input>
      <el-input placeholder="发布者" v-model="username" clearable> </el-input>
      <el-button @click="search()">确认筛选条件</el-button>
      <el-button @click="clear()">恢复默认</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="id" width="100"> </el-table-column>
      <el-table-column prop="title" label="帖子标题" width="150"> </el-table-column>
      <el-table-column prop="cname" label="分类"> </el-table-column>
      <el-table-column prop="username" label="发布者"> </el-table-column>
      <el-table-column label="状态"> <template slot-scope="scope">{{scope.row.keeping ? '正常': '下架'}}</template></el-table-column>
      <el-table-column label="置顶"> <template slot-scope="scope">{{scope.row.topping ? '是': '否'}}</template></el-table-column>
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
      // 筛选条件
      current: 1,
      size: 10,
      title: '',
      username: '',
      cname: '',
      topping: false,
      keeping: true,
      pages: 1,

      total: 1,
      tableData: [],
      multipleSelection: [],
      options: [{ value: '微信单' }, { value: 'QQ单' }, { value: '需求发布' }, { value: '骗子曝光' }]
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
      this.$router.push(`/jobUpdate/${coldata.id}`)
    },
    // 删除帖子
    handleDelete(index, id) {
      this.$confirm('此操作将永久删除帖子, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          const { data } = await this.$request.delete('/master/job/' + id)
          if (data.code === 20000) {
            // 删除最后一页最后一条则跳转到前一页
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
    // 批量删除
    batchdel() {
      const ids = []
      this.multipleSelection.forEach(item => {
        ids.push(item.id)
      })
      this.handleDelete(1, ids)
    },
    // 获取job列表
    selectall(key) {
      this.$request
        .get(
          `/master/job/${this.current}/${this.size}?keeping=${this.keeping}&topping=${this.topping}
      &title=${this.title}&cname=${this.cname}&username=${this.username}`
        )
        .then(res => {
          this.tableData = res.data.data.records
          this.total = res.data.data.total
          this.pages = res.data.data.pages
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
      this.title = ''
      this.username = ''
      this.cname = ''
      this.topping = false
      this.keeping = true
      this.current = 1
      this.selectall()
    }
  },
  mounted() {
    this.selectall()
  }
}
</script>

<style lang="less" scoped>
.jobList {
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
    .el-input--suffix .el-input__inner {
      width: 130px;
    }
    display: flex;
    button {
      margin-left: 5px;
    }
  }
}
</style>

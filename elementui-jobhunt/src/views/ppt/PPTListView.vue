<template>
  <div class="pptList">

    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="id" width="100"> </el-table-column>
      <el-table-column prop="description" label="描述" width="250"> </el-table-column>
      <el-table-column prop="showing" label="展示">
        <template slot-scope="scope">{{scope.row.showing ? '展示': '隐藏'}}</template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="primary" @click="handleDelete(scope.$index, scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="block">
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
      key: '',
      isshowing: '展示'
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
      this.$router.push(`/pptUpdate/${coldata.id}`)
    },
    // 删除ppt
    handleDelete(index, id) {
      this.$confirm('此操作将永久删除该幻灯片, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          const { data } = await this.$request.delete('/master/ppt/' + id)
          this.current -= 1
          this.selectall()
          if (data.code === 20000) {
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
    // 获取ppt信息
    selectall() {
      this.$request.get(`/master/ppt/${this.current}/${this.size}`).then(res => {
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
      this.selectall()
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
  align-items: flex-end;
  .block {
    margin-top: 20px;
  }
  .search {
    display: flex;
    button {
      margin-left: 5px;
    }
  }
}
</style>

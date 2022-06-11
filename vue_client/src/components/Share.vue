/* eslint-disable vue/no-unused-vars */
<template>
  <div style="wight:100%">
    <h3>分享</h3>
    <div id="file-table"
         style="margin:50px auto;">
      <el-table :data="pageInfo.list"
                style="wigth:100%"
                tooltip-effect="dark">
        <el-table-column label="结束时间"
                         sortable
                         prop='endTime'>
        </el-table-column>
        <el-table-column label="提取码"
                         prop="extractionCode">

        </el-table-column>
        <el-table-column label="分享连接"
                         prop="sharePath">

        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-popconfirm title="确认取消吗？"
                           icon="el-icon-info"
                           icon-color="red"
                           @confirm="deleteShare(scope.row.id)">
              <el-button size="mini"
                         type='danger'
                         slot="reference">取消分享</el-button>
            </el-popconfirm>

          </template>

        </el-table-column>
      </el-table>
    </div>
    <div class="pages">
      <el-pagination background
                     layout="prev, pager, next"
                     :page-count="pageInfo.pages"
                     @current-change="currentChange"
                     @prev-click="prevClick"
                     @next-click="nextclick">
      </el-pagination>
    </div>
  </div>
</template>
 
<script>
export default {
  name: 'Share',
  components: {},
  data() {
    return {
      pageInfo: {
        pages: 0,
        list: null,
        prePage: 0,
        nextPage: 0,
      },
    
    }
  },

  created: function () {
    this.$axios
      .get('/file/share/group/info/' + localStorage.getItem('userid') + '/1')
      .then((res) => {
        var result = res.data
        this.pageInfo = result.model
        console.log(this.pageInfo.list)
      })
  },
  methods: {
    currentChange(val) {
      this.$axios
        .get(
          '/file/share/group/info/' + localStorage.getItem('userid') + '/' + val
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    prevClick() {
      this.$axios
        .get(
          '/file/share/group/info/' +
            localStorage.getItem('userid') +
            '/' +
            this.pageInfo.prePage
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    nextclick() {
      this.$axios
        .get(
          '/file/share/group/info/' +
            localStorage.getItem('userid') +
            '/' +
            this.pageInfo.nextPage
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    deleteShare(id) {
      console.log(id)
      this.$axios.delete('/file/share/group/' + id).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
        } else {
          this.$message.error(result.message)
        }
      })
    },
  },
  watch: {},
}
</script>

<style lang="scss" scoped>
.pages {
  text-align: center;
}
.bottomMenu {
  width: 100%;
  text-align: center;
  margin-top: 30px;
}
.addBtn {
  border: none;
  margin: 0px;

  width: 150px;
}
.moreBtn {
  border: none;
  margin: 0px;
  width: 150px;
}
.fastBtn {
  border: none;
  margin: 5px;
  width: auto;
}
</style>
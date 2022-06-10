<template>
  <div>
    <div>
      <el-button icon="el-icon-plus"
                 type="primary"
                 circle
                 style="float:right"
                 @click="dialogVisible = true"
                 ></el-button>
      <el-dialog title="添加反馈"
                 :visible.sync="dialogVisible">
        <el-form :model="newFeedBack"
                 label-width="80px"
                 :rules="ru1">
          <el-form-item label='标题'
                        prop="title">
            <el-input v-model="newFeedBack.title"></el-input>
          </el-form-item>
          <el-form-item label="描述"
                        prop="desc">
            <el-input type="textarea"
                      v-model="newFeedBack.desc"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="addFeedback">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <div style="margin: 50px auto;">
      <el-table :data="pageInfo.list"
                tooltip-effect="dark"
                style="width: 100%"
                stripe>
        <el-table-column label="反馈日期"
                         width="120">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.sendDate }}</span></template>
        </el-table-column>

        <el-table-column label="反馈标题"
                         width="120"
                         prop="title">

        </el-table-column>
        <el-table-column prop="solution"
                         label="是否处理"
                         width="120">
          <template slot-scope="scope">

            <el-tag type="success"
                    v-if="scope.row.isSolve">已处理</el-tag>
            <el-tag type="danger"
                    v-else>未处理</el-tag>

          </template>
        </el-table-column>
        <el-table-column prop="solveDate"
                         label="处理日期"
                         show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini"
                       type="info"
                       @click="open(scope.row.solution)"
                       :disabled=!scope.row.isSolve>处理结果</el-button>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="deleteFeedback(scope.row.id)">
              <el-button size="mini"
                         type="danger"
                         :disabled=!scope.row.isSolve
                          slot="reference">删除</el-button>
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
  name: 'UserService',

  data() {
    return {
      pageInfo: {
        pages: 0,
        list: null,
        prePage: 0,
        nextPage: 0,
      },
      newFeedBack: {
        title: '',
        desc: '',
      },
      dialogVisible: false,
      ru1: {
        title: [
          {
            required: true,
            message: '请输入标题',
            trigger: 'blur',
          },
        ],
        desc: [
          {
            required: true,
            message: '请输入详细情况',
            trigger: 'blur',
          },
        ],
      },
    }
  },

  mounted() {},
  created: function () {
    this.$axios
      .get('/user/feedback/result/' + localStorage.getItem('userid') + '/1')
      .then((res) => {
        var result = res.data
        this.pageInfo = result.model
      })
  },
  methods: {
    currentChange(val) {
      this.$axios
        .get(
          '/user/feedback/result/' + localStorage.getItem('userid') + '/' + val
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    prevClick() {
      this.$axios
        .get(
          '/user/feedback/result/' +
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
          '/user/feedback/result/' +
            localStorage.getItem('userid') +
            '/' +
            this.pageInfo.nextPage
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    addFeedback() {
      if (this.newFeedBack.title == '' || this.newFeedBack.desc == '') {
        this.$message.error('标题或内容不能为空！！！！')
        setTimeout(() => {
          this.dialogVisible = false
          this.newFeedBack.title=''
          this.newFeedBack.desc=''
        }, 3 * 1000)
        return
      }
      this.$axios
        .post('/user/feedback', {
          userId: localStorage.getItem('userid'),
          title: this.newFeedBack.title,
          body: this.newFeedBack.desc,
        })
        .then((res) => {
          var result = res.data
          if (result.code == 200) {
            this.dialogVisible = false
            this.$message.success(result.message)
           this.$router.go(0)
            
          } else {
            this.dialogVisible = false
            this.$message.console.error(result.message)
          }
        })
    },
    deleteFeedback(id) {
      this.$axios.delete('/user/feedback/delete/' + id).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
        } else {
          this.$message.error(result.message)
        }
      })
    },
    open(body) {
      this.$alert(body, '处理结果', {
        confirmButtonText: '确定',
      })
    },
  },
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
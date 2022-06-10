<template>
  <div>
    <div style="margin: 50px auto;">
      <el-table :data="pageInfo.list"
                tooltip-effect="dark"
                style="width: 100%">
        <el-table-column type='expand'>
          <template slot-scope="scope">
            <el-form label-position="top">
              <el-form-item label='问题详情：'>
                <span>
                  {{scope.row.body}}
                </span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="反馈日期"
                         width="120">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.sendDate }}</span></template>
        </el-table-column>
        <el-table-column label="UID"
                         width="120"
                         prop="userId">

        </el-table-column>
        <el-table-column label="反馈标题"
                         prop="title">

        </el-table-column>
        
        <el-table-column label="操作"
                         width="120">
          <template slot-scope="scope">
            <el-dialog title="解决反馈"
                       :visible.sync="dialogVisible"
                       width="30%"
                       :modal='false'
                      >
              <el-form :model='feedback'>
                <el-form-item label='解决方案'>
                <el-input type='text' v-model="feedback.solution"></el-input>
                </el-form-item>
              </el-form>
              <span slot="footer"
                    class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary"
                           @click="updateFeedback(scope.row)">确 定</el-button>
              </span>
            </el-dialog>
            <el-button size="mini"
                       type="info"
                       @click="dialogVisible = true">编辑</el-button>

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
  name: 'Feedback',

  data() {
    return {
      pageInfo: {
        pages: 0,
        list: null,
        prePage: 0,
        nextPage: 0,
      },
      dialogVisible: false,
      feedback:{
        id:'',
        solution:''
      }
    }
  },

  mounted() {},
  created: function () {
    this.$axios.get('/admin/feedback/' + 1).then((res) => {
      var result = res.data
      this.pageInfo = result.model
    })
  },
  methods: {
    currentChange(val) {
      this.$axios.get('/admin/feedback/' + val).then((res) => {
        var result = res.data
        this.pageInfo = result.model
      })
    },
    prevClick() {
      this.$axios
        .get('/admin/feedback/' + this.pageInfo.prePage)
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    nextclick() {
      this.$axios
        .get('/admin/feedback/' + this.pageInfo.nextPage)
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    updateFeedback(row) {
      this.feedback.id=row.id
      if( this.feedback.solution==''){
        return;
      }
      console.log(row)
      this.$axios.put('admin/feedback/solve',this.feedback)
      .then(res=>{
          var result=res.data;
          if(result.code==200){
            this.$message.success(result.message)
            this.dialogVisible=false
            this.$router.go(0) 

          }else{
             this.$message.error(result.message)
          }

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
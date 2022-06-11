<template>
  <div>
    <div>
      <el-button icon="el-icon-plus"
                 type="primary"
                 circle
                 style="float:right"
                 @click="openAdd"></el-button>
      <el-dialog title="添加新套餐"
                 :visible.sync="AddCombodialogvisible"
                 width="30%">
        <el-form :model='newCombo'>
          <el-form-item label='套餐名'>
            <el-input v-model="newCombo.name"></el-input>
          </el-form-item>
          <el-form-item label='有效时间/月'>
            <el-input-number v-model="newCombo.time"></el-input-number>
          </el-form-item>
          <el-form-item label='容量/G'>
            <el-input-number v-model="newCombo.capacity"></el-input-number>
          </el-form-item>
          <el-form-item label="价格/元">
            <el-input-number v-model="newCombo.amount"
                             :step="5"></el-input-number>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="AddCombodialogvisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="addCombo">确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <div>
      <el-table :data="pageInfo.list" stripe>
        <el-table-column label="套餐名"
                         prop="name">

        </el-table-column>
        <el-table-column label="容量"
                         prop="capacity">

        </el-table-column>
        <el-table-column label="时间">
          <template slot-scope="scope">
            {{scope.row.time}}月
          </template>
        </el-table-column>
        <el-table-column label="价格">
          <template slot-scope="scope">
            {{scope.row.amount}}元
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-dialog title="编辑套餐"
                       :visible.sync="editCombodialogvisible"
                       width="30%">
              <el-form :model='newCombo'>
                <el-form-item label='套餐名'>
                  <el-input v-model="newCombo.name"></el-input>
                </el-form-item>
                <el-form-item label='有效时间/月'>
                  <el-input-number v-model="newCombo.time"></el-input-number>
                </el-form-item>
                <el-form-item label='容量/G'>
                  <el-input-number v-model="newCombo.capacity"></el-input-number>
                </el-form-item>
                <el-form-item label="价格/元">
                  <el-input-number v-model="newCombo.amount"
                                   :step="5"></el-input-number>
                </el-form-item>
              </el-form>
              <span slot="footer"
                    class="dialog-footer">
                <el-button @click="editCombodialogvisible = false">取 消</el-button>
                <el-button type="primary"
                           @click="updateCombo">确 定</el-button>
              </span>
            </el-dialog>
            <el-button @click="openUpdateDialog(scope.row)" type='primary'>编辑</el-button>
            <el-popconfirm title="确认移除吗？"
                           icon="el-icon-info"
                           icon-color="red"
                           @confirm="deleteCombo(scope.row.id)">
              <el-button class="moreBtn"
                         slot="reference"
                         type="danger">移除</el-button>
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
  name: 'Combo',

  data() {
    return {
      AddCombodialogvisible: false,
      editCombodialogvisible:false,
      pageInfo: {
        pages: 0,
        list: null,
        prePage: 0,
        nextPage: 0,
      },
      newCombo: {
        id:0,
        name: '',
        capacity: 0,
        amount: 0,
        time: 0,
      },
    }
  },

  created: function () {
    this.$axios.get('/admin/combo/all/' + 1).then((res) => {
      var result = res.data
      this.pageInfo = result.model
    })
  },
  methods: {
    deleteCombo(id){
      console.log("id:"+id)
      this.$axios.delete('/admin/combo/delete/'+id).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
          this.editCombodialogvisible= false
        } else {
          this.$message.error(result.message)
        }
      })
    },
    updateCombo(){
       this.$axios.put('/admin/combo/update', this.newCombo).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
          this.editCombodialogvisible= false
        } else {
          this.$message.error(result.message)
        }
      })
    },
    openUpdateDialog(row){
      this.newCombo.id=row.id
      this.newCombo.name=row.name
      // var str=row.capacity.spli('')[0]
      this.newCombo.capacity=Number(row.capacity.replace(/[^0-9]/g, '')) 
      this.newCombo.amount=row.amount
      this.newCombo.time=row.time
      this.editCombodialogvisible=true

    },
    addCombo() {
      this.newCombo.id=null
      this.$axios.post('/admin/combo/add', this.newCombo).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
          this.AddCombodialogvisible = false
        } else {
          this.$message.error(result.message)
        }
      })
    },
    currentChange(val) {
      console.log(val)
      this.$axios.get('/admin/combo/all/' + val).then((res) => {
        var result = res.data
        this.pageInfo = result.model
      })
    },
    prevClick() {
      this.$axios
        .get('/admin/combo/all/' + this.pageInfo.prePage)
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    nextclick() {
      this.$axios
        .get('/admin/combo/all/' + this.pageInfo.nextPage)
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
     openAdd(){
       this.newCombo.name='新套餐'
       this.newCombo.capacity=0
       this.newCombo.amount=0
       this.newCombo.time=0
      this.AddCombodialogvisible=true
     }
  },
}
</script>

<style lang="scss" scoped>
.pages {
  text-align: center;
}
</style>
<template>
  <div>
     <div>
      <el-page-header @back="goBack"
                      content="搜索结果"
                      title="返回"
                     >
      </el-page-header>
    </div>
    <div id="file-table"
         style="margin:50px auto;">
      <el-table :data="userData"
                style="wigth:100%"
                tooltip-effect="dark"
                stripe
              >
        <el-table-column label="UID"
                         sortable
                         prop='id'>
        </el-table-column>
        <el-table-column label="昵称"
                         prop="name"
                         sortable>

        </el-table-column>
        <el-table-column label="注册时间"
                         prop="regDate"
                         sortable>

        </el-table-column>
        <el-table-column label="更多">
          <template slot-scope="scope">
            <el-popover placement="right"
                        trigger="click">
              <el-menu style="border-right: 0;">
                <el-menu-item index="1">
                  <el-button type="text"
                             @click="dialogDepotVisible = true">查看仓库信息</el-button>

                  <el-dialog title="文件仓库"
                             :visible.sync="dialogDepotVisible"
                             :modal=false>
                    <el-descriptions title="仓库信息">
                      <template slot="extra">
                        <el-dialog title="更改购买容量"
                                   :visible.sync="dialogDepotUpdateVisible"
                                   :modal=false
                                   width='30%'>

                          <el-form :model='depot' >
                            <el-form-item label='UID：'>
                              {{depot.id}}
                            </el-form-item>
                            <el-form-item label='总容量：'>
                              {{depot.maxCapacity}}GB
                            </el-form-item>
                            <el-form-item label='购买容量 /G'>
                              <el-input-number v-model="depot.buyCapacity"></el-input-number>
                            </el-form-item>
                            <el-form-item>

                            </el-form-item>
                          </el-form>
                          <span slot="footer"
                                class="dialog-footer">
                            <el-button @click="dialogDepotUpdateVisible = false">取 消</el-button>
                            <el-button type="primary"
                                       @click="updateDepot">确 定</el-button>
                          </span>
                        </el-dialog>
                        <el-button type="primary"
                                   size="small"
                                   @click="openUpdate(scope.row)">修改</el-button>
                      </template>
                      <el-descriptions-item label="UID">{{scope.row.id}}</el-descriptions-item>
                      <el-descriptions-item label="用户名">{{scope.row.name}}</el-descriptions-item>
                      <el-descriptions-item label="总容量">{{scope.row.maxSize}}</el-descriptions-item>
                      <el-descriptions-item label="已用容量">{{scope.row.usedSize}}</el-descriptions-item>
                      <el-descriptions-item label="购买容量">{{scope.row.buySize}}</el-descriptions-item>
                      
                    </el-descriptions>
                  </el-dialog>
                </el-menu-item>
                <el-menu-item index="2">
                  <el-button type="text"
                             @click="getUserConsume(scope.row.id)">用户消费信息</el-button>

                  <el-dialog title="消费记录"
                             :visible.sync="dialogConsumeVisible"
                             :modal=false>
                    <el-table :data="consumeData"
                     tooltip-effect="dark"
                      style="wigth:auto"
                     >
                      <el-table-column prop="subject"
                                       label="套餐名"
                                       width="150"></el-table-column>
                      <el-table-column prop="totalAmount"
                                       label="花费"
                                       width="150"></el-table-column>
                      <el-table-column prop="buyTime"
                                       label="购买时间"
                                       width="200"></el-table-column>
                    </el-table>
                  </el-dialog>
                </el-menu-item>

              </el-menu>

              <el-button icon="el-icon-more"
                         slot="reference"
                         plain></el-button>
            </el-popover>
          </template>

        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserSeacher',

  data() {
    return {
     userData:null,
      consumeData:[],
      userId:"",
        depot: {
        id: '',
        usedCapacity: 0,
        maxCapacity: 0,
        buyCapacity: 0,
      },
      dialogDepotVisible:false,
       dialogDepotUpdateVisible: false,
      dialogConsumeVisible:false,
    }
  },

 created:function(){
    this.userId = this.$route.query.userId
    this.$axios
        .get('/admin/user/'+this.userId)
        .then((res) => {
          var result = res.data
         this.userData=result.model
        })
 },

  methods: {
    searchUser(){
      this.$axios.get("admin/user/"+this.searchInfo).then((result) => {
        var res=result.data
        if(res.code==200){
          this.pageInfo.list = result.model
        }

      }).catch((err) => {
        console.log(err)
      });
    },
    getUserConsume(id){
      console.log(id)
      this.$axios
        .get(
          '/admin/user/consume/' +
            id
        )
        .then((res) => {
          var result = res.data
          this.consumeData = result.model
        })
      this.dialogConsumeVisible=true;
    },
     openUpdate(row) {
      this.depot.id=row.id
      this.depot.maxCapacity = Number(row.maxSize.replace(/[^0-9]/g, ''))
      this.depot.buyCapacity = Number(row.buySize.replace(/[^0-9]/g, ''))
      this.depot.usedCapacity= Number(row.usedSize.replace(/[^0-9]/g, ''))
      this.dialogDepotUpdateVisible = true
    },
    updateDepot() {
      this.$axios.put("/admin/update/depot",this.depot).then((result) => {
        var res=result.data
        if(res.code==200){
          this.$message.success(res.message)
          this.$router.go(0)
        }else{
           this.$message.error(res.message)
        }
      }).catch((err) => {
        console.log(err)
      });
    },
    goBack() {
      this.$router.back()
    },
   
  },
}
</script>

<style lang="scss" scoped>
.pages {
  text-align: center;
}
.moreBtn {
  border: none;
  margin: 0px;
  width: 150px;
}
</style>
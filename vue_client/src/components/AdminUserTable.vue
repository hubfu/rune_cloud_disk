<template>
  <div>
    <div style="float:right;margin-right:30px">
      <el-popover placement="bottom"
                  trigger="click">

        <el-input placeholder="请输入用户id"
                  v-model="searchInfo">
          <el-button slot="append"
                     icon="el-icon-search"
                     @click="searchUser"></el-button>
        </el-input>

        <el-button type="primary"
                   icon="el-icon-search"
                   slot="reference"
                   circle></el-button>
      </el-popover>
    </div>
    <div id="file-table"
         style="margin:50px auto;">
      <el-table :data="pageInfo.list"
                style="wigth:100%"
                tooltip-effect="dark"
                @select="Check"
                @select-all="CheckAll"
                stripe>
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

                  <el-dialog 
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

                  <el-dialog 
                             :visible.sync="dialogConsumeVisible"
                             :modal=false>
                    <el-table :data="consumeData"
                              tooltip-effect="dark"
                              style="wigth:auto">
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
  name: 'Adminusertable',

  data() {
    return {
      pageInfo: {
        pages: 0,
        list: null,
        prePage: 0,
        nextPage: 0,
      },
      depot: {
        id: '',
        usedCapacity: 0,
        maxCapacity: 0,
        buyCapacity: 0,
      },
      consumeData: [],
      searchInfo: '',
      dialogDepotVisible: false,
      dialogDepotUpdateVisible: false,
      dialogConsumeVisible: false,
    }
  },

  created: function () {
    this.$axios.get('/admin/user/info/' + 1).then((res) => {
      var result = res.data
      this.pageInfo = result.model
    })
  },

  methods: {
    Check() {},
    CheckAll() {},
    searchUser() {
      this.$router.push({
        path: '/adminhome/userseacher',
        query: { userId: this.searchInfo },
      })
    },
    currentChange(val) {
      console.log(val)
      this.$axios.get('/admin/user/info/' + val).then((res) => {
        var result = res.data
        this.pageInfo = result.model
      })
    },
    prevClick() {
      this.$axios
        .get('/admin/user/info/' + this.pageInfo.prePage)
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    nextclick() {
      this.$axios
        .get('/admin/user/info/' + this.pageInfo.nextPage)
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    getUserConsume(id) {
      console.log(id)
      this.$axios.get('/admin/user/consume/' + id).then((res) => {
        var result = res.data
        this.consumeData = result.model
      })
      this.dialogConsumeVisible = true
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
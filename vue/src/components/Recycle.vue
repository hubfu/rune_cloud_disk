<template>
  <div style="wight:100%">
    <div>
      <h4>回收站</h4>
    </div>

    <div id="file-table"
         style="margin:50px auto;">
      <el-table :data="pageInfo.list"
                style="wigth:100%"
                tooltip-effect="dark"
                @select="Check"
                @select-all="CheckAll"
                @cell-dblclick="getFolderList"
                stripe>
        <el-table-column type="selection"
                         width="55">
        </el-table-column>
        <el-table-column label="名称"
                         sortable>
          <template slot-scope="scope">
            <i :class="tableClass[scope.row.type-1]"></i>
            <span style="margin-left: 10px">{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="修改日期"
                         prop="modifiedDate"
                         sortable>

        </el-table-column>
        <el-table-column label="大小"
                         prop="size"
                         sortable>

        </el-table-column>
        <el-table-column label="更多">
          <template slot-scope="scope">
            <el-popover placement="right"
                        trigger="click">
              <el-menu style="border-right: 0;">
                <el-menu-item index="1">
                  <el-button class="moreBtn"
                             @click="removeRecycle(scope.row.id)">恢复</el-button>
                </el-menu-item>
                <el-menu-item index="2">
                  <el-popconfirm title="确认删除吗？"
                                 icon="el-icon-info"
                                 icon-color="red"
                                 @confirm="deleteFile(scope.row.id)">
                    <el-button class="moreBtn"
                               slot="reference"
                               type="danger">彻底删除</el-button>
                  </el-popconfirm>

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
    <div class="bottomMenu"
         v-show="isOpen">

      <div style="dispaly: inline-block;width: auto;">
        <el-tooltip content="删除"
                    placement="bottom"
                    effect="light">
             <el-button icon="el-icon-delete"
                   class="fastBtn"
                   type='danger'
                   circle
                   @click="batchDelete"></el-button>
        </el-tooltip>
        
      </div>
    </div>
  </div>
</template>
 
<script>
export default {
  name: 'Recycle',

  data() {
    return {
      pageInfo: {
        pages: 0,
        list: null,
        prePage: 0,
        nextPage: 0,
      },
      searchInfo: '',
      tableClass: ['el-icon-folder', 'el-icon-document'],
      isShow: false,
      isOpen: false,
      multipleSelection: '',
    }
  },

  created: function () {
    this.$axios
      .get('/file/recycle/list/' + localStorage.getItem('userid') + '/1')
      .then((res) => {
        var result = res.data
        this.pageInfo = result.model
        console.log(this.pageInfo.list)
      })
  },

  methods: {
    goBack() {
      this.$router.go(-1)
    },
    Check(selection) {
      this.multipleSelection = selection
      this.isOpen = false
      if (selection.length != 0) {
        this.isOpen = true
      }
    },
    CheckAll(selection) {
      this.multipleSelection = selection

      this.isOpen = false
      if (selection.length != 0) {
        this.isOpen = true
      }
    },
    currentChange(val) {
      this.$axios
        .get('/file/recycle/list/' + localStorage.getItem('userid') + '/' + val)
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    prevClick() {
      this.$axios
        .get(
          '/file/recycle/list/' +
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
          '/file/recycle/list/' +
            localStorage.getItem('userid') +
            '/' +
            this.pageInfo.nextPage
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    getFolderList(row) {
      console.log('type:' + row.type)
      if (row.type != 1) {
        return
      }
      this.$router.push({ path: '/home/filelist', query: { folderid: row.id } })
    },
    deleteFile(id) {
      this.$axios.delete('/file/delete/' + id).then((res) => {
        var result = res.data
        console.log(result)
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
        } else {
          this.$message.error(result.message)
        }
      })
    },
    removeRecycle(id) {
      this.$axios.get('/file/recycle/remove/' + id).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
        } else {
          this.$massage.error(result.message)
        }
      })
    },
    batchDelete() {
      let checkArr = this.multipleSelection
      let params = []
      checkArr.forEach(function (item) {
        params.push(item.id)
      })
      console.log(params)
      this.$axios
        .post('/file/batch/delete', {
          ids: params,
        })
        .then((res) => {
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
  watch: {
    $router(now) {
      if (now.path == '/home/file') {
        this.isShow = false
      } else {
        this.isShow = true
      }
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
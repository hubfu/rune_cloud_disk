/* eslint-disable vue/no-unused-vars */
<template>
  <div style="wight:100%">
    <h3>收藏夹</h3>
    <div>
      <el-page-header @back="goBack"
                      content="全部收藏"
                      title="上一页"
                      v-show="isShow">
      </el-page-header>
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
        <el-table-column label="名称"
                         sortable>
          // eslint-disable-next-line vue/no-unused-vars
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
              <el-menu-item index="1" v-if="scope.row.type!=1">
                <el-button class="moreBtn" @click="downLoad(scope.row.id,scope.row.name)">下载</el-button>
              </el-menu-item>
              <el-menu-item index="2" v-if="scope.row.type!=1">
                <el-dialog :visible.sync="dialogShareVisible"
                             :modal="false"
                             width='100'>
                    <el-form :model="shareInfo">
                      <el-form-item label="是否加密">
                        <el-switch v-model="shareInfo.switch"></el-switch>
                      </el-form-item>
                      <el-form-item label="提取码">
                        <el-input v-model='shareInfo.code'
                                  style="width: 100px;"
                                  :disabled='!shareInfo.switch'></el-input>
                      </el-form-item>
                      <el-form-item label='分享码'>
                        :{{shareInfo.shareLike}}
                      </el-form-item>
                    </el-form>
                    <div slot="footer"
                         class="dialog-footer">
                      <el-button type="danger"
                                 @click=" dialogShareVisible=false">取消</el-button>
                      <el-button @click="fileShar(scope.row.id)"
                                 type="primary">确定</el-button>
                    </div>
                  </el-dialog>
                  <el-button class="moreBtn"
                             @click="dialogShareVisible=true">分享</el-button>
              </el-menu-item>
              <el-menu-item index="3">
                 <el-button  class="moreBtn" @click="removeInCollect(scope.row.id)">取消收藏</el-button>
              </el-menu-item>
              <el-menu-item index="4">
                <el-button  class="moreBtn" @click="renameOpen(scope.row.name)">重命名</el-button>
                <el-dialog title="文件重命名" :visible.sync="dialogFolderRename" :modal="false">
                <el-form>
                  <el-form-item >
        
                    <el-input v-model="newFolderName"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer" >
                  <el-button type="danger" @click=" dialogFolderRename=false">取消</el-button>
                  <el-button @click="renameFile(scope.row.id)" type="primary">确定</el-button>
                </div>
              </el-dialog>
              </el-menu-item>
              <el-menu-item index="6">
                 <el-button  class="moreBtn" @click="moveInSafe(scope.row.id)">移到保险箱</el-button>
              </el-menu-item>
              <el-menu-item index="7">
                
                  <el-dialog :visible.sync="dialogFileInfo"
                             :modal="false"
                             width="400">
                    <el-descriptions title="文件信息" :column="4">
                      <el-descriptions-item label="上传时间">{{scope.row.uploadDate}}</el-descriptions-item>
                      <el-descriptions-item label="修改时间">{{scope.row.modifiedDate}}</el-descriptions-item>
                      <el-descriptions-item label="下载次数">
                        {{scope.row.downloadSum}}
                      </el-descriptions-item>
                      <el-descriptions-item label="是否收藏">
                       <el-tag v-if="scope.row.isCollect" type="success">已收藏</el-tag>
                       <el-tag v-else type="danger">未收藏</el-tag>
                      </el-descriptions-item>
                    </el-descriptions>
                    <div slot="footer"
                         class="dialog-footer">
                      <el-button @click="dialogFileInfo=false">确定</el-button>
                    </div>

                  </el-dialog>
                  <el-button class="moreBtn"
                             @click="dialogFileInfo=true">查看详细信息</el-button>
              </el-menu-item>
              <el-menu-item index="8">
                 <el-button  class="moreBtn" @click="moveInRecycle(scope.row.id)">移到回收站</el-button>
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
  name: 'File',
  components:{
   
   },
  data() {
    return {
       pageInfo: {
        pages: 0,
        list: null,
        prePage: 0,
        nextPage: 0,
      },
       shareInfo: {
        switch: false,
        code: '',
        shareLike: '',
      },
       dialogShareVisible: false,
      dialogFolderRename:false,
      newFolderName:"",
      searchInfo: '',
      tableClass: ['el-icon-folder', 'el-icon-document'],
      isShow: false,
      isOpen: false,
      dialogFileInfo: false,
      dialogUploadVisible:false,
      dialogFolderAddVisible:false,
      addFolderName:"新建文件夹"
    }
  },

 created:function(){
   this.$axios
      .get('/file/collect/list/'+ localStorage.getItem('userid')+'/1')
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
      // alert("选择")
      this.isOpen = false
      if (selection.length != 0) {
        this.isOpen = true
      }
    },
    CheckAll(selection) {
      // alert("选择")

      this.isOpen = false
      if (selection.length != 0) {
        this.isOpen = true
      }
    },
currentChange(val) {
      this.$axios
        .get(
          '/file/collect/list/' + localStorage.getItem('userid') + '/' + val
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    prevClick() {
      this.$axios
        .get(
          '/file/collect/list/' +
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
          '/file/collect/list/' +
            localStorage.getItem('userid') +
            '/' +
            this.pageInfo.nextPage
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
         
        })
    },
    getFolderList(row){
      console.log("type:"+row.type)
      if(row.type!=1){
        return;
      }
      this.$router.push({path:'/home/filelist',query:{folderid:row.id}})
    },
     downLoad(downloadId,filename){
      console.log(downloadId)
     this.$axios({
          method: 'post',
          url: '/upload/download',
          headers: {
            'Content-type': 'application/json;charset:utf-8;',
          },
          data: {id:downloadId},
          responseType: 'blob',
        }).then((res) => {
          const blob = new Blob([res.data]);
          const fileName = filename;
          if ('download' in document.createElement('a')) { // 非IE下载
            const elink = document.createElement('a');
            elink.download = new Date().getTime()+'_'+fileName;
            elink.style.display = 'none';
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href);// 释放URL 对象
            document.body.removeChild(elink);
            this.loading = false;
          } else { // IE10+下载
            navigator.msSaveBlob(blob, fileName);
            this.loading = false;
          }
        }).catch((err) => {
          console.log(err);
          this.loading = false;
        });
    },
    fileShar(shareFileId) {
      // console.log(fileId)
      this.$axios
        .post('/file/share', {
          fileId: shareFileId,
          userId: localStorage.getItem('userid'),
          isEncryptCode: this.shareInfo.switch,
          extractionCode: this.shareInfo.code,
          shareTime: 7,
        })
        .then((res) => {
          var result = res.data
          if (result.code == 200) {
            this.shareInfo.shareLike = result.model
          } else {
            this.$message.error(result.message)
          }
        })
    },
   removeInCollect(fileId){
      console.log(fileId)
      this.$axios.get("/file/collect/remove"+'/'+fileId)
      .then(res=>{
        var result=res.data;
        if(result.code==200){
          this.$message.success(result.message)
          this.$router.go(0)
        }else{
          this.$massage.error(result.message)
        }
      })
    },
    renameOpen(filename){
      this.newFolderName=filename
      this. dialogFolderRename=true;
    },
     renameFile(fileId){
      
        console.log(fileId)
       if(this.newFolderName==''){
         return;
       }
       this.$axios.put("/file/rename",{
         id:fileId,
         name:this.newFolderName
       }).then(res=>{
         var result=res.data;
         if(result.code==200){
           this.$message.success(result.message)
          this.$router.go(0)
         }else{
            this.$massage.error(result.message)
         }
       })

      this.dialogFolderRename=false;
    },
     moveInSafe(fileId){
      console.log(fileId)
       this.$axios.get("/file/safe/move"+'/'+fileId)
      .then(res=>{
        var result=res.data;
        if(result.code==200){
          this.$message.success(result.message)
          this.$router.go(0)
        }else{
          this.$massage.error(result.message)
        }
      })
    },
     moveInRecycle(fileId){
      console.log(fileId)
       this.$axios.get("/file/recycle/move"+'/'+fileId)
      .then(res=>{
        var result=res.data;
        if(result.code==200){
          this.$message.success(result.message)
          this.$router.go(0)
        }else{
          this.$massage.error(result.message)
        }
      })
    },

  },
  watch: {
    $router(now) {
      if (now.path == '/home/collect') {
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

.addBtn{
   border:none;
   margin:0px;
   
   width: 150px;
}
.moreBtn{
   border:none;
   margin:0px;
   width: 150px;
}

</style>
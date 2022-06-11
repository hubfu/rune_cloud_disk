/* eslint-disable vue/no-unused-vars */
<template>
  <div style="wight:100%">
    <div>
      <el-page-header @back="goBack"
                      content="全部文件"
                      title="上一页"
                      v-show="true">
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
                             @click="downLoad(scope.row.id,scope.row.name)" v-if="scope.row.type!=1">下载</el-button>
                </el-menu-item>
                <el-menu-item index="2" v-if="scope.row.type!=1">
                   <el-dialog :visible.sync="dialogShareVisible"
                             :modal="false"
                             width='100'>
                    <el-form :model="shareInfo">
                      <el-form-item label="是否加密">
                        <el-switch v-model="shareInfo.switch"></el-switch>
                      </el-form-item>
                       <el-form-item label='分享时间'>
                        <el-radio-group v-model="shareInfo.shareTime">
                          <el-radio :label="7">七天</el-radio>
                          <el-radio :label="30">三十天</el-radio>
                        </el-radio-group>
                      </el-form-item>
                      <el-form-item label="提取码">
                        <el-input v-model='shareInfo.code'
                                  style="width: 100px;"
                                  :disabled='!shareInfo.switch'></el-input>
                      </el-form-item>
                      <el-form-item label='分享链接'>
                        :{{shareInfo.shareLike}}
                      </el-form-item>
                    </el-form>
                    <div slot="footer"
                         class="dialog-footer">
                      <el-button type="danger"
                                 @click=" dialogShareVisible=false">取消</el-button>
                      <el-button @click="fileShare(scope.row.id)"
                                 type="primary" :disabled='shareBtu'>确定</el-button>
                    </div>
                  </el-dialog>
                  <el-button class="moreBtn"
                              @click="openShare">分享</el-button>
                </el-menu-item>
                <el-menu-item index="3">
                  <el-button class="moreBtn"
                             @click="moveInCollect(scope.row.id)">收藏</el-button>
                </el-menu-item>
                <el-menu-item index="4">
                  <el-button class="moreBtn"
                             @click="renameOpen(scope.row.name)">重命名</el-button>
                  <el-dialog title="文件重命名"
                             :visible.sync="dialogFolderRename"
                             :modal="false">
                    <el-form>
                      <el-form-item>

                        <el-input v-model="newFolderName"></el-input>
                      </el-form-item>
                    </el-form>
                    <div slot="footer"
                         class="dialog-footer">
                      <el-button type="danger"
                                 @click=" dialogFolderRename=false">取消</el-button>
                      <el-button @click="renameFile(scope.row.id)"
                                 type="primary">确定</el-button>
                    </div>
                  </el-dialog>
                </el-menu-item>
                <el-menu-item index="5">
                  <el-dialog title="文件夹"
                             :visible.sync="dialogTableVisible" :modal="false">
                    <el-table :data="folderTableData" @cell-dblclick="moveFolder">
                      <el-table-column property="name"
                                       label="文件夹名"
                                       width="200"></el-table-column>
                      <el-table-column property="path"
                                       label="相对地址"></el-table-column>
                    </el-table>
                  </el-dialog>

                  <el-button class="moreBtn" @click="getAllFolder(scope.row.id)">移动</el-button>
                </el-menu-item>
                <el-menu-item index="6">
                  <el-button class="moreBtn"
                             @click="moveInSafe(scope.row.id)">移到保险箱</el-button>
                </el-menu-item>
                 <el-menu-item index="7" v-if="scope.row.type!=1">
                  <el-dialog title="压缩内容"
                             :visible.sync="dialogZipTable" :modal="false">
                    <span v-for="info in ziplist" :key="info">
                      <i class="el-icon-document"></i>
                      {{info}}<br>
                    </span>
                  </el-dialog>
                  <el-button class="moreBtn"
                             @click="getZipInfo(scope.row.id)">解压预览</el-button>
                </el-menu-item>
                <el-menu-item index="8">
                  <el-dialog :visible.sync="dialogFileInfo"
                             :modal="false">
                    <el-descriptions title="文件信息">
                      
                      <el-descriptions-item label="上传时间">{{scope.row.uploadDate}}</el-descriptions-item>
                      <el-descriptions-item label="修改时间">{{scope.row.modifiedDate}}</el-descriptions-item>
                      <el-descriptions-item label="下载次数">
                        {{scope.row.downloadSum}}
                      </el-descriptions-item>
                      <el-descriptions-item label="是否收藏">
                        <el-tag v-if="scope.row.isCollect"
                                type="success">已收藏</el-tag>
                        <el-tag v-else
                                type="danger">未收藏</el-tag>
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
                  <el-button class="moreBtn"
                             @click="moveInRecycle(scope.row.id)">移到回收站</el-button>
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

      <el-tooltip content="压缩"
                    placement="bottom"
                    effect="light">
          <el-button icon="el-icon-files"
                     class="fastBtn"
                     circle
                     type="primary"
                     @click="compreFile"></el-button>
        </el-tooltip>
        <el-tooltip content="回收"
                    placement="bottom"
                    effect="light">
          <el-button icon="el-icon-delete"
                     class="fastBtn"
                     circle
                     type="warning"
                     @click="batchRecycle"></el-button>
        </el-tooltip>
      </div>
    </div>
  </div>
</template>
 
<script>

export default {
  name: 'SearchResult.vue',
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
        shareTime:7
      },
      dialogZipTable:false,
      ziplist:'',
      multipleSelection :'',
      shareBtu:false,
      moveFileId:"",
      folderTableData:null,
      dialogTableVisible:false,
      dialogShareVisible: false,
      dialogFileInfo: false,
      folderId: '',
      likeName:'',
      searchInfo: '',
      fileType: '',
      dialogFolderRename: false,
      newFolderName: '',
      tableClass: ['el-icon-folder', 'el-icon-document'],
      isShow: false,
      isOpen: false,
      dialogUploadVisible: false,
      dialogFolderAddVisible: false,
      addFolderName: '新建文件夹',
    }
  },
  created: function () {
    this.folderId = localStorage.getItem('userid')
    this.likeName= this.$route.query.likeName
    this.fileType= this.$route.query.fileType
    console.log(this.likeName)
    this.$axios
      .get(
        '/file/like/name/' +
          localStorage.getItem('userid') +
          '/' +
          this.likeName +
          '/1'+'/'+this.fileType
      )
      .then((res) => {
        var result = res.data
        this.pageInfo = result.model
        console.log(this.pageInfo.list)
      })
  },

  methods: {
    goBack() {
      if (this.$router.path == '/home/file') {
        return
      }
      this.$router.back()
    },
     Check(selection) {
      // alert("选择")
      this.multipleSelection =selection;
      this.isOpen = false
      if (selection.length != 0) {
        this.isOpen = true
      }
    },
    CheckAll(selection) {
      this.multipleSelection =selection;
      this.isOpen = false
      if (selection.length != 0) {
        this.isOpen = true
      }
    },
    currentChange(val) {
      this.$axios
        .get(
          '/file/like/name/' +
          localStorage.getItem('userid') +
          '/' +
          this.likeName +
            val
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    prevClick() {
      this.$axios
        .get(
          '/file/like/name/' +
          localStorage.getItem('userid') +
          '/' +
          this.likeName +
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
          '/file/like/name/' +
          localStorage.getItem('userid') +
          '/' +
          this.likeName +
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
 addNewFolder() {
      this.$axios
        .post('/file/folder/add', {
          name: this.addFolderName,
          folder: this.folderId,
          depot: localStorage.getItem('userid'),
        })
        .then((res) => {
          var result = res.data
          if (result.code == 200) {
            this.$message.success(result.message)
            this.dialogFolderAddVisible = false
            this.$router.go(0)
          } else {
            this.$message.error(result.message)
          }
        })
    },
    downLoad(downloadId,filename) {
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
    fileShare(shareFileId) {
      // console.log(fileId)
      this.$axios
        .post('/file/share', {
          fileId: shareFileId,
          userId: localStorage.getItem('userid'),
          isEncryptCode: this.shareInfo.switch,
          extractionCode: this.shareInfo.code,
          shareTime: this.shareInfo.shareTime,
        })
        .then((res) => {
          var result = res.data
          if (result.code == 200) {
            this.shareInfo.shareLike = result.model
            this.shareBtu=true
          } else {
            this.$message.error(result.message)
          }
        })
    },
    moveInCollect(fileId) {
      console.log(fileId)
      this.$axios.get('/file/collect/move' + '/' + fileId).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
        } else {
          this.$massage.error(result.message)
        }
      })
    },
    renameOpen(filename) {
      this.newFolderName = filename
      this.dialogFolderRename = true
    },
    renameFile(fileId) {
      console.log(fileId)
      if (this.newFolderName == '') {
        return
      }
      this.$axios
        .put('/file/rename', {
          id: fileId,
          name: this.newFolderName,
        })
        .then((res) => {
          var result = res.data
          if (result.code == 200) {
            this.$message.success(result.message)
            this.$router.go(0)
          } else {
            this.$massage.error(result.message)
          }
        })

      this.dialogFolderRename = false
    },
    moveInSafe(fileId) {
      console.log(fileId)
      this.$axios.get('/file/safe/move' + '/' + fileId).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
        } else {
          this.$massage.error(result.message)
        }
      })
    },
    moveInRecycle(fileId) {
      console.log(fileId)
      this.$axios.get('/file/recycle/move' + '/' + fileId).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.$router.go(0)
        } else {
          this.$massage.error(result.message)
        }
      })
    },
     getAllFolder(id){
      this.$axios.get('/file/all/folder/'+localStorage.getItem('userid'))
      .then(res=>{
        var result=res.data;
        if(result.code==200){
           this.folderTableData=result.model;
        }
        this.dialogTableVisible=true;
        this.moveFileId=id
      })
    },
    moveFolder(row){
     this.$axios.get('/file/move/'+row.id+'/'+this.moveFileId)
     .then(res=>{
       var result=res.data;
       if(result.code==200){
         this.$message.success(result.message)
         this.$router.go(0)
       }else{
          this.$message.error(result.message)
       }
     })
    },
     openShare(){
      this.dialogShareVisible=true
      this.shareBtu=false
    },
     compreFile(){
      let checkArr=this.multipleSelection;
      let params=[];
      checkArr.forEach(function(item){
        params.push(item.id)
      })
      console.log(params)
      this.$axios.post('/file/compression',{
        ids:params,
        userId:localStorage.getItem('userid')
      }).then(res=>{
        var result=res.data
        if(result.code==200){
          this.$message.success(result.message)
          this.$router.push('/home/file')
        }else{
          this.$message.error(result.message)
        }
      })
    },
    getZipInfo(fileId){
       
      this.$axios.get('/file/zip/info/'+fileId)
      .then(res=>{
        var result=res.data;
        if(result.code==200){
          this.ziplist=result.model
          // this.$message.success(result.message)
          this.dialogZipTable=true
        }else{
          this.$message.error(result.message)
        }
      })
    },
    batchRecycle(){
      let checkArr=this.multipleSelection;
      let params=[];
      checkArr.forEach(function(item){
        params.push(item.id)
      })
      console.log(params)
      this.$axios.post('/file/batch/recycle',{
        ids:params,
      }).then(res=>{
        var result=res.data
        if(result.code==200){
          this.$message.success(result.message)
          this.$router.go(0)
        }else{
          this.$message.error(result.message)
        }
      })
    }
  

  },
  watch: {
    $router(now) {
      if (now.path == '/home/filelist') {
        this.isShow = true
      } else {
        this.isShow = false
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
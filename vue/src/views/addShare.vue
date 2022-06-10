<template>
  <div>
    <el-dialog title="登录验证"
               :visible.sync="loginVisible"
               width="30%">
      <el-form :model='user' rules='rules1' ref="user">
        <el-form-item label='账号'>
          <el-input v-model="user.id" prop="id"></el-input>
        </el-form-item>
        <el-form-item label='密码'>
          <el-input v-model="user.password" prop='password' type='password'></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="loginVisiblee = false">取 消</el-button>
        <el-button type="primary"
                   @click="lognin">确 定</el-button>
      </span>
    </el-dialog>
    <div>

    </div>
    <div>
      <el-dialog title="添加分享文件"
                 :visible.sync="addShareVisible"
                 width="30%">
        <el-form :model='shareGroup' rules='rules2' ref="shareGroup">
          <el-form-item label='是否加密'>
            <el-switch v-model="shareGroup.isEncrypt"></el-switch>
          </el-form-item>
          <el-form-item label='提取码' prop="extractionCode">
            <el-input v-model="shareGroup.extractionCode"
                      :disabled="!shareGroup.isEncrypt"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="addShareVisible = false">取 消</el-button>
          <el-button type="primary"
                     @click="addShareFile">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Addshare',

  data() {
    return {
      loginVisible:false,
      addShareVisible:false,
      user:{
        id:"",
        password:""
      },
      shareGroup:{
        id:'',
        userId:'',
        isEncrypt:"",
        extractionCode:''
      },
     rules1:{
       id:[ { required: true, message: '请输入手机号', trigger: 'blur' }],
       password:[{ required: true, message: '请输入密码', trigger: 'blur' }]

     },
rules2:{
      extractionCode:[ { required: true, message: '请输入提取码', trigger: 'blur' }],
      

     },

    }
  },
  created:function(){
    console.log(this.$route.query.groupid)
    this.shareGroup.id=this.$route.query.groupid
   this.$axios.get("/file/share/group/"+this.shareGroup.id).then((result) => {
     var res =result.data
     if(res.code!=200){
        this.$message.error(res.message)
     }else{
       this.shareGroup.isEncrypt=res.model.isEncrypt
      
      this. loginVisible=true
       
     }
   }).catch((err) => {
     console.log(err)
   });
  },

  mounted() {},

  methods: {
    lognin(){
       this.$refs.user.validate((valid) => {
          if (valid) {
             this.$axios
        .post('/user/login',this.user
        )
        .then((response) => {
          var result = response.data
          if (result.code == 200) {
             this.shareGroup.userId=this.user.id
            this.$message.success(result.message)
             this. loginVisible=false
              localStorage.setItem('userid', result.model.id)
            localStorage.setItem('userpassword', result.model.password)
            localStorage.setItem('username', result.model.name)
            setTimeout(() => {
              this.addShareVisible=true
            }, 3 * 1000)
          } else {
            this.$message.error(result.message)
            this.$router.go(0)
          }
        })
          } else {
            
            return false;
          }
        });
     
    },
    addShareFile(){
       this.$refs.shareGroup.validate((valid) => {
          if (valid) {
             this.$axios.post('/file/share/add', this.shareGroup).then((res) => {
        var result = res.data
        if (result.code == 200) {
          this.$message.success(result.message)
          this.dialogAddShareFileVisible = false
          this.$router.push("/home/file")
        } else {
          this.$message.error(result.message)
        }
      })
          } else {
            
            return false;
          }
        });
     
    }
  },
}
</script>

<style lang="scss" scoped>
</style>
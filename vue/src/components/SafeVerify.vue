<template>
  <div style="dispaly:flex;width:100%;height:100%">
    <div class="butDivClass">
      <el-button type="primary"
               @click="dialogVisible = true"
               class="butClass"
               >点击验证</el-button>

    <el-dialog title="密码验证"
               :visible.sync="dialogVisible"
               width="30%"
               >
          <el-tabs type="border-card">
            <el-tab-pane label="密码验证">
              <el-form :model="formData">
                <el-form-item>
                  <el-input v-model="formData.safePassword" type='password'></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="loginSafe" type='primary'>验证</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label='添加修改密码'>
              <el-form :model="formData">
                <el-form-item label='登录密码'>
                  <el-input v-model="formData.loginPassword" type="password"></el-input>
                </el-form-item>
                <el-form-item label='验证密码'>
                  <el-input v-model="formData.safePassword" type='password'></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="updateSafePassword" type="primary">更新</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
      
      <span slot="footer"
            class="dialog-footer">
        <el-button type="primary"
                   @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Safeverify',

  data() {
    return {
      dialogVisible:false,
      formData:{
        loginPassword:"",
        safePassword:""
      }
    }
  },

  mounted() {},

  methods: {
    loginSafe(){
      if(this.formData.safePassword==''){
         this.$message.error("请重新检查密码！！！")
        return;
      }

      console.log("进入loginSafe")
      this.$axios.post("/user/safe/login",{
        id:localStorage.getItem("userid"),
        safePassword:this.formData.safePassword
      })
      .then(res=>{
        var result=res.data;
        if(result.code==200){
          this.$message.success(result.message)
          setTimeout(() => {
            this.$router.push('/home/safe')
          }, 3*1000);
        }else{
          this.$message.error(result.message)
        }
      })
      
    },
    updateSafePassword(){
      console.log("更新密码")
      if(this.formData.loginPassword==''||this.formData.safePassword==''){
        this.$message.error("请重新检查密码！！！")
      }
      if(this.formData.loginPassword!=localStorage.getItem("userpassword")){
         this.$message.error("登录密码错误！！！")
         return
        }else{
          this.$axios.put('/user/update',{
            id:localStorage.getItem("userid"),
            safePassword:this.formData.safePassword
          })
          .then(res=>{
            var result=res.data;
            if(result.code==200){
          this.$message.success(result.message)
          this.formData.loginPassword='';
          this.formData.safePassword='';
          this.dialogVisible=false;

        }else{
          this.$message.error(result.message)
        }
          })
        }
    }
  },
}
</script>

<style lang="scss" scoped>
.butDivClass {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center; //定义body的元素垂直居中（/主轴居中-主轴默认是x轴）
justify-content: center; //定义body的元素水平居中//侧轴居中-主轴默认是y轴）
}
.butclass{
   align-items: center; //定义body的元素垂直居中（/主轴居中-主轴默认是x轴）
justify-content: center; //定义body的元素水平居中//侧轴居中-主轴默认是y轴）
}
</style>
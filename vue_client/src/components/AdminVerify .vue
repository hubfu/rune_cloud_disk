<template>
  <div style="dispaly:flex;width:100%;height:100%">
    <div class="butDivClass">
      <el-button type="primary"
               @click="dialogVisible = true"
               class="butClass"
               >ROOT验证</el-button>
      <el-button type="primary"
               @click="goIndex"
               class="butClass"
               >点击登出</el-button>
    <el-dialog title="ROOT验证"
               :visible.sync="dialogVisible"
               width="30%"
               >
          <el-tabs type="border-card">
            <el-tab-pane label="ROOT密码验证">
              <el-form :model="formData">
                <el-form-item>
                  <el-input v-model="formData.rootPassword" type='password'></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="isRoot" type='primary'>验证</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label='修改密码'>
              <el-form :model="formData">
                <el-form-item label='原密码'>
                  <el-input v-model="formData.oldPassword" type="password"></el-input>
                </el-form-item>
                <el-form-item label='新密码'>
                  <el-input v-model="formData.newPassword" type='password'></el-input>
                </el-form-item>
                <el-form-item label='二次验证'>
                  <el-input v-model="formData.checkPassword" type='password'></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="updateAdminPassword" type="primary">更新</el-button>
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
  name: 'Adminverify',

  data() {
    return {
      dialogVisible:false,
      formData:{
        rootPassword:"",
        oldPassword:"",
        newPassword:"",
        checkPassword:""

      }
    }
  },

  mounted() {},

  methods: {
    isRoot(){
     this.$axios.post("/admin/login",{
       name:localStorage.getItem("adminname"),
       password:this.formData.rootPassword
     }).then((result) => {
       var res=result.data
       if(res.code==200){
         this.$message.success("验证成功")
        setTimeout(() => {
          this.$router.push('/adminhome/admintable')
        }, 3*1000);
       }else{
         this.$message.error("验证失败")
       }
     }).catch((err) => {
       console.log(err)
     });
    },
    updateAdminPassword(){
     this.$axios.put("/admin/update",{
        name: localStorage.getItem("adminname"),
       password: this.formData.checkPassword
     }).then((result) => {
       var res=result.data
       if(res.code==200){
         this.$message.success(res.message)
         this.dialogVisible=false
         this.formData.oldPassword=''
         this.formData.newPassword=''
         this.formData.checkPassword=''
         this.$router.go(0)
         
       }else{
         this.$message.error(res.message)
       }
     }).catch((err) => {
       console.log(err)
     });
    },
    goIndex(){
      this.$router.push('/')
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
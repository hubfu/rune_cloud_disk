<template>
  <div id="app">
    <el-container>
      <el-header style="text-al">
        <div>
          <h1>符文云盘管理端</h1>
        </div>
      </el-header>
      <el-main>
        <el-tabs type="border-card"
                 style="margin: auto; width: 400px;">
          <el-tab-pane label="登录">
            <el-form :model="admin"
                     :rules="rules1"
                     ref="admin">
              <el-form-item label="管理账号"
                            prop="name">
                <el-input v-model="admin.name">

                </el-input>

              </el-form-item>
              <el-form-item label="密码"
                            prop="password">
                <el-input
                          type="password"
                          v-model="admin.password"></el-input>

              </el-form-item>
              <el-form-item>
                <el-button type="primary"
                           
                           @click="login">立即登录</el-button>

              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>

  </div>
</template>

<script>
export default {
  name:'AdminLogin',

  data() {
    return {
      admin:{
        name:'',
        password:''
      },

      loginChecked: false,
      rules1: {
       name: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur',
          },
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
        ],
      },
      
    }
  },

  mounted() {},
  created: function () {
    
  },
  methods: {
    login() {
       this.$refs.admin.validate((valid) => {
          if (valid) {
            this.$axios
        .post('/admin/login', {
          name: this.admin.name,
          password: this.admin.password,
        })
        .then((response) => {
          var result = response.data
          if (result.code == 200) {
            localStorage.setItem('adminpassword', result.model.password)
            localStorage.setItem('adminname', result.model.name)
            localStorage.setItem('adminpower',this.admin.password)
            this.$message.success(result.message)
            setTimeout(() => {
              this.$router.push({ path:'/adminhome/statistics' })
            }, 3 * 1000)
          } else {
            this.$message.error(result.message)
             setTimeout(() => {
              this.$router.go(0)
            }, 3 * 1000)
           
          }
        })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      console.log(this.admin)
      
    },
  },
}
</script>

<style lang="scss" scoped>
#app {
  background-color: #6666cc;
  text-align: center;
}
</style>
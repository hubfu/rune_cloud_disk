<template>
  <div id="app">
    <el-container>
      <el-header style="text-al">
        <div>
          <h1>符文云盘</h1>
        </div>
      </el-header>
      <el-main>
        <el-tabs type="border-card"
                 style="margin: auto; width: 400px;">
          <el-tab-pane label="登录">
            <el-form :model="user"
                     :rules="rules1"
                     ref="user">
              <el-form-item label="手机号"
                            prop="loginUserID">
                <el-input v-model="user.loginUserID">

                </el-input>

              </el-form-item>
              <el-form-item label="密码"
                            prop="loginUserPassword">
                <el-input placeholder="密码为字母、数字和符号三种组合，8-20个字符"
                          type="password"
                          v-model="user.loginUserPassword"></el-input>

              </el-form-item>
              <el-form-item>
                <el-checkbox v-model="loginChecked">我已阅读并同意用户协议和隐私条款</el-checkbox>
              </el-form-item>
              <el-form-item>
                <el-button type="primary"
                           :disabled="!loginChecked"
                           @click="login">立即登录</el-button>

              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="注册">
            <el-form :model="newuser"
                     :rules="rules2"
                     ref="newuser">

              <el-form-item label="手机号"
                            prop="regID">
                <el-input v-model="newuser.regID">

                </el-input>

              </el-form-item>
              <el-form-item label="昵称"
                            prop="regName">
                <el-input v-model="newuser.regName"
                          type="text">

                </el-input>

              </el-form-item>
              <el-form-item label="密码"
                            prop="regPassword">
                <el-input placeholder="密码为字母、数字和符号三种组合，8-20个字符"
                          v-model="newuser.regPassword"
                          type="password"></el-input>

              </el-form-item>
              <el-form-item label="确认密码"
                            prop="towRegPassword">
                <el-input v-model="newuser.towRegPassword"
                          type="password"></el-input>

              </el-form-item>
              <el-form-item>
                <el-checkbox v-model="regChecked">我已阅读并同意用户协议和隐私条款</el-checkbox>
              </el-form-item>
              <el-form-item>
                <el-button type="primary"
                           :disabled="!regChecked"
                           @click="register">立即注册</el-button>

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
  name: 'Login',

  data() {
    var passwordEqual = (rule, value, callback) => {
      if (value != this.newuser.regPassword) {
        callback(new Error('请重新输入密码'))
      } else {
        callback()
      }
    }
    return {
      user: {
        loginUserID: '',
        loginUserPassword: '',
      },
      newuser: {
        regName: '',
        regID: '',
        regPassword: '',
        towRegPassword: '',
      },
      loginChecked: false,
      regChecked: false,
      rules1: {
        loginUserID: [
          {
            required: true,
            pattern:
              /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/,
            message: '请输入手机号',
            trigger: 'blur',
          },
        ],
        loginUserPassword: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
        ],
      },
      rules2: {
        regID: [
          {
            required: true,
            pattern:
              /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/,
            message: '请输入正确的手机号',
            trigger: 'blur',
          },
        ],
        regName: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
        ],

        regPassword: [
          {
            required: true,
            pattern:
              /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,20}$/,
            message: '密码为字母、数字和符号三种组合，8-20个字符',
            trigger: 'blur',
          },
        ],
        towRegPassword: [
          { required: true, validator: passwordEqual, trigger: 'blur' },
        ],
      },
    }
  },

  mounted() {},
  created: function () {
  
  },
  methods: {
    login() {
      this.$refs.user.validate((valid) => {
          if (valid) {
          
            this.$axios
        .post('/user/login', {
          id: this.user.loginUserID,
          password: this.user.loginUserPassword,
        })
        .then((response) => {
          var result = response.data
          if (result.code == 200) {
            localStorage.setItem('userid', result.model.id)
            localStorage.setItem('userpassword', this.user.loginUserPassword)
            localStorage.setItem('username', result.model.name)
            this.$message.success(result.message)
            setTimeout(() => {
              this.$router.push({ path: '/home' })
            }, 3 * 1000)
          } else {
            this.$message.error(result.message)
            this.$router.go(0)
          }
        })
          } else {
            
            return false;
          }
          console.log('no end1')
        });
         
      
    },
    register() {
       this.$refs.newuser.validate((valid) => {
          if (valid) {
             this.$axios
        .post('/user/register', {
          id: this.newuser.regID,
          name: this.newuser.regName,
          password: this.newuser.towRegPassword,
        })
        .then((response) => {
          var result = response.data
          if (result.code == 200) {
            this.$message.success(result.message)
            this.$router.go(0)
          } else {
            this.$message.error(result.message)
          }
        })
          } else {
           
            return false;
          }
        });
     
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
<template>
  <div>
    <div class="user-main">

      <div class="user-info">
        <el-descriptions direction="vertical"
                         :column="4"
                         border
                         title="用户信息">
          <template slot="extra">
            <el-button type="primary"
                       size="small"
                       @click="openName">修改昵称</el-button>
            <el-dialog title="修改信息"
                       :visible.sync="NickNameVisible"
                       width="30%">
              <el-form :rules="ru1"
                       :model="form">
                <el-form-item label="昵称"
                              prop="newUserName">
                  <el-input v-model="form.newUserName"></el-input>
                </el-form-item>
              </el-form>
              <span slot="footer"
                    class="dialog-footer">
                <el-button @click="close">取 消</el-button>
                <el-button type="primary"
                           @click="updateUserName">确 定</el-button>
              </span>
            </el-dialog>
          </template>
          <template slot="extra">
            <el-button type="primary"
                       size="small"
                       @click=" PasswordVisible=true">修改密码</el-button>
            <el-dialog title="修改信息"
                       :visible.sync="PasswordVisible"
                       width="30%">
              <el-form :rules="ru2"
                       :model="form">
                <el-form-item label="旧密码"
                              prop="oldPassword">
                  <el-input v-model="form.oldPassword"
                            type="password"></el-input>
                </el-form-item>
                <el-form-item label="新密码"
                              prop="newPassword">
                  <el-input v-model="form.newPassword"
                            type="password"></el-input>
                </el-form-item>
                <el-form-item label="确认密码"
                              prop="confirmPassword">
                  <el-input v-model="form.confirmPassword"
                            type="password"></el-input>
                </el-form-item>
              </el-form>
              <span slot="footer"
                    class="dialog-footer">
                <el-button @click="close">取 消</el-button>
                <el-button type="primary"
                           @click="updatePassowrd">确 定</el-button>
              </span>
            </el-dialog>
          </template>
          <el-descriptions-item label="UID">{{userInfo.id}}</el-descriptions-item>
          <el-descriptions-item label="昵称">{{userInfo.name}}</el-descriptions-item>
          <el-descriptions-item label="购买的容量"
                                :span="2">{{userInfo.buySize}}</el-descriptions-item>
          <el-descriptions-item label="初始的容量">
            {{userInfo.initialSize}}
          </el-descriptions-item>
          <el-descriptions-item label="已用的容量">
            {{userInfo.usedSize}}
          </el-descriptions-item>
          <el-descriptions-item label="注册时间">{{userInfo.regDate}}</el-descriptions-item>
        </el-descriptions>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Userinfo',

  data() {
    var passwordEqual = (rule, value, callback) => {
      console.log(value)
      if (value != localStorage.getItem('userpassword')) {
        callback(new Error('请输入正确的密码！！！'))
      } else {
        callback()
      }
    }
    var passwordConfirm = (rule, value, callback) => {
      console.log('vaule:' + value)
      console.log('new:' + this.form.newPassword)
      if (value != this.form.newPassword) {
        callback(new Error('与第一次输入的密码不相等，请重新输入密码'))
      } else {
        callback()
      }
    }

    return {
      userInfo: {
        id: '',
        name: '',
        buySize: '',
        initialSize: '',
        usedSize: '',
        regDate: '',
      },
      NickNameVisible: false,
      PasswordVisible: false,
      form: {
        newUserName: '',
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      },

      ru1: {
        newUserName: [
          {
            required: true,
            message: '请输入昵称',
            trigger: 'blur',
          },
        ],
      },
      ru2: {
        oldPassword: [
          { required: true, validator: passwordEqual, trigger: 'blur' },
        ],
        newPassword: [
          {
            required: true,
            pattern:
              /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,20}$/,
            message: '密码为字母、数字和符号三种组合，8-20个字符',
            trigger: 'blur',
          },
        ],
        confirmPassword: [
          { required: true, validator: passwordConfirm, trigger: 'blur' },
        ],
      },
    }
  },
  mounted() {},
  created: function () {
    this.$axios
      .get('/user/info/' + localStorage.getItem('userid'))
      .then((res) => {
        var result = res.data
        this.userInfo = result.model
        console.log(this.userInfo)
      })
  },
  methods: {
    updateUserName() {
      if(this.form.newUserName==''){
        this.$message.error("用户名不能为空！！！")
        return
      }
      this.$axios
        .put('user/update', {
          id: localStorage.getItem('userid'),
          name: this.form.newUserName,
        })
        .then((res) => {
          var result = res.data
          if (result.code == 200) {
            this.$message.success(result.message)
          } else {
            this.$message.error(result.message)
          }
          this.NickNameVisible = false
          this.$router.go(0)
        })
    },
     updatePassowrd() {
       if(this.form.confirmPassword==''){
        this.$message.error("密码不能为空！！！")
        return
      }
      this.$axios
        .put('user/update', {
          id: localStorage.getItem('userid'),
          password: this.form.confirmPassword,
        })
        .then((res) => {
          var result = res.data
          if (result.code == 200) {
            this.$message.success(result.message)
           localStorage.setItem('userpassword', this.form.confirmPassword)
          } else {
            this.$message.error(result.message)
          }
          this.PasswordVisible = false
          this.$router.go(0)
        })
    },
    openName() {
      this.form.newUserName = this.userInfo.name
      this.NickNameVisible = true
    },
    close() {
      this.NickNameVisible = false
      this.PasswordVisible = false
      this.$router.go(0)
    },
  },
}
</script>

<style lang="scss" scoped>
.user-main {
  margin: 120px 50px;
}
</style>
<template>

  <div>
    <div>
      <el-button icon="el-icon-plus"
                 type="primary"
                 circle
                 style="float:right"
                 @click="openAdd"></el-button>
      <el-dialog title="添加新用户"
                 :visible.sync="addAdminDialog"
                 width="30%">
        <el-form :model='newAdmin'>
          <el-form-item label='账户名'>
            <el-input v-model="newAdmin.name"></el-input>
          </el-form-item>
          <el-form-item label='密码'>
            <el-input v-model="newpassword"
                      type="password"></el-input>
          </el-form-item>
          <el-form-item label='二次验证'>
            <el-input v-model="newAdmin.password"
                      type="password"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="addAdminDialog  = false">取 消</el-button>
          <el-button type="primary"
                     @click="addAdmin">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <el-table :data="adminList" stripe>
      <el-table-column label="账号名"
                       prop="name">

      </el-table-column>
      <el-table-column label='密码'
                       prop='password'>

      </el-table-column>
      <el-table-column label='操作'>
        <template slot-scope="scope">
          <el-dialog title="编辑用户"
                     :visible.sync="updateAdminDialog"
                     width="30%">
            <el-form :model='newAdmin'>
              <el-form-item label='账户名'>
                {{newAdmin.name}}
              </el-form-item>
              <el-form-item label='新密码'>
                <el-input v-model="newpassword"
                          type='password'></el-input>
              </el-form-item>
              <el-form-item label='二次验证'>
                <el-input v-model="newAdmin.password"
                          type='password'></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
              <el-button @click="updateAdminDialog  = false">取 消</el-button>
              <el-button type="primary"
                         @click="updateAdmin">确 定</el-button>
            </span>
          </el-dialog>
          <el-button type='text'
                     @click="openUpdate(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'Admintable',

  data() {
    return {
      adminList: null,
      addAdminDialog: false,
      updateAdminDialog: false,
      newpassword: '',
      newAdmin: {
        name: '',
        password: '',
      },
    }
  },

  created: function () {
    this.$axios
      .get('/admin/all')
      .then((result) => {
        var res = result.data
        this.adminList = res.model
      })
      .catch((err) => {
        console.log(err)
      })
  },

  methods: {
    addAdmin() {
      if (this.newAdmin.password == '' || this.newAdmin.name == '') {
        this.$message.error('账户或密码不能为空！！！')
        return
      }
      this.$axios
        .post('/admin/add', this.newAdmin)
        .then((result) => {
          var res = result.data
          if (res.code == 200) {
            this.$message.success(res.message)
            this.addAdminDialog = false
            this.$router.go(0)
          } else {
            this.$message.error(res.message)

            this.addAdminDialog = false
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    openUpdate(row) {
      this.newAdmin.name = row.name
      this.newpassword=''
      this.newAdmin.password = ''
      this.updateAdminDialog = true
    },
    openAdd() {
      this.newAdmin.name = 'newAdmin'
      this.newAdmin.name = ''
      this.newAdmin.password = ''
      this.addAdminDialog = true
    },
    updateAdmin() {
      console.log(this.newAdmin)
      if (this.newAdmin.password == '' || this.newAdmin.name == '') {
        this.$message.error('账户或密码不能为空！！！')
        return
      }
      this.$axios
        .put('/admin/update', this.newAdmin)
        .then((result) => {
          var res = result.data
          if (res.code == 200) {
            this.$message.success(res.message)
            this.$router.go(0)
          } else {
            this.$message.error(res.message)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },
}
</script>

<style lang="scss" scoped>
</style>
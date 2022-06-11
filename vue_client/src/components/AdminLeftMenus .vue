<template>
  <div class="left">

    <h3>符文云盘管理端</h3>
    <el-menu
             class="left-menu"
             router
             >
       <el-menu-item index="/adminhome/statistics">
        <i class="el-icon-data-analysis"></i>
        <span>一览</span>
      </el-menu-item>      
      <el-menu-item index="/adminhome/usertable">
        <i class="el-icon-user"></i>
        <span>用户</span>
      </el-menu-item>
      <el-menu-item index="/adminhome/combo">
        <i class="el-icon-goods"></i>
        <span>套餐</span>
      </el-menu-item>
      <el-menu-item index="/adminhome/leaderboard">
        <i class="el-icon-s-data"></i>
        <span>排行榜</span>
      </el-menu-item>
      <el-menu-item index="/adminhome/feedback">
        <i class="el-icon-phone-outline"></i>
        <span>反馈</span>
      </el-menu-item>
      <el-menu-item index="/adminhome/adminverify">
        <i class="el-icon-edit"></i>
                <span>账户</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'AdminLeftMenus',

  data() {
    return {
    
    }
  },
  created: function () {
 
  },
  methods: {
    
     
  },
}
</script>

<style scoped>
.left {
  margin: 0px;
  width: auto;
  background-color: #f5f5f5;
  height: 100%;
}
.left-menu:not(.el-menu--collapse) {
  width: auto;
  background-color: #f5f5f5;
  margin-top: 30px;
}
h3 {
  margin: 0 auto;
  padding: 0 auto;
  margin-left: 20px;
}

a {
  text-decoration: none;
  color: #000;
}
</style>
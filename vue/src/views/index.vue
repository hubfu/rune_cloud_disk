<template>
  <div id="app">
    <el-container>
  <el-header>
    <h1>符文云盘</h1>
    <h3>享受快捷方便的网络文件服务</h3>
  </el-header>
  <el-main>
    <el-card style="width: 300px; margin:80px 80px 80px 115px;float:left; ">
      <div slot='header'>
        <i class="el-icon-user" style=" font-size: 100px;"></i>
      </div>
            <router-link to="/login">进入用户登录</router-link>
            <i class="el-icon-right"></i>
    </el-card>
    <el-card style="width: 300px; margin:80px;float:left; ">
      <div slot='header'>
        <i class="el-icon-s-custom" style=" font-size: 100px;"></i>
      </div>
        <router-link to="/adminlogin">进入管理登录</router-link>
            <i class="el-icon-right"></i>
    </el-card>
    <el-card style="width: 300px;float:left; margin:80px;">
      <div slot='header'>
        <i class="el-icon-user" style="font-size: 100px;"></i>
      </div>
      <el-link href='https://github.com/hubfu'>支持我们<i class="el-icon-right"></i> </el-link>
    </el-card>
    
  </el-main>
  <el-footer>
    <span><h4>开发者@软件c181王治福</h4></span>
    <span>个人邮箱：wzf327075@163.com</span>
  </el-footer>
</el-container>
  </div>
</template>

<script>
export default {
  name: 'Index',

  data() {
    return {
      
    };
  },
 created:function(){
     localStorage.clear()
 },
  mounted() {
    
  },

  methods: {
    
  },
};
</script>

<style lang="scss" scoped>
#app {
  background-color: #6666cc;
  text-align: center;
}
a {
  text-decoration: none;
  color: #000;
}
</style>
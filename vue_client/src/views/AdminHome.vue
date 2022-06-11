<template>
  <el-container class="index-container">
    <el-aside class="el-aside">
      <admin-left-menus></admin-left-menus>
    </el-aside>
    <el-main>
      <router-view>

      </router-view>
    </el-main>
  </el-container>
</template>

<script>
// @ is an alias to /src
import AdminLeftMenus from '../components/AdminLeftMenus .vue'

export default {
  name: 'AdminHome',
  components: {
    AdminLeftMenus
  },

  data() {
    return {}
  },
 
 
}
</script>
<style>
.index-container {
  height: 100%;
}
</style>
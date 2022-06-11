<template>
  <el-container class="index-container">
    <el-aside class="el-aside">
      <left-menus />
    </el-aside>
    <el-main>
      <router-view>

      </router-view>
    </el-main>
  </el-container>
</template>

<script>
// @ is an alias to /src
import LeftMenus from '../components/LeftMenus.vue'

export default {
  name: 'Home',
  components: {
    LeftMenus
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
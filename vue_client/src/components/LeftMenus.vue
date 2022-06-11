<template>
  <div class="left">

    <h3>符文云盘</h3>
    <el-menu default-active="/home/file"
             class="left-menu"
             router>
      <el-menu-item index="/home/file">
        <i class="el-icon-folder"></i>
        <span>文件</span>
      </el-menu-item>
      <el-menu-item index="/home/share">
        <i class="el-icon-share"></i>
        <span>分享</span>
      </el-menu-item>
      <el-menu-item index="/home/safeverify">
        <i class="el-icon-lock"></i>
        <span>保险箱</span>
      </el-menu-item>
      <el-menu-item index="/home/collect">
        <i class="el-icon-star-off"></i>
        <span>收藏夹</span>
      </el-menu-item>
      <el-menu-item index="/home/recycle">
        <i class="el-icon-delete"></i>
        <span>回收站</span>
      </el-menu-item>
    </el-menu>
    <div class="capacity-ratio">
      <div class="ratio-info">
        <span>{{used}}/{{total}}</span>
        <router-link to="/home/shop"
                     style="float:right">扩容</router-link>
      </div>
      <div class="el-progress">
        <el-progress :percentage="this.percentage"
                     :show-text="false"
                     :stroke-width="7"></el-progress>
      </div>
    </div>
    <div class="user-div">
      <i class="el-icon-user"></i>

      <span class="username-span">{{username}}</span>
      <el-popover placement="top"
                  width="200"
                  trigger="click">
        <el-menu style="width: auto; border-right: 0;"
                 router>
          <el-menu-item index="/home/userinfo">
            <span>我的账号</span>
          </el-menu-item>
          <el-menu-item index="/home/shop">
            <span>我要购买</span>
          </el-menu-item>
          <el-menu-item index="/home/consume">
            <span>我的消费</span>
          </el-menu-item>
          <el-menu-item index="userservice">
            <span>我的客服</span>
          </el-menu-item>
          <el-menu-item index="/">
            <span>我要退出</span>
          </el-menu-item>
        </el-menu>
        <el-button icon="el-icon-more"
                   circle
                   size="mini"
                   class="more"
                   slot="reference"></el-button>
      </el-popover>

    </div>
  </div>
</template>

<script>
export default {
  name: 'LeftMenus',

  data() {
    return {
      username: '',
      total: '20g',
      used: '10g',
      percentage: 0,
    }
  },
  created: function () {
    this.$axios
      .get('/user/depot/info/' + localStorage.getItem('userid'))
      .then((reponse) => {
        var result = reponse.data
        this.total = this.getFileSize(result.model.maxCapacity)
        this.used = this.getFileSize(result.model.usedCapacity)
        this.percentage =
          (result.model.usedCapacity / result.model.maxCapacity) * 100
      })
    this.username = localStorage.getItem('username')
  },
  methods: {
    getFileSize(size) {
      if (size == 0) {
        return '0B'
      }
      var k = 1024
      var sizes = ['B', 'KB', 'MB', 'GB']
      var i = Math.floor(Math.log2(size) / Math.log2(k))
      return (size / Math.pow(k, i)).toPrecision(3) + '' + sizes[i]
    },
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

.capacity-ratio {
  margin-top: 200px;
  padding-bottom: 30px;
  background-color: #f5f5f5;
}
.user-div {
  margin-top: 50px;
}
.user-div i {
  margin-left: 15px;
  margin-right: 15px;
  font-size: 30px;
}
.more {
  margin-left: 40px;
  margin-right: 20px;
}
.el-progress {
  width: 250px;
  margin: auto;
  margin-top: 10px;
}
.ratio-info {
  width: 250px;
  margin: auto;
}
a {
  text-decoration: none;
  color: #000;
}
</style>
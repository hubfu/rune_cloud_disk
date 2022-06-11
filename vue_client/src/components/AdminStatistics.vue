<template>
  <div id='app'>
    <el-descriptions title="系统一览表"
                     direction="vertical"
                     :column="4"
                     border>
      <el-descriptions-item label="注册用户数">{{statistics.userNum}}</el-descriptions-item>
      <el-descriptions-item label="付费用户数">{{statistics.payUserNum}}</el-descriptions-item>
      <el-descriptions-item label="付费率">{{getPercentage(statistics.payUserNum,statistics.userNum)}}%</el-descriptions-item>
      <el-descriptions-item label="用户总空间" >{{getFileSize(statistics.userTotalCapacity)}}</el-descriptions-item>
      <el-descriptions-item label="用户已用空间">{{getFileSize(statistics.userUsablePatitionSpace)}}</el-descriptions-item>
      <el-descriptions-item label="用户剩余空间" >{{getFileSize(statistics.userTotalCapacity-statistics.userUsablePatitionSpace)}}</el-descriptions-item>
      <el-descriptions-item label="用户空间使用率">{{getPercentage(statistics.userUsablePatitionSpace,statistics.userTotalCapacity)}}%</el-descriptions-item>
      <el-descriptions-item label="硬盘总空间">
      {{getFileSize(statistics.totalCapacity)}}
      </el-descriptions-item>
      <el-descriptions-item label="硬盘可用空间" >
      {{getFileSize(statistics.usablePatitionSpace)}}
      </el-descriptions-item>
      <el-descriptions-item label="硬盘已用空间" >
        {{getFileSize(statistics.totalCapacity-statistics.usablePatitionSpace)}}
      </el-descriptions-item>
      <el-descriptions-item label="硬盘使用率">
         {{getPercentage(statistics.totalCapacity-statistics.usablePatitionSpace,statistics.totalCapacity)}}%
      </el-descriptions-item>
      <el-descriptions-item label="空间是否充足" v-if="statistics.totalCapacity-statistics.usablePatitionSpace>5*1024*1024*1024">
      <el-tag type='success'>充足</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="是否充足" v-else>
      <el-tag type='danger'>不充足</el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default {
  name: 'AdminStatistics',

  data() {
    return {
      statistics:{
        userNum:0,
        payUserNum:0,
        userTotalCapacity:'',
        userUsablePatitionSpace:'',
        totalCapacity:'',
        usablePatitionSpace:''
      }
    }
  },
 created:function(){
   this.$axios.get("/admin/statistics").then((result) => {
     var res=result.data
     if(res.code==200){
       this.statistics=res.model
       
     }
   }).catch((err) => {
     console.log(err)
   });
 }
 ,
  mounted() {},

  methods: {
    // 转换文件大小的显示格式
    getFileSize(size) {
      if (size == 0) {
        return '0B'
      }
      var k = 1024
      var sizes = ['B', 'KB', 'MB', 'GB']
      var i = Math.floor(Math.log2(size) / Math.log2(k))
      return (size / Math.pow(k, i)).toPrecision(3) + '' + sizes[i]
    },
    // 保留两位有效数字
    getPercentage(divisor,dividend){
      var result=divisor/dividend*100
      return result.toFixed(2)
    }
  },
}
</script>

<style lang="scss" scoped>
#app {
 margin: 50px 40px;
}
</style>
<template>
  <div>
    <h3>商店</h3>
    <div v-for="itme in info"
         :key="itme.id">
      <el-card class="card">
        <div slot="header">
          <i class="el-icon-collection-tag"></i>
          <span>{{itme.name}}</span>
         
        </div>
        <div>
          
          <span>容量:{{itme.capacity}}</span><br>
          <span>价格:{{itme.amount}}元</span><br>
          <span>时间:{{itme.time}}月</span>
        </div>
        <el-button style="float:right;"
                   type="primary"
                   size="small"
                   @click="toPayment(itme.id)">去支付</el-button>
        <el-dialog title="支付宝扫码付账"
                   :visible.sync="dialogVisible"
                   width="252px"
                   center>
          <vueQr :text="qr"
                 :size="200"
                 :margin="10"
                 :whiteMargin="false"
                 v-if="!paySucc"></vueQr>
          <span>
            <el-result icon='success' title='支付成功'  sub-title='正在关闭扫码窗口' v-if="paySucc"></el-result>
          </span>
        </el-dialog>
      </el-card>
    </div>
  </div>
</template>

<script>
import vueQr from 'vue-qr'
export default {
  name: 'Shop',
  components: {
    vueQr
  },

  data() {
    return {
      info:null ,
      dialogVisible: false,
      qr: '',
      paySucc: false,
    }
  },

  mounted() {},
  created:function(){
     this.$axios.get('/user/combo').then(res=>{

       var result=res.data;
       console.log(result)
       this.info=result.model
     
     })
  },
  methods: {
    toPayment(cid){
      this.paySucc=false;
      let _this=this;
      this.$axios.post("/user/alipay",{
        userID:localStorage.getItem("userid"),
        comboID:cid
      }).then(res=>{
        if(res.data.code!=200){
          this.$message.error(res.data.message);
           setTimeout(()=>{
                 
          this.dialogVisible=false
          this.$router.go(0)
                
              },3*1000)
          
        }
        this.qr=res.data.model;
        console.log("qr:"+this.qr)
       
        if("WebSocket"in window){
          //打开一个webSocket
          // 通道地址按项目进行配置
          var ws=new WebSocket("ws://localhost:8080/bindingRecord");
          ws.onopen=function(){
            // web Socket已经连接，使用send()方法发送数据
            ws.send("data");
            // alert("数据发送中...")
          };
          ws.onmessage=function(evt){
            var received_msg=evt.data;
            console.log("接收到的信息："+received_msg)
            // // 接收后台推送的数据
            console.log("后台数据已经接收.."+evt.data);
           
           var is=Boolean(evt.data)
            if(is){
              _this.paySucc=true;
              setTimeout(()=>{
                 
                _this.dialogVisible=false;
                
              },3*1000)
              _this.$router.go(0)
              console.log("刷新")
            }
            ws.close();

          }
          ws.onclose=function(){

          }
        } else{
          alert("你的浏览器不支持WebSocket")
        }
      })
     
      this.dialogVisible=true;
    },
     getFileSize(size){
       if(size==0){
         return '0B'
       }
       var k=1024;
       var sizes=['B','KB','MB','GB']
      var  i=Math.floor(Math.log2(size)/Math.log2(k));
       return(size/Math.pow(k,i)).toPrecision(3)+''+sizes[i]

    }
  },
}
</script>

<style lang="scss" scoped>
.card {
  width: 200px;
  float: left;
  margin: 0px 30px;
  background-color: #f8df72;
}
</style>

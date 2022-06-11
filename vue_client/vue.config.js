module.exports={
  devServer:{
    port:70,
    open:false,
    hotOnly:true,
    proxy:{
      '/api':{
        target:'http://localhost:8080/',
        ws: true,
        ChangeOrigin:true,
        pathRewrite:{
          '^/api':'/'
        }
      }
    }
  }
}
<template>
  <div>

    <uploader :options="options"
              :file-status-text="fileStatusText"
              :key="uploader_key"
              :autoStart="true"
              @file-added="filesAdded"
              @file-success="onFileSuccess"
              @file-error="onFileError"
              ref="uploader">
      <uploader-unsupport></uploader-unsupport>

      <uploader-btn style="background-color:#67C23A;color: #fefefe;margin-right:15px">上传文件</uploader-btn>
      <uploader-list></uploader-list>
    </uploader>
  </div>
</template>

<script>
import qs from 'qs'
import SparkMD5 from 'spark-md5'
export default {
  name: 'SimpleUploaderFile',
  data() {
    return {
      uploader_key: new Date().getTime(),
      options: {
        target: '/api/upload/chunk',
        testChunks: true,
        fileParameterName: 'file',
        checkChunkUploadedByResponse: function (chunk, message) {
          console.log('message' + message)
          let objMessage = message
          let chunkNumbers = objMessage.chunkNumbers
          // console.log("numbers"+chunkNumbers)
          return (chunkNumbers || []).indexOf(chunk.offset + 1) >= 0
        },
        generateUniqueIdentifier:function(file){
          // file.uniqueIdentifier= this.computeMD5(file)
          // console.log("1-md5"+file.uniqueIdentifier)
          console.log("file-1:"+file)
          
        },
        chunkSize: 1024 * 1024 * 1, //MB
        simultaneousUploads: 3, //并发上传
        parseTimeRemaining: function (timeRemaining, parsedTimeRemaining) {
          return parsedTimeRemaining
            .replace(/\syears?/, '年')
            .replace(/\days?/, '天')
            .replace(/\shours?/, '小时')
            .replace(/\sminutes?/, '分钟')
            .replace(/\sseconds?/, '秒')
        },
        query: {
          // id: localStorage.getItem("userid")
        },
      },
      attrs: {},
      statusTextMap: {
        success: '上传成功',
        error: '上传出错了',
        uploading: '上传中...',
        paused: '暂停',
        waiting: '等待中...',
        cmd5: '计算md5...',
      },
      fileStatusText: (status) => {
        return this.statusTextMap[status]
      },
    }
  },

  methods: {
    filesAdded(file) {
      file.pause()
      if (file.size > 2 * 1024 * 1024 * 1024) {
        this.$message.error('文件过大')
        file.removeFile(file)
        return
      }

      this.$axios
        .get(
          '/upload/detect/' + localStorage.getItem('userid') + '/' + file.size
        )
        .then((res) => {
          var result = res.data
          if (result.code == 200) {
            this.$message.success(result.message)
            this.computeMD5(file)
            //  file.resume()
          } else {
            this.$message.error(result.message)
            file.removeFile(file)
          }
        })
    },
    computeMD5(file) {
      let md5=''
      let blobSlice =
          File.prototype.slice ||
          File.prototype.mozSlice ||
          File.prototype.webkitSlice,
        chunkSize = 2097152,
        chunks = Math.ceil(file.size / chunkSize),
        currentChunk = 0,
        spark = new SparkMD5.ArrayBuffer(),
        fileReader = new FileReader()
      let time = new Date().getTime()
      file.cmd5 = true
      fileReader.onload = (e) => {
        spark.append(e.target.result) // Append array buffer
        currentChunk++

        if (currentChunk < chunks) {
          //console.log(`第${currentChunk}分片解析完成, 开始第${currentChunk +1} / ${chunks}分片解析`);
          let percent = Math.floor((currentChunk / chunks) * 100)
          file.cmd5progress = percent
          loadNext()
        } else {
          console.log('finished loading')
          md5 = spark.end()
          console.log(
            `MD5计算完成：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${
              file.size
            } 用时：${new Date().getTime() - time} ms`
          )
          spark.destroy() //释放缓存
          file.uniqueIdentifier = md5 //将文件md5赋值给文件唯一标识
          file.cmd5 = false //取消计算md5状态
          this.md5IsExist(file)
        }
      }
      fileReader.onerror = () => {
        console.warn('oops, something went wrong.')
        file.cancel()
      }

      let loadNext = () => {
        let start = currentChunk * chunkSize,
          end = start + chunkSize >= file.size ? file.size : start + chunkSize

        fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end))
      }

      loadNext()
      
    },
    md5IsExist(file) {
      console.log(file)
      this.$axios
        .get(
          '/upload/check/md5/' +
            localStorage.getItem('userid') +
            '/' +
            file.uniqueIdentifier
        )
        .then((result) => {
          var res = result.data
          if (res.code == 200) {
            this.$message.success(res.message)
            file.removeFile(file)
            this.$router.go(0)
          } else {
            file.resume() //开始上传
          }
        })
        .catch((err) => {
          console.log(err)
        })
        
    },
    onFileSuccess(file) {
      this.$axios
        .post(
          '/upload/merge',
          qs.stringify({
            filename: file.name,
            identifier: file.uniqueIdentifier,
            totalSize: file.size,
            userid: localStorage.getItem('userid'),
          })
        )
        .then(this.$message.success(file.name + '上传成功!!!'))
      setTimeout(() => {
        file.cancel()
      }, 3 * 1000)
      this.$router.go(0)
    },

    onFileError(file) {
      this.$message.error(file.name + '上传失败！！！')
    },
  },
}
</script>

<style>
.uplode-example {
  margin: 10px, 10px;
}
.uploade-btn {
  margin: 10px, 15px;
}
.uploader-example .uploader-list {
  max-height: auto;
  overflow: auto;
  overflow-x: hidden;
  overflow-y: auto;
}
</style>
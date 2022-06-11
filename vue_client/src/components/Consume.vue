<template>
  <div>
    <div style="margin: 60px auto;">
      <el-table :data="pageInfo.list"
                tooltip-effect="dark"
                style="width: 100%"
                stripe>

        <el-table-column prop="subject"
                         label="名称"
                         width="120">
        </el-table-column>
        <el-table-column prop="totalAmount"
                         label="花费"
                         show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="buyTime"
                         label="购买日期"
                         sortable
                         show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="expireDate"
                         label="结束日期"
                         sortable
                         show-overflow-tooltip>
        </el-table-column>

      </el-table>
    </div>

    <div class="pages">
      <el-pagination background
                     layout="prev, pager, next"
                     :page-count="pageInfo.pages"
                     @current-change="currentChange"
                     @prev-click="prevClick"
                     @next-click="nextclick">
      </el-pagination>
    </div>
  
  </div>
</template>

<script>
export default {
  name: 'Consume',

  data() {
    return {
      pageInfo: {
        pages: 0,
        list: null,
        prePage: 0,
        nextPage: 0,
      },
    }
  },

  mounted() {},
  created: function () {
    this.$axios
      .get('/user/consume/record/' + localStorage.getItem('userid') + '/1')
      .then((res) => {
        var result = res.data
        this.pageInfo = result.model
      
      })
  },

  methods: {
    currentChange(val) {
      this.$axios
        .get(
          '/user/consume/record/' + localStorage.getItem('userid') + '/' + val
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    prevClick() {
      this.$axios
        .get(
          '/user/consume/record/' +
            localStorage.getItem('userid') +
            '/' +
            this.pageInfo.prePage
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
        })
    },
    nextclick() {
      this.$axios
        .get(
          '/user/consume/record/' +
            localStorage.getItem('userid') +
            '/' +
            this.pageInfo.nextPage
        )
        .then((res) => {
          var result = res.data
          this.pageInfo = result.model
         
        })
    },
  },
}
</script>

<style lang="scss" scoped>
.pages {
  text-align: center;
}
.bottomMenu {
  width: 100%;
  text-align: center;
  margin-top: 30px;
}
.moreBtn {
  border: none;
  margin: 0px;
  width: 80px;
}
.fastBtn {
  border: none;
  margin: 5px;
  width: auto;
}
</style>
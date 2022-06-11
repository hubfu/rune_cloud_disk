<template>
  <div>
    <h3>套餐统计</h3>
    <div>
      <el-table
    :data="tableData"
    max-height="300"
    border
     :summary-method="getSummaries"
    stripe
    show-summary
    style="width: 100%; margin-top: 20px">
    <el-table-column
     type='index'
      label="销量排名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="套餐名">
    </el-table-column>
    <el-table-column
      prop="amount"
      label="单价（元）">
    </el-table-column>
    <el-table-column
      prop="buySum"
      label="销量"
       sortable>
    </el-table-column>
    <el-table-column
       sortable
      label="销售额（元）"
      prop='totalSales'>
      
    </el-table-column>
  </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Comboleaderboard',

  data() {
    return {
      tableData:[]
    };
  },
  created:function(){
     this.$axios.get("/admin/combo/leaderboard")
     .then(res=>{
       var result=res.data
       if(result.code==200){
         this.tableData=result.model
       }
     })
  },

  mounted() {
    
  },

  methods: {
    getSummaries(param) {
        const { columns, data } = param;
        const sums = [];
        columns.forEach((column, index) => {
          if (index === 0) {
            sums[index] = '合计';
            return;
          }
           if (index === 2) {
            sums[index] = '无';
            return;
          }
          const values = data.map(item => Number(item[column.property]));
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
            // sums[index] += ' 元';
          } else {
            sums[index] = '无';
          }
        });

        return sums;
      }
  },
};
</script>

<style lang="scss" scoped>

</style>
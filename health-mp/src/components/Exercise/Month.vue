<template>
  <view class="week">
    <view class="charts-box">
      <qiun-data-charts type="column" :chart-data="chartData" :reshow="true" />
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue';
const chartData = reactive({
  categories: ['2016', '2017', '2018', '2019', '2020', '2021'],
  series: [
    {
      name: '本月运动时间(h)',
      data: [35, 26, 31, 33, 13, 24],
    },
  ],
});
</script>

<style scoped lang="less">
.charts-box {
  height: 530rpx;
}
</style>

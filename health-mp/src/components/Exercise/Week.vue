<template>
  <view class="week">
    <view class="charts-box">
      <qiun-data-charts type="column" :chart-data="chartData" :reshow="true" @complete="complete" />
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue';
const chartData = reactive({
  categories: ['一', '二', '三', '四', '五', '六', '七'],
  series: [
    {
      name: '本周运动时长(h)',
      data: [3, 4, 2, 1, 5, 6, 3],
    },
  ],
});
const complete = () => {
  console.log('Week');
};
</script>

<style scoped lang="less">
.charts-box {
  height: 530rpx;
}
</style>

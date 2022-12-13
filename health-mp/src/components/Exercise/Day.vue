<template>
  <view class="week">
    <view class="charts-box">
      <qiun-data-charts type="column" :chart-data="chartData" @complete="complete" />
    </view>
    <view class="text">
      <view>
        今日运动时长
        <view class="num">5</view>
        小时
      </view>
      <view>
        消耗
        <view class="num">90</view>
        千卡，相当于消耗
        <view class="num">5</view>
        个鸡蛋
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive, toRefs } from 'vue';
const chartData = reactive({
  categories: ['6~9', '9~12', '12~15', '16~18', '18~21', '21~24'],
  series: [
    {
      name: '今日运动时长(min)',
      data: [23, 13, 24, 33],
    },
  ],
});
const complete = () => {
  console.log('Day');
};
</script>

<style scoped lang="less">
.charts-box {
  height: 530rpx;
}
.text {
  font-size: 30rpx;
  color: black;
  margin: 10rpx 0 20rpx 44rpx;
  .num {
    display: inline;
    font-size: 40rpx;
    font-weight: bolder;
    color: #eb9c9d;
  }
}
</style>

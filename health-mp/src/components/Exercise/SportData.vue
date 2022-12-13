<template>
  <view class="sport-data">
    <view class="sport-data-card">
      <view class="area">
        <uni-segmented-control
          :current="current"
          :values="segmentedControlData.items"
          active-color="#83C6E8"
          style-type="text"
          @clickItem="onClickItem"
        ></uni-segmented-control>
      </view>
      <view class="content">
        <view v-show="segmentedControlData.current === 0">
          <view class="text">运动周期表（每日）</view>
          <Day></Day>
          <!-- <SportRecord></SportRecord> -->
        </view>
        <view v-show="segmentedControlData.current === 1">
          <view class="text">运动周期表（每周）</view>
          <Week></Week>
        </view>
        <view v-show="segmentedControlData.current === 2">
          <view class="text">运动周期表（每月）</view>
          <Month></Month>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import Week from './Week.vue';
import Month from './Month.vue';
import Day from './Day.vue';
import SportRecord from '@/components/Exercise/SportRecord.vue';
import { reactive } from 'vue';
//分段器
const segmentedControlData = reactive({
  items: ['日', '周', '月'],
  current: 0,
});
const onClickItem = (e) => {
  if (segmentedControlData.current != e.currentIndex) {
    segmentedControlData.current = e.currentIndex;
  }
};
</script>

<style scoped lang="less">
.sport-data {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 30rpx;
}
.sport-data-card {
  width: 700rpx;
  // height: 640rpx;
  border-radius: 35rpx;
  margin-bottom: 30rpx;
  box-shadow: 0 3rpx 6rpx #dfdbdb;
}
.area {
  margin: 23rpx 0 0 20rpx;
  width: 250rpx;
}
.content {
  font-size: 35rpx;
  font-weight: 300;
  color: #1890ff;
  .text {
    margin: 23rpx 0 10rpx 45rpx;
  }
}
</style>

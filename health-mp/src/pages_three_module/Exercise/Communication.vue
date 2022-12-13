<template>
  <view class="communication">
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
        <SportData></SportData>
        <SportRecord></SportRecord>
      </view>
      <view v-show="segmentedControlData.current === 1">
        <Ranking></Ranking>
      </view>
    </view>
  </view>
  <ExerciseTarbar></ExerciseTarbar>
</template>

<script setup>
import ExerciseTarbar from '../../components/Exercise/ExerciseTarbar.vue';
import SportData from '@/components/Exercise/SportData.vue';
import SportRecord from '@/components/Exercise/SportRecord.vue';
import Ranking from '../../components/Exercise/Ranking.vue';
import { reactive } from 'vue';

//分段器
const segmentedControlData = reactive({
  items: ['运动数据', '排行'],
  current: 0,
});
const onClickItem = (e) => {
  if (segmentedControlData.current != e.currentIndex) {
    segmentedControlData.current = e.currentIndex;
  }
};
</script>

<style lang="less" scoped>
.area {
  width: 300rpx;
  margin: 10rpx 0 0 15rpx;
}
.card {
  width: 600rpx;
  height: 200rpx;
  background-color: antiquewhite;
}
</style>

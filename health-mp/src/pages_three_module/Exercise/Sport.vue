<template>
  <view class="content">
    <view class="img">
      <image src="https://s1.ax1x.com/2022/03/10/bh6qeS.png" mode="aspectFit"></image>
    </view>
    <uni-segmented-control
      :current="current"
      :values="segmentedControlData.items"
      active-color="#83C6E8"
      style-type="text"
      @clickItem="onClickItem"
    ></uni-segmented-control>
    <view class="content">
      <view v-show="segmentedControlData.current === 0">
        <WarmUp></WarmUp>
      </view>
      <view v-show="segmentedControlData.current === 1">
        <Relaxation></Relaxation>
      </view>
      <view v-show="segmentedControlData.current === 2">
        <Run></Run>
      </view>
      <view v-show="segmentedControlData.current === 3">
        <Swimming></Swimming>
      </view>
      <view v-show="segmentedControlData.current === 4">
        <Cycling></Cycling>
      </view>
      <view v-show="segmentedControlData.current === 5">
        <Walk></Walk>
      </view>
    </view>
  </view>
  <ExerciseTarbar></ExerciseTarbar>
</template>

<script setup>
import ExerciseTarbar from '../../components/Exercise/ExerciseTarbar.vue';
import WarmUp from '@/components/Exercise/WarmUp.vue';
import Relaxation from '@/components/Exercise/Relaxation.vue';
import Run from '@/components/Exercise/Run.vue';
import Swimming from '@/components/Exercise/Swimming.vue';
import Cycling from '../../components/Exercise/Cycling.vue';
import Walk from '../../components/Exercise/Walk.vue';
import { reactive } from 'vue';
//分段器
const segmentedControlData = reactive({
  items: ['热身', '放松', '跑步', '游泳', '骑行', '行走'],
  current: 0,
});
const onClickItem = (e) => {
  if (segmentedControlData.current != e.currentIndex) {
    segmentedControlData.current = e.currentIndex;
  }
};
</script>

<style lang="less" scoped>
.img {
  display: flex;
  justify-content: center;
  margin: 33rpx 0 55rpx 0;
  image {
    height: 240rpx;
    width: 700rpx;
  }
}
</style>

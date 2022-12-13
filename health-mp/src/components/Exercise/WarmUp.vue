<template>
  <view class="warm-up">
    <view v-for="(item, index) in data" :key="index" class="warm-up-card">
      <image :src="item.imgSrc" mode="aspectFit" class="warm-up-img"></image>
      <view>{{ item.text }}</view>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue';
const data = reactive([
  {
    imgSrc: 'https://s1.ax1x.com/2022/03/10/bh53Rg.png',
    text: '腿部热身',
  },
  {
    imgSrc: 'https://s1.ax1x.com/2022/03/10/bhHtUI.png',
    text: '手臂热身',
  },
  {
    imgSrc: 'https://s1.ax1x.com/2022/03/10/bhHKC6.png',
    text: '膝关节运动',
  },
]);
</script>

<style scoped lang="less">
.warm-up {
  display: flex;
  flex-direction: column;
  margin-top: 15rpx;
  margin-bottom: 110rpx;
  .warm-up-card {
    // height: 182rpx;
    // width: 100%;
    display: flex;
    align-items: flex-start;
    font-size: 35rpx;
    font-weight: bold;
    margin-top: 35rpx;
    .warm-up-img {
      width: 288rpx;
      height: 182rpx;
      margin: 0rpx 20rpx 0 57rpx;
    }
  }
}
</style>

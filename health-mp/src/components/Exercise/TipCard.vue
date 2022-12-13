<template>
  <view v-for="(item, index) in tipCard" :key="index" class="tip-card">
    <view class="left">
      <view class="icon-part">
        <image :src="item.iconSrc" class="tip-icon"></image>
        <view>{{ item.iconName }}</view>
      </view>
      <view class="text">{{ item.text }}</view>
    </view>
    <view class="right">
      <image :src="item.imgSrc" mode="aspectFit"></image>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue';
const props = defineProps({
  tipCard: {
    type: Array,
    required: true,
  },
});
</script>

<style scoped lang="less">
.tip-card {
  width: 700rpx;
  height: 350rpx;
  display: flex;
  border: 1rpx solid #d6d2d2;
  border-radius: 60rpx;
  margin-bottom: 30rpx;
  .left {
    display: flex;
    flex-direction: column;
    width: 380rpx;
    margin: 46rpx 0 0 43rpx;
    font-size: 28rpx;
    font-weight: 500;
    line-height: 40rpx;
    text-align: justify;
    .icon-part {
      display: flex;
      font-size: 35rpx;
      font-weight: bold;
      align-items: center;
      margin-bottom: 20rpx;
      .tip-icon {
        width: 86rpx;
        height: 86rpx;
        margin-right: 16rpx;
      }
    }
  }
  .right {
    display: flex;
    align-items: center;
    margin-left: 10rpx;
    image {
      width: 240rpx;
      height: 180rpx;
    }
  }
}
</style>

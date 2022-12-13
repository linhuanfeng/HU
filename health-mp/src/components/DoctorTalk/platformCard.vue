<template>
  <view class="content">
    <image :src="plantformCard.plantformImg" mode="aspectFit"></image>
    <view class="plantformName">{{ plantformCard.plantformName }}</view>
    <view class="attention">{{ plantformCard.plantformAttention }}关注</view>
  </view>
</template>

<script setup>
const props = defineProps({
  plantformCard: {
    type: Object,
    required: true,
  },
});
</script>

<style lang="less" scoped>
.content {
  width: 240rpx;
  height: 130rpx;
  background-color: #f5f7fa;
  box-shadow: 1rpx 1rpx 3rpx #f5f7fa;
  border-radius: 20rpx;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-left: 95rpx;
  margin-bottom: 30rpx;
  margin-right: 20rpx;
  image {
    width: 100rpx;
    height: 100rpx;
    position: absolute;
    border-radius: 10rpx;
    top: 50%;
    left: -20%;
    transform: translateY(-50%);
  }
  .plantformName {
    font-size: 26rpx;
    color: #303232;
  }
  .attention {
    font-size: 20rpx;
    color: #949699;
  }
}
</style>

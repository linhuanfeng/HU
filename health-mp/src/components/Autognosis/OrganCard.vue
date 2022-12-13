<template>
  <view class="content">
    <view class="left">
      <image :src="organImgSrc" mode="aspectFit"></image>
    </view>
    <view class="right">
      <view class="organ-name">{{ organName }}</view>
      <text class="organ-text">{{ organText }}</text>
    </view>
  </view>
</template>

<script setup>
const props = defineProps({
  organName: {
    type: String,
    required: true,
  },
  organImgSrc: {
    type: String,
    required: true,
  },
  organText: {
    type: String,
    required: true,
  },
});
</script>

<style scoped lang="less">
.content {
  width: 680rpx;
  height: 325rpx;
  border: 1rpx solid #00c4f7;
  border-radius: 36rpx;
  box-shadow: 2rpx 2rpx 10rpx 0rpx #daddda;
  position: fixed;
  background-color: #f2fcff;
  top: 50rpx;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  .left {
    flex: 2;
    text-align: center;
    image {
      width: 150rpx;
      height: 150rpx;
    }
  }
  .right {
    flex: 3;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    .organ-name {
      font-size: 64rpx;
      font-weight: bold;
      padding-bottom: 10rpx;
    }
    .organ-text {
      width: 330rpx;
      font-size: 26rpx;
      color: #949699;
      line-height: 40rpx;
      text-align: justify;
    }
  }
}
</style>

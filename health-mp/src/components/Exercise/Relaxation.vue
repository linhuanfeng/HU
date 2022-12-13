<template>
  <view class="realx">
    <image src="https://s1.ax1x.com/2022/03/10/b4E2nJ.png" mode="aspectFit" class="img"></image>
    <text class="text">休息一下吧</text>
  </view>
</template>

<script setup></script>

<style scoped lang="less">
.realx {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 110rpx;
  .img {
    width: 100%;
    height: 368rpx;
  }
  .text {
    position: absolute;
    font-size: 35rpx;
    font-weight: bold;
    color: aliceblue;
    top: 520rpx;
    right: 193rpx;
  }
}
</style>

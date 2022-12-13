<template>
  <view class="rank">
    <view class="text">今日运动时长排名</view>
    <view class="words">我的排名：{{ 1 }}</view>
    <view class="rank-lists">
      <view class="rank-lists-item">
        <view class="number">1</view>
        <image src="../../static/images/common/avatar.png" class="img"></image>
        <view class="name">小黑有话说</view>
        <view class="time">3.5小时</view>
      </view>
    </view>
  </view>
</template>

<script setup></script>

<style scoped lang="less">
.text {
  font-size: 30rpx;
  font-weight: 300;
  margin: 22rpx 0 10rpx 35rpx;
}
.words {
  position: absolute;
  right: 93rpx;
  font-size: 31rpx;
  font-weight: 600;
}
.rank-lists {
  font-size: 30rpx;
  font-weight: 600;
  margin-top: 100rpx;
  .rank-lists-item {
    width: 100%;
    height: 119rpx;
    border-bottom: 1rpx solid #ececec;
    display: flex;
    align-items: center;
    .number {
      margin-left: 57rpx;
    }
    .img {
      width: 87rpx;
      height: 87rpx;
      margin: 0 0 0 51rpx;
    }
    .name {
      margin-left: 30rpx;
    }
    .time {
      position: absolute;
      right: 100rpx;
    }
  }
}
</style>

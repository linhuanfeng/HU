<template>
  <view class="center">
    <view class="cards">
      <view class="words">运动记录</view>
      <view class="sport-record-card">
        <view class="texts">
          <view class="detail-texts">
            <view class="strong">跑步</view>
            <view>1公里 ( 5分钟 )</view>
            <view>强度：中</view>
          </view>
        </view>
        <image src="https://s1.ax1x.com/2022/03/10/b4xFZ8.png" mode="aspectFit" class="img"></image>
      </view>
      <view class="sport-record-card">
        <view class="texts">
          <view class="detail-texts">
            <view class="strong">跑步</view>
            <view>1公里 ( 5分钟 )</view>
            <view>强度：中</view>
          </view>
        </view>
        <image src="https://s1.ax1x.com/2022/03/10/b4xFZ8.png" mode="aspectFit" class="img"></image>
      </view>
      <button class="btn">生成运动报告</button>
    </view>
  </view>
</template>

<script setup></script>

<style scoped lang="less">
.center {
  display: flex;
  justify-content: center;
}
.cards {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10rpx;
  margin-bottom: 115rpx;
  border: 1rpx solid #eceaea;
  border-radius: 35rpx;
  width: 700rpx;
  .words {
    font-size: 36rpx;
    font-weight: bolder;
    margin: 23rpx 0 40rpx -430rpx;
  }
  button::after {
    display: none;
  }
  .btn {
    width: 663rpx;
    height: 95rpx;
    margin-bottom: 20rpx;
    border: 1rpx solid #707070;
    border-radius: 71rpx;
    background: linear-gradient(to right, #9fdafa, #d1efff);
  }
}
.sport-record-card {
  width: 532rpx;
  height: 196rpx;
  border-radius: 47rpx;
  box-shadow: 1rpx 1rpx 8px #eceaea;
  background-color: #83c6e844;
  display: flex;
  align-items: center;
  margin-bottom: 44rpx;
  margin-left: -80rpx;
  .texts {
    width: 520rpx;
    height: 172rpx;
    display: flex;
    border: 1rpx solid #ffffff;
    background-color: #ffffff;
    border-radius: 47rpx;
    margin-left: 10rpx;
    align-items: center;
    .detail-texts {
      margin-left: 56rpx;
      font-size: 30rpx;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      line-height: 56rpx;
      font-weight: 300;
      .strong {
        font-size: 32rpx;
        font-weight: 600;
      }
    }
  }
  .img {
    position: absolute;
    width: 120rpx;
    right: 65rpx;
  }
}
</style>

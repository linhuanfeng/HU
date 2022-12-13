<template>
  <view class="content">
    <view class="left">
      <view class="text text-weight">{{ userName }}</view>
      <view class="text">身高: {{ userHeight }}</view>
      <view class="text">体重: {{ userWeight }}</view>
      <view class="text">BMI指数:{{ userBMI }}</view>
    </view>
    <view class="right">
      <view class="user-img">
        <image :src="userAvatar" mode="aspectFit"></image>
      </view>
      <button class="btn" @click="toEdit">修改个人资料</button>
    </view>
  </view>
</template>

<script setup>
//接收父组件传递进来的参数
const props = defineProps({
  userName: {
    type: String,
    required: true,
  },
  userHeight: {
    type: String,
    required: true,
  },
  userWeight: {
    type: String,
    required: true,
  },
  userBMI: {
    type: String,
    required: true,
  },
  userAvatar: {
    type: String,
    default: '../../static/images/common/avatar.png',
  },
});
//跳转至修改个人资料页面
const toEdit = () => {
  uni.navigateTo({
    url: '/pages/HomePage/UserInfo',
  });
};
</script>

<style scoped lang="less">
.content {
  width: 690rpx;
  height: 300rpx;
  border: 1rpx solid #f2f5f5;
  border-radius: 24rpx;
  box-shadow: 2rpx 2rpx 10rpx 0rpx #daddda;
  // position: fixed;
  background-color: #ffffff;
  // top: 50rpx;
  // left: 50%;
  // transform: translateX(-50%);
  display: flex;
  align-items: center;
  .left {
    flex: 1;
    display: flex;
    flex-direction: column;
    margin-left: 40rpx;
    align-items: flex-start;
    justify-content: space-evenly;
    .text {
      font-size: 32rpx;
      font-weight: 100;
      margin-top: 10rpx;
    }
    .text-weight {
      font-size: 46rpx;
      font-weight: bold;
      margin-bottom: 10rpx;
    }
  }
  .right {
    flex: 1;
    display: flex;
    flex-direction: column;
    .user-img {
      text-align: center;
      image {
        width: 160rpx;
        height: 160rpx;
        border-radius: 80rpx;
      }
    }
    button::after {
      border: none;
    }
    .btn {
      font-size: 24rpx;
      font-weight: bold;
      background-color: #f5f7fa;
      margin-top: 10rpx;
    }
  }
}
</style>

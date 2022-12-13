<template>
  <view class="Sleep">
    <uni-nav-bar :fixed="false" background-color="#313233" height="88rpx" :border="false"></uni-nav-bar>
    <view class="custom-navbar">
      <uni-icons type="left" size="24" color="white" @click="toHomePage" style="margin-left: 30rpx"></uni-icons>
      <view class="custom-navbar-text">好眠</view>
      <uni-icons type="gear-filled" size="24" color="white"></uni-icons>
    </view>
    <view class="clock">
      <Clock></Clock>
    </view>
  </view>
</template>

<script setup>
import Clock from '../../components/Sleep/Clock.vue';
const toHomePage = () => {
  uni.navigateBack();
};
</script>

<style lang="less" scoped>
.Sleep {
  width: 100%;
  background-color: #313233;
  display: flex;
  flex-direction: column;
  .custom-navbar {
    color: white;
    font-size: 38rpx;
    font-weight: bold;
    display: flex;
    align-items: center;
    .custom-navbar-text {
      margin: 0 130rpx 0 180rpx;
    }
  }
}
</style>

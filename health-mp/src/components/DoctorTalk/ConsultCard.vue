<template>
  <view class="consult-card">
    <view class="consult-card-image">
      <image :src="consultCard.imgSrc" mode="aspectFit"></image>
    </view>
    <view class="consult-card-text">
      <view class="row-one">
        <text class="row-one-name">{{ consultCard.name }}</text>
        <text class="row-one-occupation">{{ consultCard.occupation }}</text>
      </view>
      <view class="row-two">
        <text class="row-two-field">{{ consultCard.field }}</text>
        <text class="row-two-college">{{ consultCard.college }}</text>
      </view>
      <view class="row-three">
        <text class="introduction">{{ consultCard.introduction }}</text>
      </view>
      <view class="row-four">
        擅长：
        <uni-tag
          v-for="(good, index) in consultCard.goods"
          :key="index"
          :text="good"
          size="small"
          :custom-style="customStyle"
        ></uni-tag>
      </view>
      <view class="row-five">
        <uni-rate :readonly="true" :value="consultCard.value" size="32" />
        {{ consultCard.value }}
      </view>
      <view class="row-six">
        <view>¥ {{ consultCard.price }}</view>
        <view class="row-six-text">{{ consultCard.way }}</view>
        <button class="btn" @click="toContactPage">联系专家→</button>
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive, ref } from 'vue';
const customStyle = ref('background-color:#ffffff;color:black;border-radius: 7rpx;width:40rpx;margin-right:20rpx');
/**
 * 子组件接收的数据
 */
const props = defineProps({
  consultCard: {
    type: Object,
    required: true,
  },
});
// 跳转到医疗咨询页面
const toContactPage = () => {
  uni.navigateTo({
    url: '../../pages/DoctorTalk/ConnectionPage',
  });
};
</script>

<style scoped lang="less">
.consult-card {
  width: 715rpx;
  height: 360rpx;
  border-radius: 32rpx;
  box-shadow: 3rpx 3rpx 6rpx rgba(0, 0, 0, 0.2);
  display: flex;
  align-items: center;
  margin: 30rpx auto;
  .consult-card-image {
    margin-left: 26rpx;
    image {
      width: 192rpx;
      height: 288rpx;
    }
  }
  .consult-card-text {
    width: auto;
    height: 350rpx;
    display: flex;
    flex-direction: column;
    margin-left: 36rpx;
    align-items: flex-start;
    justify-content: space-evenly;
    .row-one {
      display: flex;
      align-items: flex-end;
      .row-one-name {
        font-size: 34rpx;
        font-weight: bold;
        margin-bottom: -4rpx;
        margin-right: 22rpx;
      }
      .row-one-occupation {
        font-size: 26rpx;
        font-weight: bolder;
        color: #00c4f7;
      }
    }
    .row-two {
      display: flex;
      font-size: 26rpx;
      font-weight: bold;
      .row-two-field {
        margin-right: 16rpx;
      }
    }
    .row-three {
      width: 470rpx;
      font-size: 26rpx;
      font-weight: bolder;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
    }
  }
  .row-four {
    display: flex;
    font-size: 30rpx;
    font-weight: bolder;
  }
  .row-five {
    font-size: 36rpx;
    font-weight: bolder;
    color: #ffbf00;
    display: flex;
    align-items: center;
  }
  .row-six {
    font-size: 36rpx;
    font-weight: bolder;
    color: #ffbf00;
    display: flex;
    align-items: flex-end;
    .row-six-text {
      font-size: 26rpx;
      font-weight: bold;
      color: #828484;
      margin-left: 10rpx;
      margin-bottom: 5rpx;
    }
    .btn {
      height: 48rpx;
      line-height: 48rpx;
      text-align: center;
      font-size: 28rpx;
      border: 1rpx solid #0088ff;
      border-radius: 30rpx;
      background-color: #d1efff;
      color: #0088ff;
      margin-left: 35rpx;
    }
  }
}
</style>

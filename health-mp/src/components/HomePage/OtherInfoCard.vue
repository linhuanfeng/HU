<template>
  <view class="content">
    <view class="left">
      <image :src="infoCardObject.iconSrc" mode="aspectFit"></image>
    </view>
    <view class="right">
      <button type="default" class="btn" @click="toThePage(infoCardObject.navigateSrc)">
        {{ infoCardObject.text }}
      </button>
    </view>
  </view>
</template>

<script setup>
const props = defineProps({
  infoCardObject: {
    type: Object,
    required: true,
  },
});
const toThePage = (src) => {
  uni.navigateTo({
    url: src,
  });
};
</script>

<style scoped lang="less">
.content {
  display: flex;
  align-items: center;
  width: 326rpx;
  height: 150rpx;
  border-radius: 18rpx;
  background: linear-gradient(120deg, #ffc7c7 0%, #d1efff 100%);
  .left {
    margin-top: 13rpx;
    image {
      width: 118rpx;
      height: 118rpx;
    }
  }
  .right {
    button::after {
      border: none;
    }
    .btn {
      font-size: 36rpx;
      color: #ffffff;
      background: inherit;
    }
  }
}
</style>

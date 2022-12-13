<template>
  <view class="content" :class="detailCard.position">
    <view class="title" :class="detailCard.titleBorder">{{ detailCard.title }}</view>
    <view class="detail-content">&emsp;&emsp;{{ detailCard.detailContent }}</view>
  </view>
</template>

<script setup>
import { ref, reactive } from 'vue';
const props = defineProps({
  detailCard: {
    type: Object,
    required: true,
  },
});
</script>

<style lang="less" scoped>
.content {
  width: 660rpx;
  height: auto;
  margin-top: 20rpx;
  display: flex;
  flex-direction: column;
  .title {
    width: 200rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-size: 38rpx;
    text-align: center;

    margin-bottom: 30rpx;
  }
  .detail-content {
    font-size: 32rpx;
    border: 1rpx solid #6eadcd;
    line-height: 42rpx;
    text-align: justify;
    padding: 20rpx 10rpx;
  }
  .border-green {
    background-color: #d5fada;
    border: 8rpx solid #8bc56e;
  }
  .border-blue {
    background-color: #d1efff;
    border: 8rpx solid #83c6e8;
  }
}
.flex-end {
  align-items: flex-end;
}
.flex-start {
  align-items: flex-start;
}
</style>

<template>
  <view class="run">
    <TipCard :tip-card="tipCard"></TipCard>
    <button class="btn">准备好了，开始游泳</button>
  </view>
</template>

<script setup>
import TipCard from './TipCard.vue';
import { reactive } from 'vue';
const tipCard = reactive([
  {
    iconSrc: 'https://s1.ax1x.com/2022/03/10/bhXy4A.png',
    iconName: '运动小贴士',
    text: '建议下水前先淋浴或做一些热身运动，可以缓慢下水让身体适应后再游泳。不要在太冷的水中游泳。',
    imgSrc: 'https://s1.ax1x.com/2022/03/10/b4Pb8g.png',
  },
  {
    iconSrc: 'https://s1.ax1x.com/2022/03/10/b4PCjA.png',
    iconName: '安全小贴士',
    text: '游泳时带上耳塞，可以有效防止中耳炎游泳结束后记得淋浴，一定要洗澡、头发最好也清洗一下。',
    imgSrc: 'https://s1.ax1x.com/2022/03/10/b4Pm9g.png',
  },
]);
</script>

<style scoped lang="less">
.run {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 35rpx;
  margin-bottom: 110rpx;
  .btn {
    font-size: 32rpx;
    color: aliceblue;
    width: 675rpx;
    height: 80rpx;
    border-radius: 23rpx;
    background-color: #83c6e8;
  }
}
</style>

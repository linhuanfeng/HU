<template>
  <view class="run">
    <TipCard :tip-card="tipCard"></TipCard>
    <button class="btn">准备好了，开始行走</button>
  </view>
</template>

<script setup>
import TipCard from './TipCard.vue';
import { reactive } from 'vue';
const tipCard = reactive([
  {
    iconSrc: 'https://s1.ax1x.com/2022/03/10/bhXy4A.png',
    iconName: '运动小贴士',
    text: '正确的行走，胸廓自然旋转，手臂自然摆动；行走过程中足部的滚动模式正确。',
    imgSrc: 'https://s1.ax1x.com/2022/03/10/b4kFBR.png',
  },
  {
    iconSrc: 'https://s1.ax1x.com/2022/03/10/b4PCjA.png',
    iconName: '安全小贴士',
    text: '许多运动医学专家一致认为，长期科学地坚持行走锻炼，可以减少体内多余的脂肪，使身材变得匀称和健美。',
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

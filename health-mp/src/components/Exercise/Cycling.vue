<template>
  <view class="run">
    <TipCard :tip-card="tipCard"></TipCard>
    <button class="btn">准备好了，开始骑行</button>
  </view>
</template>

<script setup>
import TipCard from './TipCard.vue';
import { reactive } from 'vue';
const tipCard = reactive([
  {
    iconSrc: 'https://s1.ax1x.com/2022/03/10/bhXy4A.png',
    iconName: '运动小贴士',
    text: '注意调整好骑乘姿势，调整适合的坐垫高度，正确的姿势是会让压力分散落在手握、脚踏及座垫上。',
    imgSrc: 'https://s1.ax1x.com/2022/03/10/b4kQud.png',
  },
  {
    iconSrc: 'https://s1.ax1x.com/2022/03/10/b4PCjA.png',
    iconName: '安全小贴士',
    text: '骑行后的拉伸非常有必要！特别是在高强度骑行之后，进行拉伸可以防止僵硬和疲劳。',
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

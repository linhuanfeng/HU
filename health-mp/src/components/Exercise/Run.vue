<template>
  <view class="run">
    <TipCard :tip-card="tipCard"></TipCard>
    <button class="btn">准备好了，开始跑步</button>
  </view>
</template>

<script setup>
import TipCard from './TipCard.vue';
import { reactive } from 'vue';
const tipCard = reactive([
  {
    iconSrc: 'https://s1.ax1x.com/2022/03/10/bhXy4A.png',
    iconName: '运动小贴士',
    text: '跑前慢跑10分钟热身：热身是为了让身体准备好，大概10分钟即可，要让身体微微出汗。',
    imgSrc: 'https://s1.ax1x.com/2022/03/10/bh53Rg.png',
  },
  {
    iconSrc: 'https://s1.ax1x.com/2022/03/10/b4PCjA.png',
    iconName: '安全小贴士',
    text: '如果你无法屏住呼吸、开始喘息或咳嗽，或感到胸痛，那么运动太剧烈了，应该立即停止跑步。',
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

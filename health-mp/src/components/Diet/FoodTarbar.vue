<template>
  <view class="tabbar">
    <view v-for="(item, index) in tabbars" :key="index" class="item" @click="toThePage({ index, item })">
      <image class="img" :src="item.isSelected ? item.selectedIconPath : item.iconPath" mode="aspectFill"></image>
      <view class="text-selected">{{ item.text }}</view>
    </view>
  </view>
</template>

<script setup>
import { reactive, toRefs, onMounted } from 'vue';
import { usefoodtabbarstore } from '@/store/foodtabbar';
import { storeToRefs } from 'pinia';
const foodtabbarstore = usefoodtabbarstore();
const { tabbars } = storeToRefs(foodtabbarstore);

const toThePage = (item) => {
  if (!item.item.isSelected) {
    uni.redirectTo({
      url: tabbars.value[item.index].pagePath,
    });
    foodtabbarstore.changeIsSelected(item);
  }
};
onMounted(() => {
  console.log('@');
});
</script>

<style scoped lang="less">
.tabbar {
  position: fixed;
  bottom: 0rpx;
  z-index: 999999;
  width: 100%;
  height: 120rpx;
  border-top: 1rpx solid gainsboro;
  display: flex;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  background-color: #ffffff;
  .item {
    font-size: 26rpx;

    .img {
      width: 60rpx;
      height: 60rpx;
    }
  }
}
.text-selected {
  color: #00c4f7;
}
</style>

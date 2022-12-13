<template>
  <view class="tabbar">
    <view v-for="(item, index) in tabbars" :key="index" class="item" @click="toThePage({ index, item })">
      <image class="img" :src="item.isSelected ? item.selectedIconPath : item.iconPath" mode="aspectFill"></image>
      <view class="text-selected">{{ item.text }}</view>
    </view>
  </view>
</template>

<script setup>
import { reactive, toRefs } from 'vue';
import { usesporttabbarstore } from '@/store/sporttabbar';
import { storeToRefs } from 'pinia';
const sporttabbarstore = usesporttabbarstore();
const { tabbars } = storeToRefs(sporttabbarstore);

const toThePage = (item) => {
  if (!item.item.isSelected) {
    uni.redirectTo({
      url: tabbars.value[item.index].pagePath,
    });
    sporttabbarstore.changeIsSelected(item);
  }
};
// const data = reactive([
//   {
//     pagePath: 'Exercise',
//     text: '首页',
//     iconPath: '../../static/tabbar/home.png',
//     selectedIconPath: '../../static/tabbar/homeSelected.png',
//     isSelected: true,
//   },
//   {
//     pagePath: 'Sport',
//     text: '运动',
//     iconPath: '../../static/tabbar/home.png',
//     selectedIconPath: '../../static/tabbar/homeSelected.png',
//     isSelected: false,
//   },
//   {
//     pagePath: 'Communication',
//     text: '交流',
//     iconPath: '../../static/tabbar/home.png',
//     selectedIconPath: '../../static/tabbar/homeSelected.png',
//     isSelected: false,
//   },
// ]);
// const toThePage = (index) => {
//   uni.navigateTo({
//     url: data[index].pagePath,
//   });
//   data.forEach((element) => {
//     element.isSelected = false;
//     console.log(element.isSelected);
//   });
//   data[index].isSelected = !data[index].isSelected;
//   console.log(data[index].isSelected);
//   toRefs(data);
// };
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

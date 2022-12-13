<template>
  <view class="content">
    <z-paging ref="paging" v-model="dataList" refresher-enabled="false" @query="queryList">
      <template #top>
        <view class="top">
          <view class="search-box">
            <uni-icons color="#999999" size="24" type="search" style="margin: 5rpx 0 0 10rpx" />
            <view class="search-box-content">
              <uni-easyinput
                v-model="searchValue"
                placeholder="请输入食物名称"
                :input-border="false"
                @confirm="confirm"
              ></uni-easyinput>
            </view>
          </view>
        </view>
        <scroll-view class="scroll-view" scroll-x="true">
          <view class="scroll-view-item">
            <uni-segmented-control
              :current="current"
              :values="items"
              style-type="text"
              active-color="#00C4F7"
              @clickItem="onClickItem"
            ></uni-segmented-control>
          </view>
        </scroll-view>
      </template>
      <view v-for="(item, index) in dataList" :key="index">
        <view class="food-card-list">
          <uni-list-item :title="item.foodNane" :note="item.foodCalorie" :thumb="item.imgSrc" thumb-size="base" />
        </view>
      </view>
    </z-paging>
  </view>
  <FoodTarbar></FoodTarbar>
</template>

<script setup>
import FoodTarbar from '@/components/Diet/FoodTarbar.vue';
import { reactive, ref, onBeforeMount } from 'vue';
/**
 * 分段器设置
 */
const items = ref(['主食类', '肉蛋类', '大豆及制品', '蔬菜菌藻类', '奶类及饮料', '水果类']);
let current = ref(0);
const onClickItem = (index) => {
  if (current.value != index.currentIndex) {
    console.log(index.currentIndex);
    current.value = index.currentIndex;
    paging.value.reload();
  }
};
/**
 *  搜索
 */
let searchValue = ref();
const confirm = () => {
  console.log(searchValue.value);
};
/*
 **数据
 */
let dataList = ref([]);
const paging = ref(null);
const queryList = (pageNo, pageSize) => {
  console.log(pageNo, pageSize);
  if (current.value == 0) {
    setTimeout(() => {
      paging.value.complete([
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },

        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
      ]);
    }, 500);
  } else if (current.value == 1) {
    setTimeout(() => {
      paging.value.complete([
        {
          imgSrc: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
          foodNane: '白粥',
          foodCalorie: '46千卡/100克',
        },
      ]);
    }, 500);
  }
};
</script>

<style lang="less" scoped>
.top {
  margin-top: 20rpx;
  .search-box {
    width: 660rpx;
    height: 55rpx;
    display: flex;
    align-items: center;
    border: 1rpx solid #707070;
    border-radius: 40rpx;
    margin: 0 auto;
    .search-box-content {
      width: 650rpx;
    }
  }
}
.scroll-view {
  width: 710rpx;
  height: 68rpx;
  white-space: nowrap;
  margin-bottom: 20rpx;
  .scroll-view-item {
    display: inline-block;
    width: 950rpx;
  }
}
.food-card-list {
  width: 100%;
  height: 130rpx;
  margin-left: 35rpx;
}
</style>

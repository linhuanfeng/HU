<template>
  <view class="diet">
    <!-- 饮食卡片 -->
    <view class="diet-card">
      <view class="diet-card-contnet">
        <view class="diet-card-left-top">经典均匀饮食</view>
        <view class="left">
          <view>饮食摄入</view>
          <view class="number">0</view>
        </view>
        <view class="right">
          <CircleProgress :percent="dietCard.percent" width="300" bg-color="#ffffff" style="font-size: 32rpx">
            <view class="circle">
              <text>还可以吃</text>
              <view class="number">{{ dietCard.number }}</view>
              <text>推荐预算{{ dietCard.number }}</text>
            </view>
          </CircleProgress>
        </view>
      </view>
    </view>
    <view class="tip">为什么记录饮食可以帮助减重</view>
    <view class="record">
      <view class="empty-record">
        <view class="bowl-img">
          <image src="https://s1.ax1x.com/2022/03/26/qdtbNj.png" mode="aspectFit"></image>
        </view>
        <view>还没有记录</view>
        <view>请点击屏幕下方按钮来添加</view>
        <view class="btns">
          <button class="btn" style="background-color: #7cc1e4" @click="toThePage(btnC.src, btnC.index)">
            {{ btnC.name }}
          </button>
          <button class="btn" style="background-color: #ffa8c0">食谱推荐</button>
        </view>
        <view>完整记录三餐分析才精准</view>
      </view>
    </view>
  </view>
  <FoodTarbar></FoodTarbar>
</template>

<script setup>
import FoodTarbar from '@/components/Diet/FoodTarbar.vue';
import CircleProgress from '@/components/Sleep/CircleProgress.vue';
import useCommonExample from '@/hooks/global/useCommon';
import { usefoodtabbarstore } from '@/store/foodtabbar';
import { ref, reactive } from 'vue';
const dietCard = reactive({
  percent: 10,
  number: 1322,
});
// 按钮
const { currentTime } = useCommonExample();
const btnC = computed(() => {
  if (currentTime.value.toString().slice(11, 13) >= '7' && currentTime.toString().slice(11, 13) < '12') {
    return { name: '添加早餐', src: '../../pages_three_module/Diet/Breakfast', index: 1 };
  } else if (currentTime.value.toString().slice(11, 13) >= '12' && currentTime.toString().slice(11, 13) < '18') {
    return { name: '添加午餐', src: '../../pages_three_module/Diet/Dinner', index: 2 };
  } else return { name: '添加晚餐', src: '../../pages_three_module/Diet/Lunch', index: 3 };
});
// 跳转
const foodtabbarstore = usefoodtabbarstore();
const toThePage = (src, index) => {
  foodtabbarstore.changeIsSelected({ index: index });
  uni.redirectTo({
    url: src,
  });
};
</script>

<style lang="less" scoped>
.diet {
  .diet-card {
    display: flex;
    justify-content: center;
    margin-top: 35rpx;
    .diet-card-contnet {
      position: relative;
      width: 688rpx;
      height: 370rpx;
      border: 3rpx solid rgb(216, 211, 211);
      box-shadow: 5rpx 5rpx 10rpx rgb(236, 234, 234);
      border-radius: 36rpx;
      display: flex;
      color: rgb(216, 211, 211);
      .left {
        flex: 3;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        .number {
          font-weight: 600;
          font-size: 45rpx;
          color: black;
        }
      }
      .right {
        flex: 7;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-left: -100rpx;
        .circle {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          .number {
            font-weight: 600;
            font-size: 45rpx;
            color: black;
          }
        }
      }
      .diet-card-left-top {
        position: absolute;
        top: -2rpx;
        left: -2rpx;
        height: 65rpx;
        line-height: 65rpx;
        padding: 0rpx 25rpx;
        font-weight: 600;
        color: rgb(162, 101, 22);
        border-top-left-radius: 36rpx;
        border-bottom-right-radius: 36rpx;
        background-color: antiquewhite;
      }
    }
  }
  .tip {
    width: 668rpx;
    height: 65rpx;
    font-size: 26rpx;
    line-height: 65rpx;
    border-radius: 20rpx;
    padding-left: 25rpx;
    color: rgb(151, 179, 108);
    background-image: linear-gradient(to right, rgb(197, 221, 197), rgb(154, 196, 159));
    margin: 30rpx 0rpx 0rpx 28rpx;
  }
  .record {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-top: 30rpx;
    .empty-record {
      width: 700rpx;
      font-size: 26rpx;
      display: flex;
      flex-direction: column;
      align-items: center;
      color: rgb(199, 197, 197);
      .bowl-img {
        text-align: center;
        margin: 60rpx 0 20rpx 0;
        image {
          width: 200rpx;
          height: 200rpx;
        }
      }
      .btns {
        margin-top: 30rpx;
        display: flex;
        margin-bottom: 20rpx;
        .btn {
          width: 276rpx;
          height: 70rpx;
          line-height: 70rpx;
          border-radius: 40rpx;
          margin: 0 20rpx;
        }
      }
    }
  }
}
</style>

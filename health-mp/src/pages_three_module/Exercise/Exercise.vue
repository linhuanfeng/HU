<template>
  <view class="content">
    <view class="bg"></view>
    <view class="bg-triangle"></view>
    <view class="circle">
      <CircleProgress :percent="sport.percent" width="380" bg-color="rgba(0,0,0,0)" active-color="#90C9E7">
        <view class="text">
          <text>{{ sport.text }}</text>
          <text class="finish-time">{{ sport.finishTime }}</text>
          <text>目标：{{ sport.planTime }}</text>
        </view>
      </CircleProgress>
    </view>
    <view class="sport-data-cards">
      <view v-for="(item, index) in 2" :key="index" class="sport-data-card">
        <view class="number-text">{{ item == 1 ? sport.finishTime : sport.fatBurning }}</view>
        <view>{{ item == 1 ? '时间/小时' : '燃脂/千卡' }}</view>
      </view>
    </view>
    <view class="column">
      <view class="item">
        <image src="../../static/other/sportReport.png" mode="aspectFit"></image>
        <view>运动报告</view>
        <button class="btn">查看</button>
      </view>
      <view class="item">
        <image src="../../static/other/course.png" mode="aspectFit"></image>
        <view>推荐课程</view>
        <button class="btn">更多</button>
      </view>
    </view>
    <view class="courses">
      <view class="course">
        <view class="top">3公里放特莱克跑</view>
        <view class="bottom">27分钟·221千卡</view>
      </view>
    </view>
  </view>
  <ExerciseTarbar></ExerciseTarbar>
</template>

<script setup>
import ExerciseTarbar from '@/components/Exercise/ExerciseTarbar.vue';
import CircleProgress from '../../components/Sleep/CircleProgress.vue';
import { reactive } from 'vue';
const sport = reactive({
  text: '运动总时长',
  finishTime: '3.05',
  fatBurning: '140',
  planTime: '4h',
  percent: 76,
});
</script>

<style lang="less" scoped>
.content {
  .bg {
    background-color: #83c6e8;
    height: 35rpx;
  }
  .bg-triangle {
    position: absolute;
    top: 35rpx;
    width: 0;
    height: 0;
    border-left: 375rpx solid transparent;
    border-right: 375rpx solid transparent;
    border-top: 125rpx solid #83c6e8;
    z-index: -1;
  }
  .circle {
    font-size: 26rpx;
    font-weight: border;
    width: 450rpx;
    height: 450rpx;
    border-radius: 450rpx;
    background-color: #ffffff;
    border: 1rpx solid #ececec;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 88888;
    .text {
      display: flex;
      flex-direction: column;
      align-items: center;
      .finish-time {
        font-size: 50rpx;
        font-weight: bolder;
        color: #eb9c9d;
        margin: 20rpx 0;
      }
    }
  }
  .sport-data-cards {
    display: flex;
    margin-top: 500rpx;
    justify-content: space-around;
    .sport-data-card {
      width: 300rpx;
      height: 120rpx;
      border-radius: 34rpx;
      background-color: #83c6e8;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      font-size: 30rpx;
      color: #ffffff;
      .number-text {
        font-size: 46rpx;
        font-weight: bolder;
      }
    }
  }
  .column {
    display: flex;
    flex-direction: column;
    margin-top: 35rpx;
    .item {
      height: 120rpx;
      display: flex;
      align-items: center;
      font-size: 30rpx;
      font-weight: border;
      border-bottom: 2rpx solid #edeff3;
      image {
        width: 60rpx;
        height: 60rpx;
        margin: 0 40rpx 0 38rpx;
      }
      button::after {
        display: none;
      }
      .btn {
        font-size: 28rpx;
        font-weight: 300;
        height: 43rpx;
        line-height: 43rpx;
        color: #f7ffff;
        border-radius: 17rpx;
        background-color: #83c6e8;
        margin-right: 45rpx;
      }
    }
  }
  .courses {
    display: flex;
    flex-direction: column;
    align-items: center;
    .course {
      margin-top: 10rpx;
      width: 633rpx;
      height: 160rpx;
      background: linear-gradient(to right, #b6def1, #f5f5f6);
      display: flex;
      flex-direction: column;
      .top {
        font-size: 30rpx;
        font-weight: bold;
        color: #f7ffff;
        margin: 21rpx 0 10rpx 40rpx;
      }
      .bottom {
        font-size: 24rpx;
        color: #f7ffff;
        margin: 10rpx 0 10rpx 60rpx;
      }
    }
  }
}
</style>

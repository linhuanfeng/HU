<template>
  <view class="container">
    <!-- <view class="clock">
      {{ data.timeText }}
    </view> -->
    <CircleProgress :percent="data.percent" width="600" bg-color="#313233" style="font-size: 80rpx">
      <text>{{ data.timeText }}</text>
    </CircleProgress>
    <view class="check-sleep-time">
      <image src="../../static/other/sand-clock.png" mode="aspectFit"></image>
      <button class="btn" @click="checkSleepTime">{{ data.btnText }}</button>
    </view>
    <view v-if="!data.isCheckSleepTime">
      <view class="on-off-about">
        <image
          v-if="data.running"
          class="on-off"
          src="../../static/other/son.png"
          mode="aspectFit"
          @click="onReset"
        ></image>
        <image
          v-if="!data.running"
          class="on-off"
          src="../../static/other/moon.png"
          mode="aspectFit"
          @click="onStart"
        ></image>
        <view>{{ data.running == false ? '开启睡眠' : '关闭睡眠' }}</view>
      </view>
      <view class="sleep-report-song">
        <view class="sleep-report-about" @click="toThePage('/pages_three_module/Sleep/SleepReport')">
          <view class="mr-2">睡眠报告</view>
          <image src="../../static/other/report.png"></image>
        </view>
        <view class="sleep-song-about" @click="toThePage('/pages_three_module/Sleep/SongPage')">
          <image src="../../static/other/music.png"></image>
          <view class="ml-2">助眠歌曲</view>
        </view>
      </view>
    </view>
    <!-- 睡眠时长 -->
    <view v-if="data.isCheckSleepTime">
      <view class="sleep-time-about">
        <view class="sleep-time">
          <image src="../../static/other/sleep.png" mode="aspectFit"></image>
          <view class="sleep-time-text">
            <text>就寝时间</text>
            <text>{{ data.startTime ? data.startTime : '早点休息哦' }}</text>
          </view>
        </view>
        <view class="sleep-duration-time">
          <image src="../../static/other/sleep2.png" mode="aspectFit"></image>
          <view class="sleep-time-text">
            <text>目前睡眠时长</text>
            <text>{{ data.sleepTime }}</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import CircleProgress from './CircleProgress.vue';
import { onBeforeMount, reactive, ref, watch, computed } from 'vue';
const DEFAULT_TIME = ref(8 * 60 * 60);
const data = reactive({
  timeText: '',
  running: false,
  time: 0,
  timer: undefined,
  isCheckSleepTime: false,
  btnText: '查看睡眠时长',
  percent: 0,
  startTime: '',
  endTime: '',
  sleepTime: 0,
});
//格式化时间
const formatTime = (time) => {
  const hours = Math.floor((time / 60 / 60) % 24);
  const minutes = Math.floor((time / 60) % 60);
  const seconds = time % 60;
  const hText = `0${hours}`.slice(-2);
  const mText = `0${minutes}`.slice(-2);
  const stext = `0${seconds}`.slice(-2);
  data.timeText = `${hText} : ${mText} : ${stext}`;
};
const setTimer = () => {
  data.timer = setInterval(() => {
    data.time = data.time - 1;
    if (data.time < 0) {
      clearInterval(data.timer);
      data.endTime = new Date().toTimeString().slice(0, 5);
      return;
    }
    console.log(data.time);
    formatTime(data.time);
  }, 1000);
};
const onStart = () => {
  if (!data.timer) {
    data.time = DEFAULT_TIME.value;
    setTimer();
    data.running = true;
    data.startTime = new Date().toTimeString().slice(0, 5);
    console.log(data.startTime);
  }
};
const onReset = () => {
  clearInterval(data.timer);
  data.timer = null;
  data.time = DEFAULT_TIME.value;
  formatTime(data.time);
  data.running = false;
};
// 页面跳转
const toThePage = (src) => {
  clearInterval(data.timer);
  uni.navigateTo({
    url: src,
  });
};
// 查看睡眠时长
const checkSleepTime = () => {
  data.isCheckSleepTime = !data.isCheckSleepTime;
  if (data.btnText == '查看睡眠时长') {
    data.btnText = '隐藏睡觉时长';
  } else {
    data.btnText = '查看睡眠时长';
  }
};
onBeforeMount(() => {
  formatTime(DEFAULT_TIME.value);
  console.log('@', data.percent);
});
// 计算属性百分比、睡眠时长
data.percent = computed(() => {
  return (data.time / DEFAULT_TIME.value) * 100;
});
</script>

<style lang="less" scoped>
.container {
  padding-top: 120rpx;
  box-sizing: border-box;
  width: 100%;
  // height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  background-color: #313233;
  font-size: 35rpx;
  color: #fafafa;
  .clock {
    width: 600rpx;
    height: 600rpx;
    border-radius: 50%;
    border: 6px solid white;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 80rpx;
    font-weight: 700;
    color: white;
  }
  .check-sleep-time {
    display: flex;
    align-items: center;
    background-color: #1c1212;
    border: 1rpx solid #707070;
    border-radius: 20rpx;
    margin-top: 87rpx;
    image {
      margin-left: 23rpx;
      width: 49rpx;
      height: 49rpx;
    }
    button::after {
      border: none;
    }
    .btn {
      background-color: #1c1212;
      margin-right: 10rpx;
      color: #f0f0f3;
    }
  }
}
.on-off-about {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.on-off {
  width: 146rpx;
  height: 146rpx;
  margin-top: 91rpx;
}
.sleep-report-song {
  font-size: 35rpx;
  color: #f5f7fa;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 128rpx;
  margin-bottom: 98rpx;
  .sleep-report-about {
    display: flex;
    align-items: center;
    margin: 0 50rpx;
  }
  .sleep-song-about {
    display: flex;
    align-items: center;
    margin: 0 50rpx;
  }
  image {
    width: 93rpx;
    height: 93rpx;
  }
}
.sleep-time-about {
  display: flex;
  flex-direction: column;
  margin-top: 100rpx;
  .sleep-time {
    display: flex;
    align-items: center;
    image {
      width: 145rpx;
      height: 145rpx;
    }
    .sleep-time-text {
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      margin-left: 73rpx;
    }
  }
  .sleep-duration-time {
    display: flex;
    align-items: center;
    margin-top: 94rpx;
    margin-bottom: 110rpx;
    image {
      width: 145rpx;
      height: 145rpx;
    }
    .sleep-time-text {
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      margin-left: 73rpx;
    }
  }
}
</style>

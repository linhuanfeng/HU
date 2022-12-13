<script setup>
import { ref, reactive } from 'vue';
import ChartArea from '@/components/Sleep/ChartArea.vue';
// import momentMini from 'moment-mini';
/**
 * 分段器
 */
const todayDate = ref(['4月6日', '4月7日', '4月8日']);
const todayIndex = ref(2);
const onClickTodayDate = (e) => {
  if (todayIndex.value != e.currentIndex) {
    todayIndex.value = e.currentIndex;
  }
};
const rateValue = ref(3.5);
/**
 * 图表
 */
const chartData = reactive({
  categories: ['2016', '2017', '2018', '2019', '2020', '2021'],
  series: [
    {
      name: '目标值',
      data: [35, 36, 31, 33, 13, 34],
    },
    {
      name: '完成量',
      data: [18, 27, 21, 24, 6, 28],
    },
  ],
});
</script>

<template>
  <view class="sleep-report">
    <uni-segmented-control
      :current="todayIndex"
      :values="todayDate"
      style-type="text"
      active-color="#FFFEFE"
      class="segmented-control"
      @clickItem="onClickTodayDate"
    ></uni-segmented-control>
    <!-- 分段器内容 -->
    <view class="content">
      <view v-show="todayIndex === 0">
        <view style="height: 500rpx"></view>
      </view>
      <view v-show="todayIndex === 1">
        <view style="height: 500rpx"></view>
      </view>
      <view v-show="todayIndex === 2">
        <view class="sleep-case">
          <view class="sleep-score">{{ 70 }}</view>
          <uni-rate v-model="rateValue" allow-half="true" active-color="#ffc53d" />
          <view style="color: #ffc53d; font-size: 36rpx; margin-top: 24rpx">睡眠质量</view>
          <view class="sleep-time">
            <view class="sleep-time-item">
              <image
                src="../../static/images/sleep/play.png"
                mode="aspectFill"
                style="width: 55rpx; height: 55rpx"
              ></image>
              <view>睡眠时间</view>
              <view>23:00-6:00</view>
            </view>
            <view class="sleep-time-item">
              <image
                src="../../static/images/sleep/play.png"
                mode="aspectFill"
                style="width: 55rpx; height: 55rpx"
              ></image>
              <view>总时长</view>
              <view>7h</view>
            </view>
          </view>
        </view>
      </view>
    </view>
    <!-- 睡眠周期图 -->
    <view style="color: #ffc53d; font-size: 32rpx; margin-top: 34rpx; margin-left: 60rpx">睡眠周期图</view>
    <view class="sleep-chart">
      <view class="sleep-chart-area">
        <view style="color: #ffffff; margin: 25rpx 0 25rpx 25rpx">睡眠时长</view>
        <ChartArea></ChartArea>
      </view>
    </view>
  </view>
</template>

<style scoped lang="less">
.sleep-report {
  display: flex;
  flex-direction: column;
  background-color: #313233;
  .segmented-control {
    width: 356rpx;
    margin-left: 54rpx;
  }
  .sleep-case {
    height: 500rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    .sleep-score {
      font-size: 99rpx;
      color: #ffc53d;
      margin-top: 35rpx;
    }
    .sleep-time {
      display: flex;
      color: #f8f7fb;

      .sleep-time-item {
        text-align: center;
        margin: 22rpx 95rpx;
      }
    }
  }
  .sleep-chart {
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 32rpx;
    margin-bottom: 135rpx;
    .sleep-chart-area {
      width: 630rpx;
      background-color: #acadad;
    }
  }
}
</style>

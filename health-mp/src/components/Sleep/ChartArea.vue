<script setup>
import { ref, reactive, onMounted } from 'vue';
import { onShow } from '@dcloudio/uni-app';
const chartControlDate = reactive(['每周', '每月']);
const chartControlIndex = ref(0);
const customOpts = reactive({
  color: ['#E6B84A'],
});
const onClickChartDate = (e) => {
  if (chartControlIndex.value != e.currentIndex) {
    chartControlIndex.value = e.currentIndex;
    chartMonthData.value = {};
    chartMonthData.value = {
      categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
      series: [
        {
          name: '睡眠时间',
          data: [244, 211],
        },
      ],
    };
  }
};
// 图表
const number = ref(9);
let chartData = ref({
  categories: ['日', '一', '二', '三', '四', '五', '六'],
  series: [
    {
      name: '睡眠时间',
      data: [8, 6, 7, 0, 8, 9, 0],
    },
  ],
});
let chartMonthData = ref({});
const getServerData = () => {
  setTimeout(() => {
    console.log(chartData.value);
  }, 1500);
};
onShow(() => {
  getServerData();
});
</script>

<template>
  <view class="chart">
    <view class="segmented-control">
      <uni-segmented-control
        :current="chartControlIndex"
        :values="chartControlDate"
        style-type="button"
        active-color="#595B5B"
        @clickItem="onClickChartDate"
      ></uni-segmented-control>
    </view>
    <view v-show="chartControlIndex === 0">
      <view class="charts-box">
        <qiun-data-charts type="column" :chart-data="chartData" background="none" :opts="customOpts" />
      </view>
    </view>
    <view v-show="chartControlIndex === 1">
      <view class="charts-box">
        <qiun-data-charts type="column" :chart-data="chartMonthData" background="none" :opts="customOpts" />
      </view>
    </view>
  </view>
</template>

<style scoped lang="less">
.chart {
  display: flex;
  flex-direction: column;
  align-items: center;
  .segmented-control {
    width: 600rpx;
  }
  .charts-box {
    width: 600rpx;
  }
}
</style>

<script setup>
import { onBeforeMount, onMounted, ref } from 'vue';
import { evaluationReport } from '@/api/evaluationlists';
const props = defineProps({
  score: {
    type: Number,
    default: 0,
  },
});
// 返回首页
const toHomePage = () => {
  uni.navigateBack();
};
// 三大建议
const suggests = ref([
  {
    suggestName: '饮食建议',
    suggestSrc: '/pages_three_module/Diet/Diet',
  },
  {
    suggestName: '运动建议',
    suggestSrc: '/pages_three_module/Exercise/Exercise',
  },
  {
    suggestName: '睡眠建议',
    suggestSrc: '/pages_three_module/Sleep/Sleep',
  },
]);
// 三大建议跳转
const toThePage = (src) => {
  uni.navigateTo({
    url: src,
  });
};
// 报告结果
let evReport = ref({});
let reportScore = ref(0);
let reportDescription = ref('');
onBeforeMount(async () => {
  /**
   * 此处做一次判断
   */
  if (props.score == 0) {
    const rScore = uni.getStorageSync('report-score');
    if (rScore) {
      reportScore.value = rScore;
      reportDescription.value = uni.getStorageSync('report-description');
    } else {
      uni.showToast({
        title: '你还没完成健康测试呢',
        icon: 'none',
        duration: 2000,
      });
      return;
    }
  } else {
    const { umsReport } = await evaluationReport(props.score);
    uni.setStorageSync('report-description', umsReport.description);
    // evReport.value = umsReport;
    reportScore.value = props.score;
    reportDescription.value = umsReport.description;
  }
});
</script>

<template>
  <view class="healthy-report">
    <uni-nav-bar :fixed="false" background-color="#ffffff" height="80rpx" :border="false"></uni-nav-bar>
    <view class="custom-navbar">
      <uni-icons
        type="left"
        size="22"
        color="rgba(0,0,0,0.6)"
        class="custom-navbar-icon"
        @click="toHomePage"
      ></uni-icons>
      <view>健康报告</view>
    </view>
    <view class="show-area">
      <view class="show-content">
        <view class="score-text">你的评测得分：{{ reportScore }}</view>
        <view style="font-size: 30rpx" class="mt-3">总分范围（0-80）</view>
        <view class="underline"></view>
        <view>您的测评结果</view>
        <view class="report-text">{{ reportDescription ? reportDescription : '请先进行健康评测' }}</view>
        <view class="underline"></view>
        <button class="btn" style="margin-top: 56rpx">调整攻略</button>
        <view v-for="(suggest, index) in suggests" :key="index" class="btns" @click="toThePage(suggest.suggestSrc)">
          <view class="btn-suggest">{{ suggest.suggestName }}</view>
          <uni-icons
            type="right"
            size="28"
            color="rgba(0,0,0,0.3)"
            class="custom-navbar-icon"
            @click="toHomePage"
          ></uni-icons>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="less" scoped>
.healthy-report {
  background-color: #ffffff;
  .custom-navbar {
    color: #83c6e8;
    font-size: 48rpx;
    display: flex;
    align-items: center;
    .custom-navbar-icon {
      margin-left: 10rpx;
      margin-right: 188rpx;
    }
  }
  .show-area {
    height: 1290rpx;
    margin-top: 32rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: auto;
    padding-top: 22rpx;
    padding-bottom: 20rpx;
    .show-content {
      width: 619rpx;
      height: 1300rpx;
      border: 2rpx solid #707070;
      border-radius: 94rpx;
      display: flex;
      flex-direction: column;
      align-items: center;
      color: #0d2d57;
      .score-text {
        font-size: 48rpx;
        margin-top: 91rpx;
      }
      .underline {
        margin-top: 13rpx;
        margin-bottom: 31rpx;
        height: 1rpx;
        width: 388rpx;
        background-color: rgba(110, 173, 205, 0.5);
      }
      .report-text {
        font-size: 44rpx;
        font-weight: bold;
        color: #ef9a9a;
        margin: 40rpx 0 22rpx 0;
      }
      .btn {
        ::after {
          display: none;
        }
        width: 314rpx;
        height: 86rpx;
        line-height: 86rpx;
        text-align: center;
        color: #595b5b;
        font-size: 36rpx;
        font-weight: bold;
        border-radius: 42rpx;
        background-image: linear-gradient(to bottom, #f9cbd8, #b4e6ff);
        margin: 23rpx 0 20rpx 0;
      }
      .btns {
        font-size: 40rpx;
        display: flex;
        width: 489rpx;
        height: 108rpx;
        border-radius: 40rpx;
        background-image: linear-gradient(to bottom, #f9cbd8, #b4e6ff);
        align-items: center;
        margin: 56rpx 0 33rpx 0;
        .btn-suggest {
          margin: 0 210rpx 0 44rpx;
        }
      }
    }
  }
}
</style>

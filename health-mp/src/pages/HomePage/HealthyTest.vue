<template>
  <view class="form">
    <uni-nav-bar :fixed="false" background-color="#ffffff" height="80rpx" :border="false"></uni-nav-bar>
    <view class="custom-navbar">
      <uni-icons
        type="left"
        size="22"
        color="rgba(0,0,0,0.6)"
        class="custom-navbar-icon"
        @click="toHomePage"
      ></uni-icons>
      <view>健康评测</view>
    </view>
    <form @submit="formSubmit">
      <scroll-view scroll-y="true" style="height: 1420rpx">
        <view v-for="(list, index) in lists" :key="index" class="uni-form-item">
          <view class="uni-form-title">{{ list.id - 1 }}. {{ list.origin }}</view>
          <radio-group :name="list.id - 1" class="radio-group">
            <label class="label">
              <radio color="#83c6e8" value="8" />
              <text>{{ list.choiceA }}</text>
            </label>
            <label class="label">
              <radio color="#83c6e8" value="6" />
              <text>{{ list.choiceB }}</text>
            </label>
            <label class="label">
              <radio color="#83c6e8" value="4" />
              <text>{{ list.choiceC }}</text>
            </label>
            <label class="label">
              <radio color="#83c6e8" value="2" />
              <text>{{ list.choiceD }}</text>
            </label>
          </radio-group>
        </view>
        <button form-type="submit" class="btn">提交问卷</button>
      </scroll-view>
    </form>
  </view>
</template>

<script setup>
import { ref, onBeforeMount } from 'vue';
import { evaluationQuestion } from '@/api/evaluationlists';
/**
 * 获取评测问题
 */
let lists = ref([]);
onBeforeMount(async () => {
  const { page } = await evaluationQuestion();
  lists.value = page.list;
});
// 提交
const formSubmit = (e) => {
  let formData = e.detail.value;
  let score = 0;
  for (let i in formData) {
    if (formData[i]) score += Number(formData[i]);
    else {
      uni.showToast({
        title: '请完成所有问题',
        icon: 'none',
      });
      return;
    }
  }
  uni.setStorageSync('report-score', score);
  uni.redirectTo({
    url: `HealthyReport?score=${score}`,
  });
};
// 返回首页
const toHomePage = () => {
  uni.navigateBack();
};
</script>

<style scoped lang="less">
.custom-navbar {
  color: #83c6e8;
  font-size: 48rpx;
  display: flex;
  align-items: center;
  margin-bottom: 25rpx;
  .custom-navbar-icon {
    margin-left: 10rpx;
    margin-right: 188rpx;
  }
}
.form {
  margin-top: 13rpx;
  .tip {
    text-align: center;
    font-size: 50rpx;
    color: #83c6e8;
    margin: 13rpx 0 12rpx 0;
  }
  .uni-form-item {
    margin: 0 0 23rpx 50rpx;
    &:nth-child(2n + 1) {
      color: #83c6e8;
    }
    &:nth-child(2n) {
      color: #eb9c9d;
    }
    .uni-form-title {
      width: 666rpx;
      font-size: 40rpx;
      font-weight: 600;
    }
    .radio-group {
      display: flex;
      flex-direction: column;
      color: #949699;
      .label {
        margin-top: 15rpx;
      }
    }
  }
  .btn {
    ::after {
      display: none;
    }
    width: 628rpx;
    height: 86rpx;
    line-height: 86rpx;
    margin-top: 44rpx;
    margin-bottom: 80rpx;
    border-radius: 43rpx;
    color: #595b5b;
    background-image: linear-gradient(to bottom, #f9cbd8, #b4e6ff);
  }
}
</style>

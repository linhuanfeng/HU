<template>
  <view class="content">
    <view class="question">
      <view class="question-left"></view>
      <view class="question-right">请问是哪个部位不舒服呢？</view>
    </view>
    <view class="text-show">
      <span>“处于亚健康状态的人，虽没有明确的疾病，</span>
      <span>&nbsp;&nbsp;&nbsp;但却会出现精神活力和适应能力的下降。</span>
      <span>&nbsp;&nbsp;&nbsp;若这状态没得到及时纠正，很容易引起身心疾病！”</span>
    </view>
    <view class="body">
      <view class="body-bg">
        <image src="../../static/other/body.png" mode="aspectFit"></image>
      </view>
      <view class="btns">
        <button
          v-for="(organ, index) in organs"
          :key="index"
          class="btn"
          :class="[organ.organFix]"
          @click="toThePage(organ.organSrc)"
        >
          {{ organ.organName }}
        </button>
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive, onBeforeMount } from 'vue';
import { getAllAutognosisData } from '@/api/autognosis';
/**
 * 身体部位
 */

let organs = reactive([
  {
    catId: 1,
    organName: '头部',
    organFix: 'btn-fix-one',
    organSrc: `/pages_autognosis/organs/Stomach/Stomach?catId=11`,
  },
  {
    catId: 2,
    organName: '眼部',
    organFix: 'btn-fix-two',
    organSrc: `/pages_autognosis/organs/Stomach/Stomach?catId=21`,
  },
  {
    catId: 3,
    organName: '颈部',
    organFix: 'btn-fix-three',
    organSrc: `/pages_autognosis/organs/Stomach/Stomach?catId=31`,
  },
  {
    catId: 4,
    organName: '颈椎',
    organFix: 'btn-fix-four',
    organSrc: `/pages_autognosis/organs/Stomach/Stomach?catId=41`,
  },
  {
    catId: 5,
    organName: '胃部',
    organFix: 'btn-fix-five',
    organSrc: `/pages_autognosis/organs/Stomach/Stomach?catId=51`,
  },
]);

onBeforeMount(async () => {
  const result = await getAllAutognosisData();
  let organsData = result.data;
  for (let i in organs) {
    organs[i].organName = organsData[i].name;
    // 不适症状栏数
    const num = organsData[i].children.length;
    organs[i].num = num;
    let index = (Number(i) + 1) * 10 + 1;
    // console.log(index);
    organs[i].organSrc = `/pages_autognosis/organs/Stomach/Stomach?catId=${index}&num=${num}`;
  }
  console.log('自诊页', organs);
});

//跳转
const toThePage = (src) => {
  if (src) {
    uni.navigateTo({
      url: src,
    });
  }
};
</script>

<style scoped lang="less">
.question {
  display: flex;
  margin-top: 10rpx;
  align-items: center;
  .question-left {
    width: 30rpx;
    height: 47rpx;
    background-color: #0088ff;
    margin-right: 10rpx;
  }
  .question-right {
    font-size: 40rpx;
  }
}
.text-show {
  width: 630rpx;
  height: 140rpx;
  font-size: 26rpx;
  margin: 15rpx 0 0 25rpx;
  display: flex;
  flex-direction: column;
  background-color: #f8f5f5;
  padding: 10rpx 10rpx 10rpx 0;
  justify-content: space-around;
}
.body-bg {
  margin-top: 60rpx;
  image {
    width: 100%;
    height: 1100rpx;
  }
}
.btns {
  button::after {
    border: none;
  }
  .btn {
    width: 250rpx;
    font-size: 46rpx;
    font-weight: lighter;
    color: #00c4f7;
    background: rgba(255, 255, 255, 0.1);
  }
  .btn-fix-one {
    position: absolute;
    top: 415rpx;
    left: 420rpx;
  }
  .btn-fix-two {
    position: absolute;
    top: 570rpx;
    left: 420rpx;
  }
  .btn-fix-three {
    position: absolute;
    top: 730rpx;
    left: 420rpx;
  }
  .btn-fix-four {
    position: absolute;
    top: 885rpx;
    left: 420rpx;
  }
  .btn-fix-five {
    position: absolute;
    top: 1045rpx;
    left: 420rpx;
  }
}
</style>

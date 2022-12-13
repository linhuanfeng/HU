<script setup>
import { reactive, ref } from 'vue';
const customStyle = ref('background-color:rgba(246,210,124,0.15);color:black;border-radius: 7rpx;margin-right:35rpx');
const customFormStyle = reactive({
  // color: 'red',
});
// 标签样式
const customTagStyle = ref({
  fitstTag: true,
  secondTag: true,
  thirdTag: true,
  fourthTag: true,
  initaial: 'background-color:rgba(245,247,250,1);color:black;border-radius: 7rpx;',
  active: 'background-color:#AACCFF;color:black;border-radius: 7rpx;',
});
// 标签信息
const tagsInfo = ref([
  {
    tagName: '+症状描述',
    tagNum: 'fitstTag',
  },
  {
    tagName: '+持续时长',
    tagNum: 'secondTag',
  },
  {
    tagName: '+预防情况',
    tagNum: 'thirdTag',
  },
  {
    tagName: '+是否就医',
    tagNum: 'fourthTag',
  },
]);
// 标签选中
const checked = (tagNum) => {
  customTagStyle.value[tagNum] = !customTagStyle.value[tagNum];
};
//基本表单信息
const baseFormData = reactive({
  introduction: '',
});
// 表单提交
const submitForm = (e) => {
  if (!baseFormData.introduction) {
    uni.showToast({
      title: '请填写症状描述',
      icon: 'none',
    });
    return;
  } else {
    let formInfo = {
      consultQuestion: baseFormData.introduction,
    };
    console.log(formInfo);
    /**
     * 将症状信息提交到后台 ………………
     */
    baseFormData.introduction = '';
    uni.showToast({
      title: '提交成功',
      icon: 'none',
      duration: 2000,
    });
  }
};
</script>

<template>
  <view class="content">
    <!-- 表单 -->
    <uni-forms ref="connectionForm">
      <!-- 医生信息 -->
      <view class="doctor">
        <view>
          <image class="avatar" src="../../static/other/consult.png" mode="aspectFill" />
          <view style="margin-left: 60rpx">张新慧</view>
        </view>
        <view class="doctor-introduction">
          <view class="text-tag">
            <uni-tag text="从业5年" size="small" :custom-style="customStyle"></uni-tag>
            <uni-tag text="均衡营养" size="small" :custom-style="customStyle"></uni-tag>
            <uni-tag text="用餐分析" size="small" :custom-style="customStyle"></uni-tag>
          </view>
          <view class="text-bg">请描述你的所面临的难题或不适症状，需要我提供什么样的帮助。</view>
          <view style="color: #acadad; font-size: 20rpx; font-weight: 600; text-align: right; margin-top: 15rpx">
            H-U医芽为你保障隐私，内容仅医师可见
          </view>
        </view>
      </view>
      <!-- 描述症状 -->
      <view class="consult-area">
        <uni-easyinput
          v-model="baseFormData.introduction"
          :input-border="false"
          type="textarea"
          autoHeight
          placeholder="请详细描述你的症状"
          placeholder-style="color:#ACADAD;font-size:40rpx"
          :styles="customFormStyle"
        />
      </view>
      <view class="tag-area">
        <uni-tag
          v-for="(tag, index) in tagsInfo"
          :key="index"
          :text="tag.tagName"
          size="small"
          :custom-style="customTagStyle[`${tag.tagNum}`] ? customTagStyle.initaial : customTagStyle.active"
          @click="checked(tag.tagNum)"
        ></uni-tag>
      </view>
    </uni-forms>
    <!-- 表单提交 -->
    <button type="primary" class="btn" @click="submitForm">提交</button>
  </view>
</template>

<style scoped lang="less">
.content {
  .doctor {
    height: 285rpx;
    display: flex;
    align-items: center;
    border-bottom: 1rpx solid #707070;
    margin-bottom: 20rpx;
    .avatar {
      width: 158rpx;
      height: 158rpx;
      border-radius: 33rpx;
      box-shadow: 5rpx 5rpx 10rpx rgba(0, 0, 0, 0.4);
      margin-left: 30rpx;
    }
    .doctor-introduction {
      display: flex;
      flex-direction: column;
      margin-bottom: 27rpx;
      .text-tag {
        display: flex;
        margin: 0 0 15rpx 30rpx;
      }
      .text-bg {
        width: 464rpx;
        background-color: rgba(209, 239, 255, 0.2);
        border-radius: 26rpx;
        color: #595b5b;
        font-size: 23rpx;
        margin-left: 20rpx;
        padding: 20rpx 30rpx 20rpx 25rpx;
      }
    }
  }
  .consult-area {
    width: 700rpx;
    height: 200rpx;
    margin-left: 25rpx;
  }
  .tag-area {
    width: 750rpx;
    display: flex;
    margin-top: 45rpx;
    justify-content: space-around;
  }
  .btn {
    width: 645rpx;
    height: 80rpx;
    line-height: 80rpx;
    background-color: rgba(131, 198, 232, 0.6);
    border-radius: 40rpx;
    margin-top: 30rpx;
  }
}
</style>

<script setup>
import { ref, reactive } from 'vue';
import { storeToRefs } from 'pinia';
import { useuserinfostore } from '@/store/userinfo';
/**
 * 健康数据
 */
const userinfostore = useuserinfostore();
const { userinfo } = storeToRefs(userinfostore);
const userInfo = reactive({
  userHeight: userinfo.value.height ? userinfo.value.height + ' cm' : ' ---',
  userWeight: userinfo.value.weight ? userinfo.value.weight + ' kg' : ' ---',
  userBMI: userinfo.value.bmi ? userinfo.value.bmi : ' ---',
  userAvatar: userinfo.value.avatar,
});
/**
 * 健康报告
 */
const items = ref(['饮食', '睡眠', '运动', '健康测试']);
const current = ref(0);
const onClickItem = (e) => {
  if (current.value != e.currentIndex) {
    current.value = e.currentIndex;
  }
};
const toThePage = () => {
  uni.navigateTo({
    url: 'ChangeUserInfo',
  });
};
</script>

<template>
  <scroll-view scroll-y="true" style="height: 1270rpx">
    <view class="user">
      <view class="user-avatar">
        <view class="bg-color">
          <view class="user-avatar-img">
            <image :src="userInfo.userAvatar" mode="aspectFit"></image>
          </view>
        </view>
        <button class="btn" @click="toThePage">完善个人信息</button>
      </view>
      <view class="user-data">
        <view class="user-data-tip">
          <image src="../../static/other/healthydata.png" mode="aspectFit" style="height: 58rpx; width: 58rpx"></image>
          <view>健康数据</view>
        </view>
        <view class="user-data-area">
          <view class="user-data-area-item">体重：{{ userInfo.userWeight }}</view>
          <view class="user-data-area-item">身高：{{ userInfo.userHeight }}</view>
          <view class="user-data-area-item">BMI指数：{{ userInfo.userBMI }}</view>
        </view>
        <view class="underline">
          <view class="underline-center"></view>
        </view>
        <view class="user-report">
          <view class="user-data-tip">
            <image
              src="../../static/other/healthydata.png"
              mode="aspectFit"
              style="height: 58rpx; width: 58rpx"
            ></image>
            <view>健康报告</view>
          </view>
          <uni-segmented-control
            :current="current"
            :values="items"
            style-type="text"
            active-color="#83C6E8"
            @clickItem="onClickItem"
          ></uni-segmented-control>
          <view class="user-report-show">
            <view v-show="current === 0">
              <view v-show="1" class="no-data">
                <image
                  src="../../static/other/nodata.png"
                  mode="aspectFit"
                  style="height: 155rpx; width: 155rpx"
                ></image>
                <view>暂无数据</view>
              </view>
            </view>
            <view v-show="current === 1">
              <view v-show="1" class="no-data">
                <image
                  src="../../static/other/nodata.png"
                  mode="aspectFit"
                  style="height: 155rpx; width: 155rpx"
                ></image>
                <view>暂无数据</view>
              </view>
            </view>
            <view v-show="current === 2">
              <view v-show="1" class="no-data">
                <image
                  src="../../static/other/nodata.png"
                  mode="aspectFit"
                  style="height: 155rpx; width: 155rpx"
                ></image>
                <view>暂无数据</view>
              </view>
            </view>
            <view v-show="current === 3"></view>
          </view>
        </view>
      </view>
    </view>
  </scroll-view>
</template>

<style scoped lang="less">
.user {
  .user-avatar {
    background-color: #83c6e8;
    display: flex;
    flex-direction: column;
    align-items: center;
    .bg-color {
      width: 220rpx;
      height: 220rpx;
      border-radius: 110rpx;
      background-color: #c1e0f0;
      .user-avatar-img {
        position: relative;
        left: 50%;
        top: 11rpx;
        transform: translateX(-50%);
        height: 200rpx;
        border-radius: 100rpx;
        image {
          position: relative;
          left: 50%;
          transform: translateX(-50%);
          width: 200rpx;
          height: 200rpx;
          border-radius: 100rpx;
          border: 1rpx solid white;
        }
      }
    }
    button::after {
      border: none;
    }
    .btn {
      font-size: 38rpx;
      font-weight: bold;
      color: #f2fcff;
      background-color: #83c6e8;
      margin-bottom: 12rpx;
    }
  }
  .user-data {
    height: 300rpx;
    .user-data-tip {
      display: flex;
      font-size: 34rpx;
      font-weight: bold;
      color: #303232;
      margin: 53rpx 0 0 36rpx;
    }
    .user-data-area {
      display: flex;
      justify-content: space-around;
      margin-top: 21rpx;
      .user-data-area-item {
        font-size: 30rpx;
        font-weight: bold;
        height: 97rpx;
        line-height: 97rpx;
        border-radius: 32rpx;
        box-shadow: 5rpx 5rpx 5rpx rgba(0, 0, 0, 0.15);
        padding: 0 15rpx 0 19rpx;
      }
    }
    .underline {
      display: flex;
      justify-content: center;
      margin-top: 53rpx;
      .underline-center {
        height: 2rpx;
        width: 690rpx;
        background-color: rgba(0, 0, 0, 0.15);
      }
    }
  }
  .user-report {
    .user-report-show {
      .no-data {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-top: 239rpx;
        margin-bottom: 250rpx;
      }
    }
  }
}
</style>

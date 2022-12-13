<template>
  <view class="content">
    <!-- 搜索 -->
    <view class="search-box">
      <view class="search-icon">
        <uni-icons type="search" size="40"></uni-icons>
      </view>
      <view class="search-input">
        <uni-easyinput
          v-model="value"
          placeholder="输入症状、疾病、查询相关咨询"
          :input-border="false"
          @input="getValue"
          @confirm="searchValue"
        ></uni-easyinput>
      </view>
    </view>
    <!-- 轮播图 -->
    <view class="swiper">
      <uni-swiper-dot
        :info="data.info"
        :current="data.current"
        field="content"
        :mode="data.mode"
        :dots-styles="data.dotsStyle"
      >
        <swiper class="swiper-box" :autoplay="true" interval="2300" @change="change">
          <swiper-item v-for="(item, index) in data.info" :key="index">
            <view class="swiper-item">
              <image :src="item.url" mode="aspectFill"></image>
            </view>
          </swiper-item>
        </swiper>
      </uni-swiper-dot>
    </view>
    <!-- 健康知识小视频，下面只是演示内容 -->
    <view class="healthy-video">
      <view class="healthy-video-text">健康知识小视频</view>
      <view class="scroll-view_H">
        <scroll-view :scroll-x="true" :enable-flex="true">
          <view v-for="(video, index) in videos" :key="index" class="scroll-view-item_H">
            <video
              :src="video.src"
              enable-danmu
              danmu-btn
              controls
              object-fit="fill"
              @error="videoErrorCallback"
            ></video>
          </view>
        </scroll-view>
      </view>
    </view>
    <!-- 周边推送 -->
    <view class="peripheral-push">
      <view class="peripheral-push-text">周边推送</view>
    </view>
  </view>
</template>

<script setup>
import { reactive, ref } from 'vue';
/**
 * 搜索
 */
const value = ref('');
const getValue = () => {
  console.log(value.value);
};
const searchValue = () => {
  uni.showToast({
    title: value.value,
    icon: 'success',
  });
};
/**
 * 轮播图
 */
const data = reactive({
  info: [
    {
      url: 'https://img9.51tietu.net/pic/2019-091200/vgkpidei2tjvgkpidei2tj.jpg',
    },
    {
      url: 'https://img9.51tietu.net/pic/2019-091200/euzekmi5m23euzekmi5m23.jpg',
    },
    {
      url: 'https://img9.51tietu.net/pic/2019-091200/143tt0ta4sr143tt0ta4sr.jpg',
    },
  ],
  current: 0,
  mode: 'dot',
  dotsStyle: {
    backgroundColor: 'rgba(83, 200, 249,0.3)',
    border: '1px rgba(83, 200, 249,0.3) solid',
    color: '#fff',
    selectedBackgroundColor: 'rgba(83, 200, 249,0.9)',
    selectedBorder: '1px rgba(83, 200, 249,0.9) solid',
  },
});
const change = (e) => {
  data.current = e.detail.current;
};
/**
 * 视频
 */
const videos = reactive([
  {
    src: 'http://s.39.net/video_Qimiaozhongyi/SZY/qinlin/ganyanzheng/gyzchang01.mp4',
  },
  // {
  //   src: 'http://s.39.net/video_Qimiaozhongyi/SZY/qinlin/ganyanzheng/gyzchang01.mp4',
  // },
  // {
  //   src: 'http://s.39.net/video_Qimiaozhongyi/SZY/luoyujun/shengzhangfayu/szfychang01.mp4',
  // },
]);
</script>

<style scoped lang="less">
.content {
  .search-box {
    width: 675rpx;
    height: 105rpx;
    border-radius: 23rpx;
    border: 4rpx solid #707070;
    margin: 43rpx auto 0 auto;
    display: flex;
    align-items: center;
    .search-icon {
      margin-left: 38rpx;
      margin-top: 6rpx;
    }
  }
  .swiper {
    width: 90%;
    height: 292rpx;
    margin: 63rpx auto 0 auto;
    text-align: center;
    // border-radius: 23rpx;
    .swiper-item {
      image {
        height: 292rpx;
      }
    }
  }
  .healthy-video {
    .healthy-video-text {
      font-size: 30rpx;
      font-weight: bold;
      color: #303232;
      margin: 60rpx 0 0 37rpx;
    }
    .scroll-view_H {
      white-space: nowrap;
      width: 95%;
      margin: 15rpx auto 0 auto;
      .scroll-view-item_H {
        display: inline-block;
        text-align: center;
        margin: 10rpx 20rpx;
        border-radius: 20rpx;
        video {
          width: 303rpx;
          height: 171rpx;
          border-radius: 20rpx;
        }
      }
    }
  }
  .peripheral-push {
    .peripheral-push-text {
      font-size: 30rpx;
      font-weight: bold;
      color: #303232;
      margin: 60rpx 0 0 37rpx;
    }
  }
}
</style>

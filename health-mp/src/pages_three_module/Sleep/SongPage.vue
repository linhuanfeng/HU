<template>
  <view class="content">
    <!-- 音乐卡片 -->
    <view class="song-card">
      <view class="song-card-swiper">
        <VearCarousel :img-list="imgList" url-key="url"></VearCarousel>
        <view class="line"></view>
      </view>
      <view class="song-name">{{ songCards[songindex].songName }}</view>
      <view class="song-btns">
        <view>
          <image src="../../static/images/sleep/random.png" mode="aspectFill"></image>
        </view>
        <view @click="playSong">
          <image src="../../static/images/sleep/play.png" mode="aspectFill"></image>
        </view>
        <view @click="nextSong">
          <image src="../../static/images/sleep/next.png" mode="aspectFill"></image>
        </view>
      </view>
    </view>
    <!-- 选择音乐 -->
    <view class="select-btn">
      <image src="../../static/other/video.png" mode="aspectFill"></image>
      <button class="btn" @click="showDrawer">选择列表</button>
    </view>
    <!-- 歌曲选择区 -->
    <uni-popup ref="showRight" type="bottom" animation="true">
      <view class="popup-area">
        <scroll-view style="height: 100%" scroll-y="true" @click="closeDrawer">
          <SongCard
            v-for="(songCard, index) in songCards"
            :key="index"
            :song-card="songCard"
            :song-index="index"
            @clickitem="getSong"
          ></SongCard>
        </scroll-view>
      </view>
    </uni-popup>
  </view>
</template>

<script setup>
import VearCarousel from '../../components/Sleep/VearCarousel.vue';
import SongCard from '../../components/Sleep/SongCard.vue';
import { reactive, ref } from 'vue';
// 轮播图片
const imgList = reactive([
  {
    url: 'https://img9.51tietu.net/pic/2019-091200/vgkpidei2tjvgkpidei2tj.jpg',
    id: 0,
  },
  {
    url: 'https://img9.51tietu.net/pic/2019-091200/euzekmi5m23euzekmi5m23.jpg',
    id: 1,
  },
  {
    url: 'https://img9.51tietu.net/pic/2019-091200/143tt0ta4sr143tt0ta4sr.jpg',
    id: 2,
  },
]);
/**
 * 弹出层
 */
const showRight = ref(null); //获取元素节点
const showDrawer = () => {
  showRight.value.open();
};
const closeDrawer = () => {
  showRight.value.close();
};
/**
 * 歌曲卡片信息
 */
const songCards = reactive([
  {
    songName: '所念皆银河',
    songSrc:
      'https://m704.music.126.net/20220630113446/3c817c9edf1d2fcdfa4e8ff028cdcfd3/jdyyaac/obj/w5rDlsOJwrLDjj7CmsOj/14096559985/bd69/06e9/b04c/c0ba7a158300d152f179a4e6e5049135.m4a?authSecret=00000181b2960c3b1b380aaba5f923ec',
    singerName: '房东的猫',
  },
  {
    songName: 'Light',
    songSrc:
      'https://m704.music.126.net/20220630113655/786e210997a11b82737b813845cef587/jdyyaac/obj/w5rDlsOJwrLDjj7CmsOj/8184165242/1f42/961e/6f42/d922e12ea2f3838a43bacd08d46fe2ef.m4a?authSecret=00000181b298039a11bf0aaba51d1c7a',
    singerName: 'Endless Melancholy',
  },
]);
const songindex = ref(0);
const getSong = (index) => {
  songindex.value = index;
  console.log('听这首歌: ', songCards[index].songName);
  innerAudioContext.stop();
};
/**
 * 音乐播放业务
 */
const isPlay = ref(false); //是否播放
const innerAudioContext = uni.createInnerAudioContext();
const playSong = () => {
  if (!isPlay.value) {
    innerAudioContext.src = songCards[songindex.value].songSrc;
    innerAudioContext.play();
    isPlay.value = true;
  } else {
    innerAudioContext.pause();
    isPlay.value = false;
  }
  innerAudioContext.onError((res) => {
    console.log(res.errMsg);
    console.log(res.errCode);
  });
};
const nextSong = () => {
  if (isPlay.value) {
    innerAudioContext.stop();
    songindex.value++;
    if (songindex.value >= songCards.length) {
      songindex.value = 0;
    }
    innerAudioContext.src = songCards[songindex.value].songSrc;
    innerAudioContext.play();
  } else {
    songindex.value++;
    if (songindex.value >= songCards.length) {
      songindex.value = 0;
    }
    innerAudioContext.src = songCards[songindex.value].songSrc;
  }
};
</script>

<style scoped lang="less">
.content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #313233;
  .song-card {
    width: 600rpx;
    height: 862rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #869695;
    margin-top: 169rpx;
    border: 1rpx solid #707070;
    border-radius: 33rpx;

    .song-card-swiper {
      margin-top: 100rpx;
      .line {
        margin-top: 100rpx;
        border: 1rpx solid #57684e;
      }
    }
    .song-name {
      font-size: 57rpx;
      color: #f5f7fa;
      margin-top: 30rpx;
    }
    .song-btns {
      display: flex;
      margin-top: 23rpx;
      image {
        width: 74rpx;
        height: 74rpx;
        margin: 7rpx 50rpx;
      }
    }
  }
  .select-btn {
    height: 113rpx;
    background-color: #869695;
    border-radius: 24rpx;
    border: 1rpx solid #707070;
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin-top: 123rpx;
    margin-bottom: 193rpx;
    image {
      margin-left: 25rpx;
      width: 89rpx;
      height: 89rpx;
    }
    button::after {
      border: none;
    }
    .btn {
      font-size: 40rpx;
      font-weight: bold;
      color: #ecedf9;
      background-color: #869695;
    }
  }
}
.select-song-lists {
  background-color: #313233;
}
.popup-area {
  width: 100%;
  height: 800rpx;
  background-color: #313233;
}
</style>

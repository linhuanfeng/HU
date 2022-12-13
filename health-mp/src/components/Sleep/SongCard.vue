<template>
  <view class="content" @click="_onClick(songIndex)">
    <view class="song-index">{{ songIndex + 1 }}</view>
    <view class="song-info">
      <view class="song-name">{{ songCard.songName }}</view>
      <view class="singer-name">{{ songCard.singerName }}</view>
    </view>
  </view>
</template>

<script setup>
const props = defineProps({
  songCard: {
    type: Object,
    required: true,
  },
  songIndex: {
    type: Number,
    required: true,
  },
});
const emit = defineEmits(['clickitem'])
const _onClick = (index)=>{
  emit('clickitem',index)
}
</script>

<style scoped lang="less">
.content {
  height: 110rpx;
  display: flex;
  align-items: center;
  border-bottom: 1rpx solid #869695;
  margin-top: 3rpx;
  .song-index {
    font-size: 49rpx;
    color: #b1d6d4;
    margin: 20rpx 60rpx 20rpx 60rpx;
  }
  .song-info {
    display: flex;
    flex-direction: column;
    .song-name {
      font-size: 44rpx;
      font-weight: bold;
      color: #fdfdfd;
    }
    .singer-name {
      font-size: 28rpx;
      color: #869695;
    }
  }
}
</style>

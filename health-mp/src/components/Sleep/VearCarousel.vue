<template>
  <swiper class="image-container" previous-margin="45rpx" next-margin="45rpx" circular autoplay @change="swiperChange">
    <swiper-item
      v-for="(item, index) in imgList"
      :key="item[urlKey]"
      :class="currentIndex == index ? 'swiper-item' : 'swiper-item-side'"
    >
      <image
        :class="currentIndex == index ? 'item-img' : 'item-img-side'"
        :src="item[urlKey]"
        :style="dontFirstAnimation ? 'animation: none;' : ''"
        mode="aspectFill"
        @click="clickImg(item)"
      ></image>
    </swiper-item>
  </swiper>
</template>
<script>
export default {
  name: 'VearCarousel',
  props: {
    imgList: {
      type: Array,
      default() {
        return [];
      },
    },
    urlKey: {
      type: String,
      default() {
        return '';
      },
    },
  },
  emits: ['selected'],
  data() {
    return {
      currentIndex: 0,
      dontFirstAnimation: true,
    };
  },
  methods: {
    swiperChange(e) {
      this.dontFirstAnimation = false;
      this.currentIndex = e.detail.current;
    },
    clickImg(item) {
      this.$emit('selected', item, this.currentIndex);
    },
  },
};
</script>
<style scoped>
.image-container {
  width: 600rpx;
  height: 400rpx;
}

.item-img {
  width: 500rpx;
  height: 400rpx;
  border-radius: 14rpx;
  animation: to-big 0.3s;
}

.swiper-item {
  width: 500rpx;
  height: 400rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}
.item-img-side {
  width: 500rpx;
  height: 350rpx;
  border-radius: 14rpx;
  animation: to-mini 0.3s;
}

.swiper-item-side {
  width: 500rpx;
  height: 350rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}
@keyframes to-mini {
  from {
    height: 400rpx;
  }
  to {
    height: 350rpx;
  }
}
@keyframes to-big {
  from {
    height: 350rpx;
  }
  to {
    height: 400rpx;
  }
}
</style>

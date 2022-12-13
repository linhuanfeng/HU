<template>
  <view class="content">
    <view class="fields">
      <view
        v-for="(item, index) in items"
        :key="index"
        class="field"
        :class="[current == index ? 'weighter' : '']"
        @click="_onClick(index)"
      >
        {{ item }}
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue';
const props = defineProps({
  items: {
    type: Array,
    required: true,
  },
  current: {
    type: Number,
    required: true,
  },
});
const emit = defineEmits(['clickitem']);
const _onClick = (index) => {
  console.log('&', props.current);
  console.log('&&', index);
  //传递出去是为了切换选项卡
  emit('clickitem', index);
};
</script>

<style lang="less" scoped>
.content {
  width: 100%;
  height: 80rpx;
  display: flex;
  justify-content: center;
  align-items: center;

  .fields {
    width: 90%;
    font-size: 48rpx;
    font-weight: lighter;
    border-bottom: 1rpx solid #edeff3;
    display: flex;
    .field {
      margin-right: 30rpx;
    }
    .weighter {
      border-bottom: 8rpx solid #707070;
      font-weight: bold;
    }
  }
}
</style>

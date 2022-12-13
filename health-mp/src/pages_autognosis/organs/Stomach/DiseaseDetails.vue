<template>
  <view class="content">
    <z-swiper :loop="false" @change="onChange">
      <z-swiper-item>
        <view class="card">
          <view class="cardName">
            {{ detailCardName }}
            <view class="linedown"></view>
          </view>
          <view class="detail-cards">
            <DetailCard v-for="(detailCard, index) in detailCards" :key="index" :detail-card="detailCard"></DetailCard>
          </view>
        </view>
      </z-swiper-item>
      <!-- <z-swiper-item>
        <view class="card"></view>
      </z-swiper-item> -->
    </z-swiper>
  </view>
</template>

<script setup>
import DetailCard from '../../../components/Autognosis/DetailCard.vue';
import { reactive, onBeforeMount } from 'vue';
import { getSymptomData } from '@/api/autognosis';
const props = defineProps({
  catId: {
    type: Number,
    default: 0,
  },
});
const detailCards = reactive([
  {
    title: '相关介绍',
    detailContent: '',
    position: 'flex-start',
    titleBorder: 'border-blue',
  },
  {
    title: '症状',
    detailContent: '',
    position: 'flex-end',
    titleBorder: 'border-green',
  },
  {
    title: '预防方法',
    detailContent: '',
    titleBorder: 'border-blue',
  },
]);
let detailCardName = ref('');
onBeforeMount(async () => {
  console.log(props.catId);
  const { selfdiagonsisSymptom } = await getSymptomData(props.catId);
  detailCardName.value = selfdiagonsisSymptom.name;
  console.log(selfdiagonsisSymptom);
  //修改疾病详情
  detailCards[0].detailContent = selfdiagonsisSymptom.relatedIntroduction;
  detailCards[1].detailContent = selfdiagonsisSymptom.symptom;
  detailCards[2].detailContent = selfdiagonsisSymptom.preventiveMethods;
  console.log(detailCards);
});
/**
 * 详情卡相关信息
 */

//轮播索引
const onChange = (index) => {
  console.log('@', index);
};
</script>

<style lang="less" scoped>
.content {
  .card {
    margin: 0 auto;
    width: 700rpx;
    height: auto;
    border: 1rpx solid #00c4f7;
    background-color: #f2fcff;
    border-radius: 48rpx;
    .cardName {
      font-size: 80rpx;
      font-family: '华文行楷';
      text-align: center;
      margin: 0 auto;
      margin-top: 65rpx;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      .linedown {
        width: 388rpx;
        border-bottom: 5rpx solid #828484;
      }
    }
    .detail-cards {
      width: 680rpx;
      height: auto;
      margin-top: 50rpx;
      margin-bottom: 80rpx;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  }
}
</style>

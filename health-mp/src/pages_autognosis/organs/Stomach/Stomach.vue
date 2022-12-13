<template>
  <view class="content">
    <OrganCard :organ-name="organName" :organ-img-src="organImgSrc" :organ-text="organText"></OrganCard>
    <view class="text">不适症状</view>
    <view class="lists">
      <uni-list-item
        v-for="(symptom, index) in symptomCards"
        :key="index"
        :title="symptom.symptomName"
        :to="symptom.symptomSrc"
        link
        class="list-item"
      ></uni-list-item>
    </view>
  </view>
</template>

<script setup>
import OrganCard from '../../../components/Autognosis/OrganCard.vue';
import { reactive, toRefs, onBeforeMount, ref } from 'vue';
import { getSymptomData } from '@/api/autognosis';
const props = defineProps({
  catId: {
    type: Number,
    default: 0,
  },
  num: {
    type: Number,
    default: 0,
  },
});
/**
 * 不适症状栏信息，后面调用API获取信息覆盖
 */
let symptomCards = reactive([
  // {
  //   symptomName: '胀气',
  //   symptomSrc: '/pages_autognosis/organs/Stomach/Disease',
  // },
]);
onBeforeMount(async () => {
  for (let i = 0; i < props.num; i++) {
    // 11 12 13
    const { selfdiagonsisSymptom } = await getSymptomData(props.catId + i);
    let symptomCard = {
      catId: selfdiagonsisSymptom.catId,
      symptomName: selfdiagonsisSymptom.name,
      symptomSrc: `/pages_autognosis/organs/Stomach/Disease?catId=${props.catId + i}`,
    };
    symptomCards.push(symptomCard);
  }
});
/**
 * 器官卡片信息，后面调用API获取信息覆盖
 */
const organCard = reactive({
  organName: '胃',
  organImgSrc: '../../static/images/organs/stomach.png',
  organText: '胃是人体最大的消化器官，它承担着人体内食物的消化和营养的吸收的功能。',
});
const { organName, organImgSrc, organText } = toRefs(organCard);
</script>

<style lang="less" scoped>
.text {
  font-size: 46rpx;
  margin-top: 400rpx;
  width: 200rpx;
  border-bottom: 10rpx solid #00c4f7;
  margin-left: 35rpx;
  padding-top: 55rpx;
}
.lists {
  margin: 50rpx 25rpx 0 25rpx;
  .list-item {
    font-weight: lighter;
  }
}
</style>

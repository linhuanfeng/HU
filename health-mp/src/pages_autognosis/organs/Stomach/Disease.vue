<template>
  <view class="content">
    <view class="disease-bg">
      <image src="../../../static/other/disease.png" mode="aspectFit"></image>
    </view>
    <view class="btns">
      <button
        v-for="(disease, index) in diseases"
        :key="index"
        class="btn"
        :class="[disease.diseaseFix]"
        @click="toThePage(disease.diseaseSrc)"
      >
        {{ disease.diseaseName }}
      </button>
    </view>
  </view>
</template>

<script setup>
import { reactive, onBeforeMount } from 'vue';
import { getSymptomData } from '@/api/autognosis';
const props = defineProps({
  catId: {
    type: Number,
    default: 0,
  },
});
/**
 * 症状名称通过API获取
 */
const diseases = reactive([
  {
    catId: 0,
    diseaseName: '',
    diseaseFix: 'btn-fix-one',
    diseaseSrc: '',
  },
  {
    catId: 0,
    diseaseName: '',
    diseaseFix: 'btn-fix-two',
    diseaseSrc: `/pages_autognosis/organs/Stomach/DiseaseDetails?catId`,
  },
  {
    catId: 0,
    diseaseName: '',
    diseaseFix: 'btn-fix-three',
    diseaseSrc: '',
  },
  {
    catId: 0,
    diseaseName: '',
    diseaseFix: 'btn-fix-four',
    diseaseSrc: '',
  },
  {
    catId: 0,
    diseaseName: '',
    diseaseFix: 'btn-fix-five',
    diseaseSrc: '',
  },
]);
onBeforeMount(async () => {
  for (let i in diseases) {
    const { selfdiagonsisSymptom } = await getSymptomData(props.catId * 10 + Number(i) + 1);
    if (!selfdiagonsisSymptom) console.log(i);
    else {
      // console.log(selfdiagonsisSymptom);
      diseases[i].catId = selfdiagonsisSymptom.catId + parseFloat(i);
      diseases[i].diseaseSrc = `/pages_autognosis/organs/Stomach/DiseaseDetails?catId=${
        props.catId * 10 + Number(i) + 1
      }`;
      diseases[i].diseaseName = selfdiagonsisSymptom.name;
    }
  }
  console.log('肯能潜在的疾病', diseases);
});
//暂时跳转，不这么写，使用案例中的方式
const toThePage = (src) => {
  uni.navigateTo({
    url: src,
  });
};
</script>

<style lang="less" scoped>
.disease-bg {
  text-align: center;
  position: relative;
  image {
    width: 680rpx;
    height: 1314rpx;
    border-radius: 48rpx;
    border: 1rpx solid #00c4f7;
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
    top: 305rpx;
    left: 178rpx;
  }
  .btn-fix-two {
    position: absolute;
    top: 410rpx;
    left: 458rpx;
  }
  .btn-fix-three {
    position: absolute;
    top: 560rpx;
    left: 68rpx;
  }
  .btn-fix-four {
    position: absolute;
    top: 703rpx;
    left: 440rpx;
  }
  .btn-fix-five {
    position: absolute;
    top: 800rpx;
    left: 150rpx;
  }
}
</style>

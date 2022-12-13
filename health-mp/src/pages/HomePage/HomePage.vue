<template>
  <view class="homepage">
    <!-- 弧形背景 -->
    <view class="arc">
      <view class="bg-arc"></view>
    </view>
    <!-- 用户资料卡片组件 -->
    <view class="user-info">
      <UserInfoCard
        :user-name="userName"
        :user-height="userHeight"
        :user-weight="userWeight"
        :user-b-m-i="userBMI"
        :user-avatar="userAvatar"
      ></UserInfoCard>
    </view>
    <!-- 健康测试、健康报告卡片 -->
    <view class="other-info-card">
      <view v-for="(card, index) in otherInfoCards" :key="index">
        <OtherInfoCard :info-card-object="card"></OtherInfoCard>
      </view>
    </view>
    <!-- 睡觉、饮食、运动卡片 -->
    <view class="activities">
      <view v-for="(card, index) in activityCards" :key="index" class="activity">
        <uni-card :title="card.title" extra="更多>" class="card" @click="toThePage(card.navigateSrc)">
          <text>{{ card.defaultText }}</text>
        </uni-card>
      </view>
    </view>
  </view>
</template>

<script setup>
import UserInfoCard from '../../components/HomePage/UserInfoCard.vue';
import OtherInfoCard from '../../components/HomePage/OtherInfoCard.vue';
import { ref, reactive, toRefs, onBeforeMount, isRef, toRef } from 'vue';
import uniRequest from '@/utils/uniRequest';
import { usesporttabbarstore } from '@/store/sporttabbar';
import { usefoodtabbarstore } from '@/store/foodtabbar';
import { useuserinfostore } from '@/store/userinfo';
import { storeToRefs } from 'pinia ';
const userinfostore = useuserinfostore();
import { onShow } from '@dcloudio/uni-app';
/**
 * 此处为默认用户卡片信息
 * 若用户授权登录，通过store获取用户信息
 */
const { userinfo } = storeToRefs(userinfostore);
const uHeight = !!userinfo.value.height;
console.log(uHeight);
const userInfoCard = reactive({
  userName: userinfo.value.username,
  userHeight: uHeight ? userinfo.value.height + ' cm' : ' ---',
  userWeight: userinfo.value.weight ? userinfo.value.weight + ' kg' : ' ---',
  userBMI: userinfo.value.bmi ? userinfo.value.bmi : ' ---',
  userAvatar: userinfo.value.avatar,
});
const { userName, userHeight, userWeight, userBMI, userAvatar } = toRefs(userInfoCard);
onShow(() => {
  userName.value = userinfo.value.username;
  userHeight.value = userinfo.value.height + ' cm';
  userWeight.value = userinfo.value.weight + ' kg';
  userBMI.value = userinfo.value.bmi + '';
  userAvatar.value = userinfo.value.avatar;
});
/**
 * 其他资料卡片
 */
const otherInfoCards = reactive({
  cardOne: {
    iconSrc: ' ../../static/images/common/stethoscope.png',
    text: '健康测试',
    navigateSrc: 'HealthyTest',
  },
  cardTwo: {
    iconSrc: '../../static/images/common/report.png',
    text: '健康报告',
    navigateSrc: 'HealthyReport',
  },
});

/**
 * 运动、睡眠、饮食卡片信息
 * 通过API获取运动、睡眠、饮食数据覆盖变量 dataText
 */
const activityCards = reactive({
  sleep: {
    title: '睡眠',
    defaultText: '圆圆日月乾坤藏，日照理想月照床。',
    dataText: undefined,
    navigateSrc: '/pages_three_module/Sleep/Sleep',
  },
  diet: {
    title: '饮食',
    defaultText: '均衡饮食是一日三餐弹性搭配的艺术！',
    dataText: undefined,
    navigateSrc: '/pages_three_module/Diet/Diet',
  },
  exercise: {
    title: '运动',
    defaultText: '运动不负有心人，坚持时日必奏效。',
    dataText: undefined,
    navigateSrc: '/pages_three_module/Exercise/Exercise',
  },
});
const sporttabbarstore = usesporttabbarstore();
const foodtabbarstore = usefoodtabbarstore();
//页面跳转
const toThePage = (src) => {
  console.log(src);
  if (src == '/pages_three_module/Diet/Diet') foodtabbarstore.changeIsSelected({ index: 0 });
  else if (src == '/pages_three_module/Exercise/Exercise') sporttabbarstore.changeIsSelected({ index: 0 });
  uni.navigateTo({
    url: src,
  });
};
// 通过接口获取数据post测试无错误
const testReq = (id) => {
  return uniRequest({
    url: `/api/selfdiagonsis/selfdiagonsissymptom/info/${id}`,
    method: 'get',
    // data: {
    //   userId: 1,
    //   planPerDay: 12,
    //   planPerWeek: 7,
    // },
  });
};
let ress = ref({});
onBeforeMount(async () => {
  // const { msg, code } = await testReq();
  // const result = await testReq(21);
  // ress.value = msg;
  // console.log(ress.value);
  // console.log(result);
});
</script>

<style lang="less" scoped>
.homepage {
  overflow: auto;
  .arc {
    position: relative;
    width: 100%;
    height: 278rpx;
    overflow: hidden;
    .bg-arc {
      position: absolute;
      left: -25%;
      width: 150%;
      height: 278rpx;
      background-color: #83c6e8;
      border-bottom-left-radius: 100%;
      border-bottom-right-radius: 100%;
      background: linear-gradient(to top, #89d6ff, #d1efff);
    }
  }
  .user-info {
    position: relative;
    margin-top: -250rpx;
    display: flex;
    justify-content: center;
    align-items: ceter;
  }
  .other-info-card {
    margin-top: 50rpx;
    height: 180rpx;
    display: flex;
    justify-content: space-evenly;
  }
  .activities {
    width: 100%;
    .activity {
      text {
        font-size: 30rpx;
      }
    }
  }
}
</style>

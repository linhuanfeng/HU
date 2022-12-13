<script setup>
import { ref, reactive } from 'vue';
import { updateUserInfo } from '@/api/user';
import { storeToRefs } from 'pinia';
import { useuserinfostore } from '@/store/userinfo';
const userinfostore = useuserinfostore();
const { userinfo } = storeToRefs(userinfostore);
// 头像自定义样式
const imageStyles = ref({
  width: 48,
  height: 48,
  border: {
    radius: '50%',
  },
});
const imageValue = reactive([]);
const select = (e) => {
  console.log('上传头像', e);
};
const avatar = ref(userinfo.value.avatar ? userinfo.value.avatar : '../../static/images/common/avatar.png');
// 昵称
const userName = ref(userinfo.value.username ? userinfo.value.username : '小青在养生');
// 签名
const userBriefIntroduction = ref(userinfo.value.sign);
// 性别
const sexArr = ref(['保密', '男', '女']);
const index = ref(0);
const bindPickerChange = (e) => {
  index.value = e.detail.value;
};
// 身高、体重
const weight = ref(userinfo.value.weight + ' kg');
const height = ref(userinfo.value.height + ' cm');
const removecm = () => {
  height.value = parseFloat(height.value);
};
const removekg = () => {
  weight.value = parseFloat(weight.value);
};
// 提交修改数据
const submit = async () => {
  console.log("userinfo")
  console.log(userinfo)
  console.log(userinfo.value.id)
  let data = {
    id: userinfo.value.id,
    username: userName.value,
    sign: userBriefIntroduction.value,
    // sex: sexArr.value[index.value],
    height: parseFloat(height.value),
    weight: parseFloat(weight.value),
  };
  //更新store里userinfo的数据
  userinfostore.$patch((state) => {
    state.userinfo.username = data.username;
    state.userinfo.sign = data.sign;
    state.userinfo.height = data.height;
    state.userinfo.weight = data.weight;
    state.userinfo.bmi = (data.weight / ((data.height / 100) * (data.height / 100))).toFixed(2);
  });
  uni.showLoading({ title: '修改中', mask: true });
  const { msg } = await updateUserInfo(data);
  if (msg == 'success') {
    setTimeout(function () {
      uni.hideLoading();
      uni.switchTab({
        url: '/pages/HomePage/HomePage',
      });
    }, 500);
  }
};
</script>

<template>
  <view class="list">
    <text style="margin-left: 20rpx">头像</text>
    <uni-file-picker
      v-model="imageValue"
      limit="1"
      :del-icon="false"
      disable-preview
      :image-styles="imageStyles"
      file-mediatype="image"
      style="margin-right: 20rpx"
      return-type="object"
      @select="select"
    >
      <image :src="avatar" mode="aspectFit" style="width: 96rpx; height: 96rpx"></image>
    </uni-file-picker>
  </view>
  <view class="underline"></view>
  <!-- 昵称 -->
  <view class="list">
    <view style="margin-left: 20rpx">昵称</view>
    <input
      v-model="userName"
      :placeholder="userName ? userName : ''"
      style="text-align: right; margin-right: 20rpx; color: rgb(179, 177, 177)"
    />
  </view>
  <view class="underline"></view>
  <!-- 简介 -->
  <view class="list">
    <view style="margin-left: 20rpx">签名</view>
    <input
      v-model="userBriefIntroduction"
      :placeholder="userBriefIntroduction ? userBriefIntroduction : ''"
      style="text-align: right; margin-right: 20rpx; color: rgb(179, 177, 177)"
    />
  </view>
  <view class="underline"></view>
  <!-- 我的实名认证 -->
  <view class="list">
    <view style="margin-left: 20rpx">实名认证</view>
    <uni-icons type="right" size="18" color="rgba(0,0,0,0.5)" style="margin-right: 11rpx"></uni-icons>
  </view>
  <view class="underline"></view>
  <!-- 性别 -->
  <view class="list">
    <view style="margin-left: 20rpx">性别</view>
    <view style="display: flex">
      <picker :value="index" :range="sexArr" @change="bindPickerChange">
        <view>{{ sexArr[index] }}</view>
      </picker>
      <uni-icons type="right" size="18" color="rgba(0,0,0,0.5)" style="margin-right: 11rpx"></uni-icons>
    </view>
  </view>
  <view class="underline"></view>
  <!-- 身高-->
  <view class="list">
    <view style="margin-left: 20rpx">身高</view>
    <input
      v-model="height"
      :placeholder="height ? height : '例如：180cm'"
      style="text-align: right; margin-right: 20rpx; color: rgb(179, 177, 177)"
      @focus="removecm"
    />
  </view>
  <view class="underline"></view>
  <!-- 体重 -->
  <view class="list">
    <view style="margin-left: 20rpx">体重</view>
    <input
      v-model="weight"
      :placeholder="weight ? weight : '例如：65kg'"
      style="text-align: right; margin-right: 20rpx; color: rgb(179, 177, 177)"
      @focus="removekg"
    />
  </view>
  <view class="underline"></view>
  <button class="btn" @click="submit">更新</button>
</template>

<style scoped lang="less">
.list {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 28rpx;
  height: 76rpx;
}
.underline {
  height: 1rpx;
  background-color: rgba(0, 0, 0, 0.15);
  margin-top: 9rpx;
}
.btn {
  margin-top: 39rpx;
  width: 678rpx;
  border-radius: 45rpx;
  background-color: #0289ff;
  color: #ffffff;
}
</style>

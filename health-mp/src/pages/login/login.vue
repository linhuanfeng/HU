<template>
  <view class="content">
    <view class="bg-img">
      <view class="logo">
        <view class="logo-img">
          <image src="../../static/images/login/logo.png" mode="aspectFit"></image>
        </view>
        <view class="logo-text">H-U青养</view>
      </view>
      <view class="login">
        <uni-icons type="weixin" color="#83C6E8" size="40" class="login-icon"></uni-icons>
        <button type="default" class="login-btn" @click="login">开启健康之旅</button>
      </view>
      <image class="bg_ware" src="https://codermoyv.gitee.io/coder-moyv/assets/images/wechat/bg_wave.gif"></image>
    </view>
    <view class="foru">只为更好的你</view>
  </view>
</template>

<script setup>
import { ref } from 'vue';
import { loginAuth, loginId } from '@/api/user';
import { useuserinfostore } from '@/store/userinfo';
const userinfostore = useuserinfostore();

let code = ref('');
let encryptedData = ref('');
let iv = ref('');
/**
 * ……………………
 *  登录业务完成后，跳转到首页。
 */
const login = async () => {
  // 先判断是否授权登录过，如授权过，即非第一次会保存用户id
  const userid = uni.getStorageSync('userid');
  if (userid) {
    const userinfo = await loginId(userid);
    if (userinfo.msg == 'success') {
      await userinfostore.storeuserinfo(userinfo.member);
      uni.switchTab({
        url: '/pages/HomePage/HomePage',
      });
    }
  } else {
    // 获取不到userid说明用户没登录过
    uni.getUserProfile({
      desc: '获取你的昵称,头像,地区以及姓名',
      success: async (res) => {
        encryptedData.value = res.encryptedData;
        iv.value = res.iv;
        await uni.showToast({
          title: '授权成功',
          duration: 1500,
          icon: 'success',
        });
        await uni.login({
          provide: 'weixin',
          success: async (res) => {
            // console.log('登录获取code', res);
            code.value = res.code;
            if (res.errMsg == 'login:ok') {
              const result = await loginAuth({
                code: code.value,
                encryptedData: encryptedData.value,
                iv: iv.value,
              });
              const id = result.member.id;
              uni.setStorageSync('userid', id);
              uni.setStorageSync('user', result.member);
              await userinfostore.storeuserinfo(result.member);
            }
            uni.switchTab({
              url: '/pages/HomePage/HomePage',
            });
            // setTimeout(() => {}, 1000);
          },
        });
      },
      fail: async (err) => {
        console.warn('取消授权,登录失败。');
        await uni.showToast({
          title: '授权已取消',
          duration: 800,
          icon: 'error',
        });
        //  用户拒绝授权也跳转到首页。
        setTimeout(() => {
          uni.switchTab({
            url: '/pages/HomePage/HomePage',
          });
        }, 1000);
      },
    });
  }
};
</script>

<style lang="less" scoped>
.bg-img {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  height: 1050rpx;
  position: relative;
  background: linear-gradient(to top, #89d6ff, #d1efff);
  .logo {
    margin-bottom: -300rpx;
    .logo-img {
      image {
        height: 306rpx;
        width: 305rpx;
      }
    }
    .logo-text {
      font-size: 42rpx;
      color: #ffffff;
      font-weight: bolder;
      text-align: center;
      margin-top: 8rpx;
    }
  }
  .login {
    width: auto;
    height: 115rpx;
    border: 1rpx solid #83c6e8;
    border-radius: 31rpx;
    display: flex;
    justify-content: space-around;
    align-items: center;
    background-color: #ffffff;
    .login-icon {
      padding-left: 20rpx;
    }
    button::after {
      border: none;
    }
    .login-btn {
      font-size: 34rpx;
      font-weight: bolder;
      color: #83c6e8;
      background-color: #ffffff;
      padding-left: 0rpx;
    }
  }
  .bg_ware {
    position: absolute;
    left: 0;
    bottom: 0rpx;
    width: 100%;
    mix-blend-mode: screen;
    height: 120rpx;
  }
}
.foru {
  font-size: 62rpx;
  color: #83c6e8;
  text-align: center;
  position: absolute;
  bottom: 7%;
  left: 50%;
  transform: translateX(-50%);
  text-shadow: 8rpx -8rpx 8rpx rgba(186, 184, 184, 0.8);
}
</style>

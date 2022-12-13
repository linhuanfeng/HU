import { defineStore } from 'pinia';
export const useuserinfostore = defineStore('userinfo', {
  state: () => {
    return {
      // 用户信息
      userinfo: {
        // 首页默认信息
        username: '小青在养生',
        height: 0,
        weight: 0,
        bmi: 0,
        avatar: '../../static/images/common/avatar.png',
      },
    };
  },
  actions: {
    async storeuserinfo(userinfo) {
      this.userinfo = userinfo;
      console.log('store：', this.userinfo);
    },
  },
});

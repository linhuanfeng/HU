// *参考
const userinfo = {
  namespaced: true,
  state: () => ({
    // 用户 token
    token: uni.getStorageSync('token') || '',
    // 用户信息
    userInfo: uni.getStorageSync('user-info') || {},
    // 微信用户唯一标识
    openid: uni.getStorageSync('openid') || '',
  }),
  mutations: {
    /**
     * 保存 token 到 vuex
     */
    setToken(state, token) {
      state.token = token;
      //保存 token到本地存储
      uni.setStorage({
        key: 'token',
        data: state.token,
      });
    },
    /**
     * 保存 用户信息 到 vuex
     */
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
      uni.setStorage({
        key: 'user-info',
        data: state.userInfo,
      });
    },
    /**
     * 保存 openid 到 vuex
     */
    setOpenid(state, openid) {
      state.openid = openid;
      uni.setStorage({
        key: 'openid',
        data: state.openid,
      });
    },
  },
  actions: {},
};

export default userinfo;

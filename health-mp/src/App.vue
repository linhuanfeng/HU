<script>
export default {
  onLaunch: function () {
    console.log('App Launch');
  },
  onShow: function () {
    // console.log('App Show', import.meta.env);
    console.log('App Show');
  },
  onHide: function () {
    console.log('App Hide');
  },
};
</script>

<style lang="scss">
/*每个页面公共css */
/*引入公共样式不能用在main.js中 app不兼容*/
@import '@/common/index.scss';
/* 修改uni卡片组件样式 */
.uni-card .uni-card__header-content-title {
  font-size: 48rpx !important;
  font-weight: bold;
}
.uni-card .uni-card__header-extra-text {
  font-size: 32rpx !important;
  font-weight: bold;
}
.uni-list-item__container {
  padding-left: 6rpx !important;
}
.uni-list-item__content .uni-list-item__content-title {
  font-size: 32rpx !important;
}

.uni-easyinput__content-input {
  font-size: 32rpx !important;
}
</style>

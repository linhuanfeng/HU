<template>
  <view class="tabbar">
    <view class="navigator">
      <view
        v-for="(item, index) in tabBar.list"
        :key="item.pagePath"
        class="navigator-item"
        @click="switchTab(index, item)"
      >
        <img v-if="selectedIndex !== index" :src="item.iconPath" class="icon" />
        <img v-else :src="item.selectedIconPath" class="icon" />
        <text :class="['item-text', { 'text-active': selectedIndex === index }]">{{ item.text }}</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: 'TabBar',
  data() {
    return {
      selectedIndex: 0,
      showselected: false,
      tabBar: {
        list: [
          {
            pagePath: 'Exercise',
            text: '首页',
            iconPath: '../../static/tabbar/home.png',
            selectedIconPath: '../../static/tabbar/homeSelected.png',
            isSelected: true,
          },
          {
            pagePath: 'Sport',
            text: '运动',
            iconPath: '../../static/tabbar/home.png',
            selectedIconPath: '../../static/tabbar/homeSelected.png',
            isSelected: false,
          },
          {
            pagePath: 'Communication',
            text: '交流',
            iconPath: '../../static/tabbar/home.png',
            selectedIconPath: '../../static/tabbar/homeSelected.png',
            isSelected: false,
          },
        ],
      },
    };
  },
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        if (to.meta.isTabBar) {
          // this.__path__ = this.$route.path
          this.showselected = true;
          const index = this.tabBar.list.findIndex((item) => to.meta.pagePath === item.pagePath);
          if (index > -1) {
            this.selectedIndex = index;
          }
        }
      },
    },
  },
  // beforeCreate() {
  // 	this.__path__ = this.$route.path
  // },
  methods: {
    switchTab(index, item) {
      let url = '/' + item.pagePath;
      let pagePath = url;
      const detail = {
        index,
        pagePath,
      };
      if (this.$route.path !== url) {
        // this.__path__ = this.$route.path
        uni.switchTab({
          from: 'tabBar',
          url,
          detail,
        });
      } else {
        // UniServiceJSBridge.emit('onTabItemTap', detail)
      }
      this.selectedIndex = index;
      // this.$emit('switchTab', detail)
    },
  },
};
</script>

<style>
.tabbar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100rpx;
  z-index: 999;
  background: #f5f5f5;
  border-top: 2rpx solid #eee;
}

.navigator {
  display: flex;
  justify-content: space-between;
  width: 85%;
  margin: 0 auto;
  padding: 20rpx;
}

.navigator-item {
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 50rpx;
  height: 100%;
}

.item-text {
  margin-top: 6rpx;
  color: #777e86;
  font-size: 24rpx;
}

.text-active {
  color: #2e92fd !important;
}

.icon {
  width: 20px;
  height: 20px;
}
</style>

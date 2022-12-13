import { defineStore } from 'pinia';
export const usesporttabbarstore = defineStore('sporttabbar', {
  state: () => {
    return {
      tabbars: [
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
          text: '排行',
          iconPath: '../../static/tabbar/home.png',
          selectedIconPath: '../../static/tabbar/homeSelected.png',
          isSelected: false,
        },
      ],
    };
  },
  actions: {
    changeIsSelected(item) {
      for (let i in this.tabbars) {
        this.tabbars[i].isSelected = false;
      }
      this.tabbars[item.index].isSelected = !this.tabbars[item.index].isSelected;
    },
  },
});

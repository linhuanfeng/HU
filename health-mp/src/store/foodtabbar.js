import { defineStore } from 'pinia';
export const usefoodtabbarstore = defineStore('foodtabbar', {
  state: () => {
    return {
      tabbars: [
        {
          pagePath: 'Diet',
          text: '饮食',
          iconPath: '../../static/tabbar/home.png',
          selectedIconPath: '../../static/tabbar/homeSelected.png',
          isSelected: true,
        },
        {
          pagePath: 'Breakfast',
          text: '早餐',
          iconPath: '../../static/tabbar/home.png',
          selectedIconPath: '../../static/tabbar/homeSelected.png',
          isSelected: false,
        },
        {
          pagePath: 'Lunch',
          text: '午餐',
          iconPath: '../../static/tabbar/home.png',
          selectedIconPath: '../../static/tabbar/homeSelected.png',
          isSelected: false,
        },
        {
          pagePath: 'Dinner',
          text: '晚餐',
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

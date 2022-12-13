<template>
  <view class="raw">
    <!-- <uni-icons type="arrowleft" size="25" @tap="backPage"></uni-icons> -->
    <view class="scan_top">
      <!-- 搜索框 -->
      <view class="inputp">
        <view class="inputs">
          <uni-icons type="search" size="20" style="margin-left: 20rpx"></uni-icons>
          <input
            ref="searchinput"
            v-model="keyword"
            type="text"
            :placeholder="placeholder"
            class="scaninput"
            maxlength="24"
            @input="inputChange"
            @search="doSearch(false)"
            @confirm="doSearch(false)"
          />
          <!-- 输入框 :placeholder：未输入时显示内容，v-model：绑定字段，maxlength：设置输入长度，@input：输入框内容有变化时触发事件 @confirm：能让手机输入框的确认变成搜索-->
          <uni-icons
            v-if="isDelShow"
            type="close"
            size="30"
            color="#a7a7a7"
            style="padding-right: 20rpx"
            @click="clear"
          ></uni-icons>
          <view class="scan_weizhi">
            <view class="scan_btn" @tap="doSearch(false)">搜索</view>
            <!-- 执行搜索事件 -->
          </view>
        </view>
      </view>
    </view>
    <!-- 搜索记录 -->
    <view class="record">
      <view class="record_heand">
        <text>搜索历史</text>
        <uni-icons
          type="trash-filled"
          size="30"
          color="#a7a7a7"
          style="padding-right: 20rpx"
          @click="delete_key"
        ></uni-icons>
        <!-- 点击历史记录直接执行搜索 -->
      </view>
      <!-- 搜索历史内容 -->
      <scroll-view v-show="!isShowKeywordList" class="keyword-box" scroll-y>
        <view v-if="oldKeywordList.length > 0" class="keyword-block">
          <!-- v-if:判断是否存在历史记录 -->
          <view class="keyword">
            <view v-for="(oldkeyword, index) in oldKeywordList" :key="index" @tap="doSearch(oldkeyword)">
              {{ oldkeyword }}
            </view>
            <!-- 循环渲染历史记录数组内容 -->
          </view>
        </view>
      </scroll-view>
      <!-- 此处是显示的搜索字段在搜索历史中存在的 -->
      <scroll-view v-show="isShowKeywordList" class="keyword-box" scroll-y>
        <view v-if="oldKeywordList.length > 0" class="keyword-block">
          <!-- v-if:判断是否存在历史记录 -->
          <view class="keyword">
            <view v-for="(oldkeyword, index) in oldKeywordList" :key="index" @tap="doSearch(oldkeyword)">
              {{ oldkeyword }}
            </view>
            <!-- 循环渲染历史记录数组内容 -->
          </view>
        </view>
      </scroll-view>
    </view>
    <!-- 热门搜索 -->
    <view class="record">
      <view class="record_heand">
        <text>热门搜索</text>
        <image :class="'iconfont' + ' icon-yanjing' + forbid" @tap="hotToggle"></image>
        <!-- ：class:判断是否点击，每次点击会改变绑定的字段(也可以是样式)，根据字段不同展示不同的图标	自定义图标在style	 -->
      </view>
      <!-- 搜索热门内容 -->
      <scroll-view v-show="!isShowKeywordList" class="keyword-box" scroll-y>
        <!-- 判断是否展示热门内容 -->
        <view v-if="forbid == ''" class="keyword-block">
          <view class="keyword">
            <view v-for="(hotkeyword, index) in hotKeywordList" :key="index" @tap="doSearch(hotkeyword)">
              {{ hotkeyword }}
            </view>
            <!-- 循环热门消息标签，并添加点击标签搜索事件 -->
          </view>
        </view>
        <view v-else class="yingcang">
          <view>当前搜热门搜索已隐藏</view>
        </view>
      </scroll-view>
    </view>
  </view>
</template>

<script setup>
import { reactive, toRefs, onBeforeMount, onMounted } from 'vue';
import {searchHotWrd,listQuestion} from '@/api/question'
import moment from "moment";
// 设置语言为中文
import 'moment/dist/locale/zh-cn'
moment.locale("zh-cn");

// 子组件使用defineEmits向父组件抛出事件
const emits = defineEmits(['returnList'])//事件数组

//子组件接收的参数
const props = defineProps({
  placeholder: {
    type: String,
    default: '大家都在搜些什么',
  },
});
//子组件自身数据
const data = reactive({
  keyword: '', //关键字
  oldKeywordList: [], //历史记录
  hotKeywordList: [], //热门搜索
  isShowKeywordList: false,
  isDelShow: false, //判断是否出现删除标志
  forbid: '', //热搜显隐标志

  showsearchbtn: false,
  searchval: '',
  target: 0,
  // 帖子列表查询参数
  questionParam:{
    keyword:"",
    areaName:"",
    tag:"",
    from:1,
    size:10
  },
  dynamicCards:{}
});
const { keyword, oldKeywordList, hotKeywordList, isShowKeywordList, isDelShow, forbid } = toRefs(data);

/**
 * 方法
 */
//页面渲染时自动读取本地存储的历史记录
const backPage = () => {
  console.log('@');
};
const loadOldKeyword = () => {
  uni.getStorage({
    key: 'OldKeys',
    success: (res) => {
      let OldKeys = JSON.parse(res.data);
      data.oldKeywordList = OldKeys;
    },
    fail: (err) => {
      console.log('@失败', err);
    },
  });
};
//页面渲染加载热门搜索关键字，后期通过后台获取数据赋值
const loadHotKeyword = async() => {
  const hot=await searchHotWrd()
  data.hotKeywordList = hot.data
};
//监听输入
const inputChange = async (event) => {
  console.log('event', event);
  if (data.keyword.length > 0) {
    data.isDelShow = true;
    console.log('显示取消图标');
  } else {
    data.isDelShow = false;
  }
  //兼容引入组件时传入参数情况
  let keyword = event.detail ? event.detail.value : event;
  if (!keyword) {
    data.isShowKeywordList = false;
    return;
  }
  data.isShowKeywordList = true;
  //替换自己接口
};
//执行搜索
const doSearch = async (keyword) => {
  if (keyword == '') {
    // if (data.keyword.length == 0) {
    //   //判断是否输入内容
    //   uni.showToast({
    //     //没有则弹出提示框，提示未输入
    //     title: '请输入关键字',
    //     icon: 'error',
    //   });
    // } else 
    // if (data.keyword.length >= 1) {
      //若存在输入内容，执行搜索
      console.log('执行了搜索方式一');
      keyword = keyword === false ? data.keyword : keyword;
      data.keyword = keyword;
      saveKeyword(keyword); //保存为历史,执行此事件
      uni.showToast({
        //弹出提示框
        title: keyword,
        icon: 'none',
        duration: 2000,
      });
      // 搜索/查询帖子列表
      data.questionParam.keyword=keyword
      await getQuestions()
    // }
  } else if (keyword != '') {
    //点击热门或历史，文本显示到搜索框
    data.keyword = keyword;
    keyword = keyword === false ? data.keyword : keyword;
    data.keyword = keyword;
    saveKeyword(keyword); //保存为历史,执行此事件
    uni.showToast({
      title: keyword,
      icon: 'none',
      duration: 2000,
    });
    //todo后面要跳转到相关搜索页面!!!!
  }
};

// 查询帖子列表
const getQuestions = async () => {
  uni.showLoading({ title: '查询中', mask: true });
  const res = await listQuestion(data.questionParam);
  if (res.msg == 'success') {
    data.dynamicCards=res.data.questionEntityList;
      // 日期转换
    data.dynamicCards.forEach(element => {
      element.modifiedTime=moment(element.modifiedTime).fromNow()
    });
    setTimeout(function () {
      uni.hideLoading();
    }, 500);
    // 触发父组件，发送结果
    triggerreturnList()
  }
};

  // 触发父组件，发送结果
  const triggerreturnList = () => {
    emits('returnList', data.dynamicCards)//后面是参数
  }

//保存关键字到历史
const saveKeyword = (keyword) => {
  uni.getStorage({
    key: 'OldKeys',
    success: (res) => {
      let OldKeys = JSON.parse(res.data);
      let findIndex = OldKeys.indexOf(keyword);
      if (findIndex == -1) {
        OldKeys.unshift(keyword);
      } else {
        OldKeys.splice(findIndex, 1);
        OldKeys.unshift(keyword);
      }
      //最多十个记录
      OldKeys.length > 10 && OldKeys.pop(); //删除数组尾部元素
      uni.setStorage({
        key: 'OldKeys',
        data: JSON.stringify(OldKeys),
      });
      data.oldKeywordList = OldKeys; //更新历史记录
    },
    fail: (e) => {
      console.error('@saveKeyword失败');
      let OldKeys = [keyword];
      uni.setStorage({
        key: 'OldKeys',
        data: JSON.stringify(OldKeys),
      });
      data.oldKeywordList = OldKeys; //更新历史记录
    },
  });
};
//清空输入框
const clear = () => {
  console.log(data.keyword);
  uni.hideKeyboard();
  data.keyword = '';
  inputChange('');
  console.log('没有' + data.keyword.length + data.keyword);
};
//删除历史记录-全部
const delete_key = () => {
  uni.showModal({
    content: '确定清除历史搜索记录？',
    success: (res) => {
      if (res.confirm) {
        data.oldKeywordList = [];
        uni.removeStorage({
          key: 'OldKeys',
        });
      } else if (res.cancenl) {
        console.log('用户点击取消');
      }
    },
  });
};
//热门开关
const hotToggle = () => {
  data.forbid = data.forbid ? '' : '1'; //两个图标的差别在这个数字"1"上
};
const init = () => {
  loadOldKeyword();
  // loadHotKeyword();
};
onBeforeMount(() => {
  init();
});
onMounted(() => {
  // console.log('@', props.placeholder);
  loadHotKeyword();
});
</script>

<style lang="less" scoped>
.scan_top {
  display: flex;
  align-items: center;
  margin-left: 10rpx;
  .inputp {
    width: 95%;
    margin-left: 10rpx;
    margin-top: -1rpx;
    .inputs {
      background: #f2f2f2;
      border-radius: 20rpx;
      position: relative;
      display: flex;
      align-items: center;

      .scaninput {
        width: 550rpx;
        height: 60rpx;
        padding-left: 10rpx;
        display: flex;
        align-items: center;
      }

      .searchimg {
        width: 1em;
        height: 1em;
        position: absolute;
        //top: 25upx;
        left: 20upx;
      }

      .scanimg {
        width: 1em;
        height: 1em;
        position: absolute;
        //top: 25upx;
        right: 20upx;
      }

      .scan_weizhi {
        display: flex;
        justify-content: flex-end;
        .scan_btn {
          width: 130rpx;
          height: 80rpx;
          color: #fff;
          background-color: #00c4f7;
          border-radius: 30rpx;
          display: flex;
          flex-direction: row;
          align-items: center;
          justify-content: center;
        }
      }
    }
  }
}

.record {
  .record_heand {
    margin-top: 30rpx;
    display: flex;
    text {
      width: 80%;
      height: 50rpx;
      margin-left: 30rpx;
    }
    image {
      width: 10%;
      height: 50rpx;
      margin-right: 50rpx;
      display: flex;
      justify-content: flex-end;
      align-items: center;
      margin-left: 70rpx;
    }
  }
  .record_buttom {
    background-color: red;
  }
}
.keyword-box .keyword-block {
  padding: 10upx 0;
}
.keyword-box .keyword-block .keyword-list-header {
  width: 94%;
  padding: 10upx 3%;
  font-size: 27upx;
  color: #333;
  display: flex;
  justify-content: space-between;
}
.keyword-box .keyword-block .keyword-list-header image {
  width: 40upx;
  height: 40upx;
}
.keyword-box .keyword-block .keyword {
  width: 94%;
  padding: 3px 3%;
  display: flex;
  flex-flow: wrap;
  justify-content: flex-start;
}
.keyword-box .keyword-block .hide-hot-tis {
  display: flex;
  justify-content: center;
  font-size: 28upx;
  color: #6b6b6b;
}
.keyword-box .keyword-block .keyword > view {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 60upx;
  padding: 0 20upx;
  margin: 10upx 20upx 10upx 0;
  height: 60upx;
  font-size: 28upx;
  background-color: rgb(242, 242, 242);
  color: #6b6b6b;
}
.yingcang {
  margin: 50rpx 50rpx;
}
</style>

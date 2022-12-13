<template>
  <view class="content" :class="{Fixed:isFixed}">
    <view class="top">
      <view class="name">青聊站</view>
      <view class="search-box">
        <uni-icons color="#999999" size="35" type="search" />
        <uni-easyinput
          v-model="value"
          placeholder="大家都在搜些什么"
          :input-border="false"
          @focus="toSearchPage"
        ></uni-easyinput>
      </view>
      <view class="icon" @click="openMessage">
        <uni-badge :text="data.Secomments.length" type="error" size="small" absolute="rightTop" :offset="[7, 10]">
          <uni-icons color="#999999" size="45" type="chat" />
        </uni-badge>
      </view>
    </view>
    <!-- 分段器 -->
    <view class="core">
      <Segment :items="items" :current="current" @clickitem="onClickItem"></Segment>
      <!-- 推荐 -->
      <scroll-view v-if="current === 0" scroll-y class="core-one">
        <recommendCard
          v-for="(dynamicCard, index) in data.dynamicCards"
          :key="index"
          :dynamic-card="dynamicCard"
        ></recommendCard>
      </scroll-view>
      <!-- 进站 -->
      <scroll-view v-if="current === 1" scroll-y class="core-two">
        <view>
          <view class="offen">最近逛的站</view>
          <view class="offen-card">
            <PlatformCard
              v-for="(plantformCard, index) in plantformCards"
              :key="index"
              :plantform-card="plantformCard"
            ></PlatformCard>
          </view>
          <view class="offen">关注的站</view>
          <view class="offen-card">
            <PlatformCard
              v-for="(plantformCard, index) in plantformCards"
              :key="index"
              :plantform-card="plantformCard"
            ></PlatformCard>
          </view>
        </view>
      </scroll-view>
      <!-- 咨询 -->
      <scroll-view v-if="current === 2" scroll-y class="core-three">
        <ConsultCard
          v-for="(consultCard, index) in ConsultCards"
          :key="index"
          :consult-card="consultCard"
        ></ConsultCard>
      </scroll-view>
      <!-- 评论 -->
      <scroll-view v-if="current === 3" scroll-y class="core-four">
        <TalkDetail           
        v-for="(dynamicCard, index) in dynamicCards"
          :key="index"
          :dynamic-card="dynamicCard"></TalkDetail>
      </scroll-view>
    </view>
  </view>
  <!-- 消息弹出层 -->
  <view  v-if="data.isShow.check" class="sub">
    <scroll-view  scroll-y >
      <view
v-for="(commt,index) in data.Secomments"
      :key="index"
      class="message"
      >
      用户{{commt.fromId}}{{commt.content}}我，标题是{{commt.questionTitle}} ---- {{commt.createTime}}
      </view>
    </scroll-view>
  <view class="exitSec" @click="unSecondComment">
    返回
  </view>
</view>
</template>

<script setup>
import Segment from '../../components/DoctorTalk/Segment.vue';
import recommendCard from '../../components/DoctorTalk/recommendCard.vue';
import ConsultCard from '../../components/DoctorTalk/ConsultCard.vue';
import PlatformCard from '../../components/DoctorTalk/platformCard.vue';
import { ref, reactive, toRefs,onBeforeMount,onMounted,onUpdated} from 'vue';
import TalkDetail from './TalkDetail.vue'
import {listQuestion,listMessage} from '../../api/question'
import moment from "moment";
// 设置语言为中文
import 'moment/dist/locale/zh-cn'
import { storeToRefs } from 'pinia';
import { useuserinfostore } from '@/store/userinfo'; 
// 缓存中的用户登录状态
const { userinfo } = storeToRefs(useuserinfostore());
moment.locale("zh-cn");
// 遮罩层动态类名
let isFixed=ref(false)
/**
 * 分段器
 */
const data = reactive({
  isShow:{ // 二级评论弹出层是否显示
    "check":false
  },
  Secomments:[], // 消息列表
  current: 0,
  items: ['推荐', '进站', '咨询'],
  code: "",
  keyMap: {
      pid: "parent_comment_id",
      id: "id",
      isAdmin: "admin_comment"
  },
  commentDatas: [
      {
          id: 1,
          admin_comment: 0,
          avatar:
              "https://img2.baidu.com/it/u=134769530,4268043097&fm=253&fmt=auto&app=138&f=JPEG?w=533&h=333",
          create_time: "2020-05-31 23:03:55",
          email: "",
          nickname: "nickname1",
          parent_comment_id: null,
          content: "我是评论1"
      },
      {
          id: 2,
          admin_comment: 0,
          avatar:
              "https://img2.baidu.com/it/u=3133697304,2308274678&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=313",
          create_time: "2020-06-1 23:03:55",
          email: "",
          nickname: "nickname2",
          parent_comment_id: 1,
          content: "我是评论2"
      },
      {
          id: 3,
          admin_comment: 0,
          avatar:
              "https://img2.baidu.com/it/u=3772577665,2044310843&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=727",
          create_time: "2020-05-2 23:03:55",
          email: "",
          nickname: "nickname3",
          parent_comment_id: null,
          content: "我是评论3"
      },
      {
          id: 4,
          admin_comment: 0,
          avatar:
              "https://img0.baidu.com/it/u=3966731730,957573008&fm=253&fmt=auto&app=138&f=JPEG?w=281&h=500",
          create_time: "2020-06-2 23:03:55",
          email: "",
          nickname: "nickname4",
          parent_comment_id: 2,
          content:
              "我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,我是评论,"
      }
  ],
  // 帖子列表查询参数
  questionParam:{
    userId:0,
    keyword:"",
    areaName:"",
    tag:"",
    from:1,
    size:10
  },
  dynamicCards:{},
  // 消息通知
    // 帖子列表查询参数
  messageParam:{
    targetId:0,
    status:1,
    page:1,
    limit:10
  },
  userId:0
});

const { current, items } = toRefs(data);
//分段器切换不同选项内容(由子组件segment调用)
const onClickItem = (index) => {
  console.log('@', index);
  if (data.current != index) {
    data.current = index;
  }
  if(index==0){
    getQuestions();
  }
  // 查看最新的消息
  secCommentList()
};


onBeforeMount(()=>{
      console.log('onBeforeMount--DoctorTalk')
  }); 

onMounted(()=>{
      console.log('onMounted--DoctorTalk')
      data.userId=userinfo.value.id
      // 获取帖子列表
      getQuestions();
      // 获取最新消息
      secCommentList()
  });


onUpdated(()=>{
      console.log('onUpdated--DoctorTalk')
  });

//跳转至搜索页面
const toSearchPage = () => {
  uni.navigateTo({
    url: '/pages/Search/Search',
  });
};

// 打开查看消息遮罩层
const openMessage=()=> {
  console.log("打开查看消息遮罩层")
    // 开启遮罩层
    data.isShow.check=!data.isShow.check;
    // // 开启遮罩层禁止下层页面滚动失效
    isFixed.value=true
    // // 查询参数
    // data.param.parent_id=id
    // 获取最新消息
    secCommentList()
}

// 关闭查看消息遮罩层
const unSecondComment=(event)=> {
      data.isShow.check=!data.isShow.check;
      // 开启遮罩层禁止下层页面滚动失效
      isFixed.value=false
      // 获取最新消息
      secCommentList()
} 

// 查询帖子列表
const getQuestions = async () => {
  data.questionParam.userId=data.userId
  uni.showLoading({ title: '查询中', mask: true });
  const res = await listQuestion(data.questionParam);
  console.log("查询帖子得到：")
  if (res.msg == 'success') {
    data.dynamicCards=res.data.questionEntityList;
      // 日期转换
    data.dynamicCards.forEach(element => {
      element.modifiedTime=moment(element.modifiedTime).fromNow()
    });
    console.log(data.dynamicCards)
    setTimeout(function () {
      uni.hideLoading();
    }, 500);
  }
};
// 查询最新消息
const secCommentList = async () => {
  uni.showLoading({ title: '查询中', mask: true });
  data.messageParam.targetId=data.userId
  const res = await listMessage(data.messageParam);
  console.log(data.messageParam)
  console.log("查询最新消息得到：")
  if (res.msg == 'success') {
    data.Secomments=res.page.list;
      // 日期转换
    data.Secomments.forEach(element => {
      element.createTime=moment(element.createTime).fromNow()
    });
    console.log(data.Secomments)
    setTimeout(function () {
      uni.hideLoading();
    }, 500);
  }
};
/**
 * 咨询卡片信息，后面接口覆盖
 */
const ConsultCards = reactive([
  {
    imgSrc: '../../static/other/consult.png',
    name: '张新慧',
    occupation: '汕头大学',
    field: '饮食营养咨询',
    college: '汕头大学',
    introduction: '本科毕业，就职于汕头新天地营养会所',
    goods: ['均衡营养', '用餐分析'],
    value: '5.0',
    price: '125',
    way: '线上问诊',
  },
]);
/**
 * 不同站信息卡片，后面接口覆盖
 */
const plantformCards = reactive([
  {
    plantformImg: '../../static/other/test.png',
    plantformName: '办公室站',
    plantformAttention: '666',
  },
  {
    plantformImg: '../../static/other/test.png',
    plantformName: '摸鱼站',
    plantformAttention: '6666',
  },
  {
    plantformImg: '../../static/other/test.png',
    plantformName: '摸鱼站',
    plantformAttention: '6666',
  },
  {
    plantformImg: '../../static/other/test.png',
    plantformName: '摸鱼站',
    plantformAttention: '6666',
  },
  {
    plantformImg: '../../static/other/test.png',
    plantformName: '摸鱼站',
    plantformAttention: '6666',
  },
]);
</script>

<style scoped lang="less">
// 弹出层
.Fixed{
  position: fixed;
}
.sub{
  // position: absolute;
  position: fixed;
  float: top;
  top: 60%;
  width: 100vw;
  height: 80vh;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgb(251, 246, 246);
  display: flex;
  .exitSec{
    margin: 40rpx auto;
  }
}
.content {
  height: 2rpx;
}
.top {
  width: 100%;
  // height: auto;
  height: 130rpx;
  display: flex;
  align-items: center;
  justify-content: space-around;
  .name {
    font-size: 56rpx;
    font-weight: normal;
  }
  .search-box {
    width: 400rpx;
    height: 70rpx;
    display: flex;
    align-items: center;
    border: 1rpx solid #707070;
    border-radius: 16rpx;
  }
}
.core {
  // margin-top: 40rpx;
  .core-one {
    height: 80vh;
    // margin-top: 40rpx;
  }
  .core-two {
    // margin-top: 40rpx;
    height: 80vh;
    display: flex;
    flex-direction: column;
    .offen {
      font-size: 32rpx;
      font-weight: lighter;
      margin-left: 45rpx;
      margin-bottom: 20rpx;
    }
    .offen-card {
      display: flex;
      flex-wrap: wrap;
      justify-content: flex-start;
    }
  }
  .core-three {
    height: 80vh;
    // margin-top: 40rpx;
  }
  .core-four {
    height: 80vh;
    // margin-top: 40rpx;
  }
}
</style>

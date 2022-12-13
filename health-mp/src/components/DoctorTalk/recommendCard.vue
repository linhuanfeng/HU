<template>
  <!-- 帖子预览组件 -->
  <view class="box">
    <view class="content">
      <view class="head"  @click="toCreatorInfo">
        <image src="../../static/images/common/avatar.png" mode="aspectFit"></image>
        <view class="name-time">
          <view class="name">{{dynamicCard.creatorName}}    {{dynamicCard.areaName}}</view>
          <view class="time">{{dynamicCard.modifiedTime}}   {{dynamicCard.tag}}</view>
        </view>
      </view>  
      <view class="body"  @click="toTalkDetail">
        <view class="body-text">	
          {{dynamicCard.title}}</view>
        <view class="body-image">
          <image src="../../static/other/test.png" mode="aspectFit"></image>
          <image src="../../static/other/test.png" mode="aspectFit"></image>
          <image src="../../static/other/test.png" mode="aspectFit"></image>
          <image src="../../static/other/test.png" mode="aspectFit"></image>
          <image src="../../static/other/test.png" mode="aspectFit"></image>
          <image src="../../static/other/test.png" mode="aspectFit"></image>
        </view>
      </view>
      <view class="foot">
        <view class="foot-icon" @click="toTalkDetail">
          {{ dynamicCard.commentCount }}
          <uni-icons type="chat" size="20"></uni-icons>
        </view> 
        <view class="foot-icon" @click="thumbQuestion">
          {{ dynamicCard.likeCount }}
          <uni-icons v-if="!dynamicCard.isHeart" type="heart" size="20" color="black"></uni-icons>
          <uni-icons v-if="dynamicCard.isHeart" type="heart-filled" size="20" color="red"></uni-icons>
        </view>  
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive,onMounted,defineProps } from 'vue';
import {questionThumb,questionUnThumb} from '@/api/question'
import { storeToRefs } from 'pinia';
import { useuserinfostore } from '@/store/userinfo'; 
// 缓存中的用户登录状态
const userinfostore = useuserinfostore();
const { userinfo } = storeToRefs(userinfostore);
const props = defineProps({
  dynamicCard: { // 帖子实体
    type: Object,
    required: true,
  },
});

const data=reactive({
  ThumbsEntity:{
    entityId:-1,
    memberId:-1,
    type:-1 // 0表示帖子
  }
})

onMounted(()=>{
          // console.log('onMounted---recommendCard')
          // console.log(props.dynamicCard)
              // 判断是否是点赞
    const sto_k='question:'+props.dynamicCard.id+':isHeart' 
    props.dynamicCard.isHeart=uni.getStorageSync(sto_k)=='true'
  });
  
onUpdated(()=>{
  // console.log('onUpdated---recommendCard')
  const sto_k='question:'+props.dynamicCard.id+':isHeart' 
  props.dynamicCard.isHeart=uni.getStorageSync(sto_k)=='true'
})
     
// 跳转至帖子详情页面   
const toTalkDetail = () => {
  uni.navigateTo({
    url: '/pages/DoctorTalk/TalkDetail?dynamicCardStr='+encodeURIComponent(JSON.stringify(props.dynamicCard)),
  });
};

//跳转至用户详情页面
const toCreatorInfo = () => {
  console.log("跳转至用户详情页面")
  // uni.navigateTo({
  //   url: 'TalkDetail?dynamicCardStr='+encodeURIComponent(JSON.stringify(props.dynamicCard)),
  // });
};

//点赞帖子
const thumbQuestion = async () => {
  console.log("点赞帖子")
  data.ThumbsEntity.memberId=userinfo.value.id
  data.ThumbsEntity.entityId=props.dynamicCard.id // 帖子的id
  data.ThumbsEntity.type=0

  const sto_k='question:'+props.dynamicCard.id+':isHeart'
  if(props.dynamicCard.isHeart==true){
    // 取消点赞
    const res=await questionUnThumb(data.ThumbsEntity)
    uni.removeStorageSync(sto_k)
  }else{
    // 点赞
    const res=await questionThumb(data.ThumbsEntity)
    uni.setStorageSync(sto_k,'true');
  }
  // 图标取反
  props.dynamicCard.isHeart=!props.dynamicCard.isHeart
};

</script>

<style lang="less" scoped>
.highLight{
  color: red;
}
.box {
  border-bottom: 1rpx solid #edeff3;
}
.content {
  margin: 40rpx auto;
  width: 700rpx;
  height: auto;
  display: flex;
  flex-direction: column;
  border-radius: 16.22rpx;
  // box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.2), 0px 2px 1px -1px rgba(0, 0, 0, 0.12), 0px 1px 1px rgba(0, 0, 0, 0.14);
  .head {
    display: flex;
    margin-left: 40rpx;
    margin-top: 10rpx;
    image {
      height: 80rpx;
      width: 80rpx;
    }
    .name-time {
      font-size: 32rpx;
      font-weight: lighter;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      margin-left: 10rpx;
    }
  }
  .body {
    margin-top: 30rpx;
    display: flex;
    flex-direction: column;
    margin-left: 40rpx;
    .body-text {
      font-size: 28rpx;
      font-weight: lighter;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
      text-align: justify;
      margin-bottom: 15rpx;
    }
    .body-image {
      display: flex;
      flex-flow: wrap;
      image {
        width: 160rpx;
        height: 160rpx;
        padding: 10rpx;
      }
    }
  }
  .foot {
    margin-top: 10rpx;
    display: flex;
    justify-content: flex-end;
    font-size: 24rpx;
    font-weight: lighter;
    .foot-icon {
      margin-right: 23rpx;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}
</style>

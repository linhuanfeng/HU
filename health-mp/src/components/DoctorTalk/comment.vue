<template>
  <!-- 二级评论 -->
  <view class="box">
    <view class="content">
      <!-- 用户信息 -->
      <view class="head">
        <image src="../../static/images/common/avatar.png" mode="aspectFit"></image>
        <view class="name-time">
          <view class="name">{{dynamicCard.creatorName}}{{dynamicCard.id}} > 父亲id:{{dynamicCard.parentId}}</view>
          <view class="time">{{dynamicCard.createTime}}</view>
        </view>
      </view>
      <!-- 评论内容 -->
      <view class="body" @click="commentComment(dynamicCard.id)">
        <view class="body-text">{{dynamicCard.content}}</view>
      </view>
      <!-- 评论数据 -->
      <view class="foot">
        <view class="foot-icon" @click="thumbComment(dynamicCard.id)">
          {{ dynamicCard.likeCount }}
          <uni-icons v-if="!dynamicCard.isHeart" type="heart" size="20" color="black"></uni-icons>
          <uni-icons v-if="dynamicCard.isHeart" type="heart-filled" size="20" color="red"></uni-icons>
        </view>
        <view v-if="(userId==dynamicCard.commentator)" class="foot-icon" @click="deleteComm(dynamicCard.id)">
          <uni-icons type="trash" size="20" color="orange"></uni-icons>
        </view>
      </view>
    </view>
  </view>
  <!-- 发布评论弹出层 -->
  <view  v-if="data.isShowCommt.check" class="sub">
    <view>
      <form @submit="SubmitcommentQuestion">
        <view class="uni-form-item uni-column">
          <view class="title">发布你的神评吧</view>
          <input class="uni-input" name="content" placeholder="请输入" />
        </view>
        <view class="uni-btn-v">
          <button form-type="submit">发布</button>
        </view>
      </form>
    </view>
    <view class="exitSec" @click="uncommentQuestion">
      返回
    </view>
  </view>
</template>

<script setup>
import { defineEmits } from 'vue'
import {questionThumb,questionUnThumb} from '@/api/question' 
import {listComment,listSecComment,saveComment,deleteComment} from '../../api/question'
import { ref, reactive } from 'vue';
import { storeToRefs } from 'pinia';
import { useuserinfostore } from '@/store/userinfo';
// 缓存中的用户登录状态
const userinfostore = useuserinfostore();
const { userinfo } = storeToRefs(userinfostore);
const props = defineProps({
  dynamicCard: {
    type: Object,
    required: true,
  },
});

// 遮罩层动态类名
let isFixed=ref(false)
let userId=ref(-1)

const data=reactive({
  isShowCommt:{ // 回复评论弹出层是否显示
    "check":false
  },
  ThumbsEntity:{ // 点赞参数
    entityId:-1,
    memberId:-1,
    type:1 // 1表示评论
  },
  CommentEntity:{ // 保存评论
    parentId:-1,
    grandParentId:-1,
    questionId:-1,
    commentator:-1,
    creatorName:"",
    content:""
  },
  CommentTo:{
    commentId:-1,
    questionId:-1
  }
})

onMounted(()=>{
        // 加载用户id
      userId.value=userinfo.value.id
      // 判断帖子是否是点赞      
      checkIsThumb()
})

onUpdated(()=>{
        // 加载用户id
      userId.value=userinfo.value.id
      // 判断帖子是否是点赞      
      checkIsThumb()
})

// 判断评论是否点赞
const checkIsThumb=(()=>{
      const sto_k_commt='commet:'+props.dynamicCard.id+':isHeart'
      props.dynamicCard.isHeart=uni.getStorageSync(sto_k_commt)=='true'
})

// 点赞二级评论
const thumbComment = async (id) => {
  data.ThumbsEntity.entityId=id // 评论的id
  data.ThumbsEntity.type=1
  data.ThumbsEntity.memberId=userinfo.value.id
  const sto_k_commt='commet:'+id+':isHeart'
  let tag=true;

  if(props.dynamicCard.isHeart==true){
    // 取消点赞
    console.log("取消二级评论点赞:"+id)
    tag=true;
    uni.removeStorageSync(sto_k_commt)
  }else{
    // 点赞
    console.log("点赞二级评论:"+id)
    tag=false;
    uni.setStorageSync(sto_k_commt,'true');
  }
  // 图标取反
  props.dynamicCard.isHeart=!props.dynamicCard.isHeart
  if(tag){
    const res=await questionUnThumb(data.ThumbsEntity)
  }else{
    const res=await questionThumb(data.ThumbsEntity)
  }
};

// 打开回复二级评论
const commentComment = async (parnetId) => {
  console.log("打开回复二级评论")
  // 开启遮罩层
  data.isShowCommt.check=!data.isShowCommt.check;
  // 开启遮罩层禁止下层页面滚动失效
  isFixed.value=true
  data.CommentEntity.parentId=parnetId //
  // 和父评论的一级评论id一样
  data.CommentEntity.grandParentId=props.dynamicCard.grandParentId // 
};

// 关闭评论二级评论
const uncommentQuestion=(e)=> {
  // 关闭遮罩层
  data.isShowCommt.check=!data.isShowCommt.check;
  // 开启遮罩层禁止下层页面滚动失效
  isFixed.value=false
} 

// 提交回复二级评论
const SubmitcommentQuestion = async (e) => {
  // 关闭遮罩层
  data.isShowCommt.check=!data.isShowCommt.check;
  // 开启遮罩层禁止下层页面滚动失效
  isFixed.value=false

  console.log(userinfo.value)
  var formdata = e.detail.value
  
  data.CommentEntity.questionId=props.dynamicCard.questionId // 
  data.CommentEntity.commentator=userinfo.value.id 
  data.CommentEntity.creatorName=userinfo.value.nickname // 评论的内容
  data.CommentEntity.content=formdata.content // 评论的内容
  const res=await saveComment(data.CommentEntity)
};

// 删除评论
const deleteComm=async (id)=>{
  console.log("删除评论"+id)
  data.CommentTo.commentId=id
  data.CommentTo.questionId=props.dynamicCard.questionId
  const res=await deleteComment(data.CommentTo)
  // 触发父组件刷新二级评论
  triggerFlush()
}


// 子组件使用defineEmits向父组件抛出事件
const emits = defineEmits(['add', 'triggerFlush'])//事件数组

// 触发父组件刷新二级评论
const triggerFlush = () => {
  console.log('触发父组件刷新二级评论')
  emits('triggerFlush', '刷新二级评论')//后面是参数
}

</script>

<style lang="less" scoped>
  .box {
    border-bottom: 1rpx solid #edeff3;
  }
  .content {
    background-color: rgb(251, 246, 246);
    margin: 40rpx auto;
    width: 600rpx;
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
  // 弹出层
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
</style>

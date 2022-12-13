<template>
  <!-- 帖子正文 -->
  <view class="box" :class="{Fixed:isFixed}">
    <view class="content topContent">
      <view class="head">
        <image src="../../static/images/common/avatar.png" mode="aspectFit"></image>
        <view class="name-time">
          <view class="name">{{data.dynamicCard.creatorName}}</view>
          <view class="time">修改于 {{data.dynamicCard.modifiedTime}}</view>  
        </view>
      </view>
      <view class="body">
        <view class="body-text">{{data.dynamicCard.title}}</view>
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
        <view class="foot-icon" @click="commentQuestion">
          {{ data.dynamicCard.commentCount }}
          <uni-icons type="chat" size="20"></uni-icons>
        </view>
        <view  class="foot-icon"  @click="thumbQuestion">
          {{ data.dynamicCard.likeCount }}
          <uni-icons v-if="!data.dynamicCard.isHeart" type="heart" size="20" color="black"></uni-icons>
          <uni-icons v-if="data.dynamicCard.isHeart" type="heart-filled" size="20" color="red"></uni-icons>
        </view>                            
      </view>
    </view>
  <!-- 评论列表 -->
  <view
    v-for="(commt, index) in data.comments"
    :key="index"
    class="box2">
    <view class="content">
      <!-- 用户信息 -->
      <view class="head">
        <image src="../../static/images/common/avatar.png" mode="aspectFit"></image>
        <view class="name-time">
          <view class="name">{{commt.creatorName}}</view>
          <view class="time">{{commt.createTime}}</view>
        </view>
      </view>
      <!-- 评论内容 -->
      <view class="body" @click="commentComment(commt.id)">
        <view class="body-text">{{commt.content}}</view>
      </view>
      <!-- 评论数据 -->
      <view class="foot">
        <view class="foot-icon" @click="secondComment(commt.id)">
          展开{{ commt.commentCount }}条回复
          <uni-icons type="bottom" size="20"></uni-icons>
        </view>
        <view class="foot-icon" @click="thumbComment(commt.id)">
          {{ commt.likeCount }}
          <uni-icons v-if="!commt.isHeart" type="heart" size="20" color="black"></uni-icons>
          <uni-icons v-if="commt.isHeart" type="heart-filled" size="20" color="red"></uni-icons>
        </view>
        <view v-if="(userId==commt.commentator)" class="foot-icon" @click="deleteComm(commt.id)">
          <uni-icons type="trash" size="20" color="orange"></uni-icons>
        </view>
      </view>
    </view>
  </view>
  </view>
  <!-- 二级评论弹出层 -->
  <view  v-if="data.isShow.check" class="sub">
    <scroll-view  scroll-y >
      <comment
      v-for="(commt,index) in data.Secomments"
      :key="index"
      :dynamic-card="commt"
      @triggerFlush="secCommentList"
      ></comment>
    </scroll-view>
    <view class="exitSec" @click="unSecondComment">
      返回
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
import { ref, reactive,onMounted,getCurrentInstance } from 'vue';
import comment from '../../components/DoctorTalk/comment.vue'
import { onLoad } from '@dcloudio/uni-app';
import {listComment,listSecComment,saveComment,deleteComment} from '../../api/question'
import {questionThumb,questionUnThumb} from '@/api/question' 
import { storeToRefs } from 'pinia';
import { useuserinfostore } from '@/store/userinfo';
import moment from "moment";
// 设置语言为中文
import 'moment/dist/locale/zh-cn'
moment.locale("zh-cn");

// 缓存中的用户登录状态
const userinfostore = useuserinfostore();
const { userinfo } = storeToRefs(userinfostore);

// 遮罩层动态类名
let isFixed=ref(false)
let userId=ref(-1)

// 变量 
const data= reactive({
  dynamicCard:{},
  isShow:{ // 二级评论弹出层是否显示
    "check":false
  },
  isShowCommt:{ // 回复评论弹出层是否显示
    "check":false
  },
  param:{
    qid:-1, // 帖子id
    parent_id:0, // 父id,0表示一级评论
    page:1,
    limit:20
  },
  comments:[], // 一级评论列表
  Secomments:{}, // 二级评论列表
  ThumbsEntity:{ // 点赞参数
    entityId:-1,
    memberId:-1,
    type:1 // 1表示评论
  },
  CommentEntity:{ // 保存评论
    parentId:-1,
    grandParentId:-1,
    questionId:-1,
    creatorId:-1,
    creatorName:"",
    content:""
  },
  CommentTo:{
    commentId:-1,
    questionId:-1
  }
})

// 页面传参接收
onLoad((option)=>{
    // 加载帖子数据
    const param = JSON.parse(decodeURIComponent(option.dynamicCardStr))
    console.log("onLoad-->TalkDetail")
    data.dynamicCard=param
    // 获取一级评论列表 
    commentList()
    // 加载用户id
    userId=userinfo.value.id
    // 初始化点赞图标
    checkIsThumb()
});

// 判断帖子是否是点赞
const checkIsThumb=(()=>{
    const sto_k='question:'+data.dynamicCard.id+':isHeart'
    data.dynamicCard.isHeart=uni.getStorageSync(sto_k)=='true'
    // 判断评论是否点赞
    data.comments.forEach(element => {
      const sto_k_commt='commet:'+element.id+':isHeart'
      element.isHeart=uni.getStorageSync(sto_k_commt)=='true'
    });
})

onMounted(()=>{
        // 初始化点赞图标
        checkIsThumb()
  });
onUpdated(()=>{
      // 初始化点赞图标
      checkIsThumb()
})

// 获取一级评论列表
const commentList= async () => {
  uni.showLoading({ title: '查询中', mask: true });
  data.param.qid=data.dynamicCard.id
  const res=await listComment(data.param)
  setTimeout(function () {
      uni.hideLoading();
    }, 500);
  console.log("查询一级评论列表")
  console.log(res.page.list)
  data.comments=res.page.list
  // 日期转换
  data.comments.forEach(element => {
    element.createTime=moment(element.createTime).fromNow()
  });
}

// 获取二级评论列表
const secCommentList= async () => {
  uni.showLoading({ title: '查询中', mask: true });
  const res=await listSecComment(data.param)
  setTimeout(function () {
      uni.hideLoading();
    }, 500);
  console.log("查询二级评论列表")
  console.log(res.page.list)
  data.Secomments=res.page.list
  // 日期转换
  data.Secomments.forEach(element => {
    element.createTime=moment(element.createTime).fromNow();
  });
}


// 打开二级评论
const secondComment=(id)=> {
    // 开启遮罩层
    data.isShow.check=!data.isShow.check;
    // 开启遮罩层禁止下层页面滚动失效
    isFixed.value=true
    // 查询参数
    data.param.parent_id=id
    // 获取二级评论列表
    secCommentList()
} 

// 关闭二级评论
const unSecondComment=(event)=> {
      data.isShow.check=!data.isShow.check;
      // 开启遮罩层禁止下层页面滚动失效
      isFixed.value=false
      // 获取二级评论列表
      secCommentList()
} 

// 点赞评论
const thumbComment = async (id) => {
  data.ThumbsEntity.entityId=id // 评论的id
  data.ThumbsEntity.type=1
  data.ThumbsEntity.memberId=userinfo.value.id
  const sto_k_commt='commet:'+id+':isHeart'
  let tag=true;

  data.comments.forEach((element) => {
      if(element.id==id){
        if(element.isHeart==true){
          // 取消点赞
          console.log("取消点赞")
          tag=true;
          uni.removeStorageSync(sto_k_commt)
        }else{
          // 点赞
          console.log("点赞")
          tag=false;
          uni.setStorageSync(sto_k_commt,'true');
        }
        // 图标取反
        element.isHeart=!element.isHeart
      }
    });
    if(tag){
      const res=await questionUnThumb(data.ThumbsEntity)
    }else{
      const res=await questionThumb(data.ThumbsEntity)
    }
};

//点赞帖子
const thumbQuestion = async () => {
  console.log("点赞帖子")
  data.ThumbsEntity.memberId=userinfo.value.id
  data.ThumbsEntity.entityId=data.dynamicCard.id // 帖子的id
  data.ThumbsEntity.type=0

  const sto_k='question:'+data.dynamicCard.id+':isHeart'
  if(data.dynamicCard.isHeart==true){
    // 取消点赞
    const res=await questionUnThumb(data.ThumbsEntity)
    uni.removeStorageSync(sto_k)
  }else{
    // 点赞
    const res=await questionThumb(data.ThumbsEntity)
    uni.setStorageSync(sto_k,'true');
  }
  // 图标取反
  data.dynamicCard.isHeart=!data.dynamicCard.isHeart
};

// 打开回复帖子
const commentQuestion = async (e) => {
  console.log("打开回复帖子")
  // 开启遮罩层
  data.isShowCommt.check=!data.isShowCommt.check;
  // 开启遮罩层禁止下层页面滚动失效
  isFixed.value=true
  data.CommentEntity.parentId=0 //
  data.CommentEntity.grandParentId=0 // 
};

// 打开回复评论
const commentComment = async (parnetId) => {
  console.log("打开回复评论")
  // 开启遮罩层
  data.isShowCommt.check=!data.isShowCommt.check;
  // 开启遮罩层禁止下层页面滚动失效
  isFixed.value=true
  data.CommentEntity.parentId=parnetId //
  data.CommentEntity.grandParentId=parnetId // 
};

// 关闭评论帖子
const uncommentQuestion=(e)=> {
  // 关闭遮罩层
  data.isShowCommt.check=!data.isShowCommt.check;
  // 开启遮罩层禁止下层页面滚动失效
  isFixed.value=false
} 

// 提交评论帖子
const SubmitcommentQuestion = async (e) => {
  // 关闭遮罩层
  data.isShowCommt.check=!data.isShowCommt.check;
  // 开启遮罩层禁止下层页面滚动失效
  isFixed.value=false

  console.log(userinfo.value)
  var formdata = e.detail.value
  
  data.CommentEntity.questionId=data.dynamicCard.id // 
  data.CommentEntity.creatorId=userinfo.value.id 
  data.CommentEntity.creatorName=userinfo.value.nickname // 评论的内容
  data.CommentEntity.content=formdata.content // 评论的内容
  const res=await saveComment(data.CommentEntity)
  // 重新查询最新的评论列表
  commentList()
};

// 删除评论
const deleteComm=async (id)=>{
  console.log("删除评论"+id)
  // data.CommentTo.commentId=id
  data.CommentTo.questionId=data.dynamicCard.id
  const res=await deleteComment(data.CommentTo)
  // 重新查询最新的评论列表  
  commentList()
}

</script>

<style lang="less" scoped>
.uni-column{
  margin: 20rpx auto;
}
.box {
  border-bottom: 1rpx solid #edeff3;
  .topContent{
    border-bottom: 10rpx solid rgb(94, 89, 89);
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
        .name{
          font-size: 38rpx;
          font-weight: bold;
        }
        .time{
          font-size: 27rpx;
        }
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
}
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
</style>

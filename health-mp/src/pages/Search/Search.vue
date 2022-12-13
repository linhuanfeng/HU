<template>
  <view class="content">
    <view class="search">
      <NewSearch :placeholder="placeholder" @returnList="returnList"></NewSearch>
    </view>
    <view class="searchRes">
      <!-- 推荐 -->
      <scroll-view scroll-y class="core-one">
        <recommendCard
          v-for="(dynamicCard, index) in data.dynamicCards"
          :key="index"
          :dynamic-card="dynamicCard"
        ></recommendCard>
      </scroll-view>
  </view>
  </view>
</template>

<script setup>
import recommendCard from '../../components/DoctorTalk/recommendCard.vue';
import NewSearch from '../../components/DoctorTalk/NewSearch.vue';
import { ref } from 'vue';

const data = reactive({
  dynamicCards:{}
})

/**
 * 获取热门搜索，通过API获取
 */
const placeholder = ref('H-U,只为更好的你');

// 子组件返回查询结果
const returnList=async(dynamicCards)=>{
  data.dynamicCards=dynamicCards
  console.log('得到子组件的搜索结果：')
  console.log(data.dynamicCards)
}

</script>

<style scoped lang="less">
.highLight{
  color: red;
}
.content {
  position: relative;
  .search {
    position: absolute;
  }
  .searchRes{
    position: absolute;
    margin-top: 55vh;
    .core-one {
      height: 80vh;
    }
  }
}

</style>

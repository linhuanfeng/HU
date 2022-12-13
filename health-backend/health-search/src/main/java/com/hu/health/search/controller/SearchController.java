package com.hu.health.search.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.hu.health.common.pojo.community.QuestionEntity;
import com.hu.health.common.utils.R;
import com.hu.health.search.entity.AreaAndTagEntity;
import com.hu.health.search.entity.UserRecordEntity;
import com.hu.health.search.feign.QuestionFeignService;
import com.hu.health.search.service.QuestionService;
import com.hu.health.search.to.SearchResultTo;
import com.hu.health.common.pojo.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("search/search")
@Slf4j
public class SearchController {

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private QuestionFeignService questionFeignService;

    @Autowired
    private QuestionService questionService;

    /**
     * 单个帖子更新到es
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/update/{id}")
    public R update(@PathVariable(name = "id") Long id) throws IOException {
        R info = questionFeignService.info(id);
        Object question = info.get("question");
        if(question==null){
            log.warn("帖子{}不存在，无法更新",id);
            return R.error("帖子{"+id+"}不存在，无法更新");
        }
        QuestionEntity entity = JSON.parseObject(JSON.toJSONString(question), QuestionEntity.class);
        BulkResponse responses = questionService.insert(Arrays.asList(entity));
        return R.ok().setData(responses);
    }

//    @GetMapping("createIndex")
//    public R createIndex() throws IOException {
//        questionService.createIndex(QuestionConstants.INDEX_NAME_QUESTION,QuestionConstants.MAPPING_QUESTION_TEMPLATE);
//        return R.ok();
//    }

    /**
     * 批量帖子更新到es中
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/update")
    public R update() throws IOException {
        Map<String,Object> params=new HashMap<>();
        int page=1,limit=20,total=0;
        // 循环拉去数据，批量导入
        while (true){
            params.put("page",page);
            params.put("limit",limit);
            R list = questionFeignService.list(params);
            LinkedHashMap<String, Object> data = list.getData(new TypeReference<LinkedHashMap<String, Object>>() {
            });
            Object realList = data.get("list");
            BulkResponse response=null;
            if(realList!=null){
                JSONArray jsonArray = (JSONArray) realList;
                List<QuestionEntity> entities = jsonArray.toJavaList(QuestionEntity.class);
                int size = entities.size();
                total+=size;
                if(size<1){
                    break;
                }
                response = questionService.insert(entities);
            }
            page++;
        }
        return R.ok().put("total",total);
    }

    /**
     * 保存用户的搜索记录：关键字、分区和标签
     * @param entity
     * @return
     * @throws IOException
     */
    @PostMapping("insertUserRecord")
    public R insertUserRecord(@RequestBody UserRecordEntity entity) throws IOException {
        BulkResponse bulkResponse = questionService.insertUserRecords(Arrays.asList(entity));
        return R.ok().setData(bulkResponse);
    }

    /**
     * 聚合查询，根据用户userId得到浏览次数最多的分区名areaName和标签tag
     * @param userId
     * @return
     */
    @GetMapping("/getAreaAndTag")
    public R getAreaAndTag(Long userId){
        AreaAndTagEntity entity = questionService.getAreaAndTag(userId);
        entity.setUserId(userId);
        return R.ok().setData(entity);
    }

    /**
     * 根据关键字查询帖子
     * 需要传入参数：关键字、分区、标签；索引自带：点赞数和浏览次数
     * 基于ElasticSearch的function_score，综合社区帖子的热度（点赞和浏览数）和用户历史浏览搜索数据（关键字，分区和标签）
     * @param searchParam
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/search")
    public R search(@RequestBody SearchParam searchParam) throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                // 异步保存热搜职位
                if(StringUtils.hasLength(searchParam.getKeyword())){
                    questionService.saveHotWord(searchParam);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        },executor);

        CompletableFuture<SearchResultTo> searchFuture = CompletableFuture.supplyAsync(() -> {
            SearchResultTo resultTo = questionService.searchMulti(searchParam);
            return resultTo;
        },executor);

        SearchResultTo resultTo = searchFuture.get();
        return R.ok().setData(resultTo);
    }

    /**
     * 获取当前热搜
     * @return
     */
    @GetMapping("/hot_word")
    public R getHotWords(){
        List<String> hotWords = questionService.hotWords();
        return R.ok().setData(hotWords);
    }

    @GetMapping("/suggestion")
    public R getSuggestion(@RequestParam("prefix") String prefix) throws IOException {
        List<String> suggestions = questionService.getSuggestions(prefix);
        return R.ok().setData(suggestions);
    }
}

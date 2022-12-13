package com.hu.health.search.service.impl;


import com.alibaba.fastjson.JSON;
import com.hu.health.common.pojo.community.QuestionEntity;
import com.hu.health.search.constants.QuestionConstants;
import com.hu.health.search.entity.AreaAndTagEntity;
import com.hu.health.search.entity.UserRecordEntity;
import com.hu.health.search.service.QuestionService;
import com.hu.health.search.to.SearchResultTo;
import com.hu.health.search.to.HotWordVo;
import com.hu.health.common.pojo.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
//import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.hu.health.search.constants.QuestionConstants.INDEX_NAME_HOT;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    RestHighLevelClient client;

    /**
     * 搜索自动补全
     *
     * @param prefix
     * @return
     * @throws IOException
     */
    @Override
    public List<String> getSuggestions(String prefix) throws IOException {
        SearchRequest request = new SearchRequest(QuestionConstants.INDEX_NAME_QUESTION);
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "suggestions",
                SuggestBuilders.completionSuggestion("suggestion")
                        .prefix(prefix)
                        .skipDuplicates(true)
                        .size(10)
        ));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        Suggest suggest = response.getSuggest();
        CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
        List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
        List<String> list = new ArrayList<>(options.size());
        for (CompletionSuggestion.Entry.Option option : options) {
            String text = option.getText().toString();
            list.add(text);
        }

        return list;
    }

    /**
     * 创建索引库
     *
     * @throws IOException
     */
    @Override
    public void createIndex(String indexName, String mappingTemplate) throws IOException {
        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 2.请求参数，MAPPER_TEMPLATE是创建索引库的DSL字符串
        request.source(mappingTemplate, XContentType.JSON);
        // 3.发送请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 删除索引库
     *
     * @throws IOException
     */
    @Override
    public void deleteHotelIndex() throws IOException {
        // 1.创建request对象
        DeleteIndexRequest request = new DeleteIndexRequest(QuestionConstants.INDEX_NAME_QUESTION);
        // 2.发送请求
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }


    /**
     * 批量插入文档
     *
     * @param questionEntities
     */
    @Override
    public BulkResponse insert(List<QuestionEntity> questionEntities) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (QuestionEntity questionEntity : questionEntities) {
            questionEntity.setSuggestion(Arrays.asList(questionEntity.getTitle(), questionEntity.getTag()));
            bulkRequest.add(new IndexRequest(QuestionConstants.INDEX_NAME_QUESTION)
                    .id(questionEntity.getId().toString())
                    .source(JSON.toJSONString(questionEntity), XContentType.JSON));
        }
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        return response;
    }

    /**
     * 用户记录批量导入到user_record索引中
     *
     * @param entities
     * @return
     * @throws IOException
     */
    @Override
    public BulkResponse insertUserRecords(List<UserRecordEntity> entities) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (UserRecordEntity entity : entities) {
            bulkRequest.add(new IndexRequest(QuestionConstants.INDEX_NAME_USER_RECORD)
//                    .id(entity.getUserId().toString())
                    .source(JSON.toJSONString(entity), XContentType.JSON));
        }
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return response;
    }

    @Override
    public SearchResultTo search(SearchParam param) {
        SearchRequest request = buildSearchRequest(param);
        log.info("构建查询{}", request);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            log.info("响应{}", response);
            SearchResultTo buildSearchResult = buildSearchResult(response);
            log.info("构建结果{}", buildSearchResult);
            return buildSearchResult;
        } catch (IOException e) {
            log.error("{}", e.getStackTrace());
        }
        return null;
    }

    /**
     * 自定义算分函数
     * 需要传入参数：关键字、分区、标签；索引自带：点赞数和浏览次数
     * 基于ElasticSearch的function_score，综合社区帖子的热度（点赞和浏览数）和用户历史浏览搜索数据（关键字，分区和标签）
     *
     * @param param
     * @return
     */
    @Override
    public SearchResultTo searchMulti(SearchParam param) {
        // 构建多条件查询
        SearchRequest request = buildSearchRequestMulti(param);
        log.info("构建查询{}", request);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            log.info("响应{}", response);
            SearchResultTo buildSearchResult = buildSearchResult(response);
            log.info("构建结果{}", buildSearchResult);
            return buildSearchResult;
        } catch (IOException e) {
            log.error("{}", e.getStackTrace());
        }
        return null;
    }

    /**
     * 通过聚合实现热搜功能
     *
     * @return
     */
    @Override
    public List<String> hotWords() {
        try {
            SearchRequest searchRequest = new SearchRequest(INDEX_NAME_HOT);//indexName是索引名称
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);
            //聚合分析查询出现次数最多的10个词汇，hotWord是聚合名称，name是es的字段名  BucketOrder.count(false):降序
            TermsAggregationBuilder hotWordAgg = AggregationBuilders.terms("hotWordAgg").field("hotWord").size(10).order(BucketOrder.count(false));
            searchSourceBuilder.aggregation(hotWordAgg);
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = response.getAggregations();

            Terms term = aggregations.get("hotWordAgg");
            List<Terms.Bucket> buckets = (List<Terms.Bucket>) term.getBuckets();
            List<String> hotWords = new ArrayList<>();
            for (Terms.Bucket bucket : buckets) {
                String key = (String) bucket.getKey();
                long docCount = bucket.getDocCount();
                hotWords.add(key);
                log.info("热词:" + key + "数量为：" + docCount);
            }
            return hotWords;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("热词搜索失败");
        }
        return null;
    }

    /**
     * 聚合查询，根据用户userId得到浏览次数最多的分区名areaName和标签tag
     */
    public AreaAndTagEntity getAreaAndTag(Long userId) {
        AreaAndTagEntity areaAndTag = new AreaAndTagEntity();
        try {
            // 设置索引名称为user_record
            SearchRequest searchRequest = new SearchRequest(QuestionConstants.INDEX_NAME_USER_RECORD);//indexName是索引名称
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 1、term查询：根据用户id进行term查询
            searchSourceBuilder.query(QueryBuilders.termQuery("userId", userId));
            searchRequest.source(searchSourceBuilder);
            final String MAX_AREA_NAME = "last_three_day_max_areaName", MAX_TAG = "last_three_day_max_tag";
            // 2、结果聚合：根据帖子分区名和帖子标签的对term结果进行聚合，降序排列取第一个
            TermsAggregationBuilder m_areaName = AggregationBuilders.terms(MAX_AREA_NAME).field("areaName").size(1).order(BucketOrder.count(false));
            TermsAggregationBuilder m_tag = AggregationBuilders.terms(MAX_TAG).field("tag").size(1).order(BucketOrder.count(false));
            searchSourceBuilder.aggregation(m_areaName).aggregation(m_tag);
            searchRequest.source(searchSourceBuilder);

            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = response.getAggregations();

            // 得到分区名称出现次数最多的结果
            Terms term = aggregations.get(MAX_AREA_NAME);
            List<Terms.Bucket> buckets = (List<Terms.Bucket>) term.getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String key = (String) bucket.getKey();
                long docCount = bucket.getDocCount();
                areaAndTag.setAreaName(key);
                log.info("用户常逛分区:" + key + "，数量为：" + docCount);
                break;
            }
            // 得到标签出现次数最多的结果
            Terms term2 = aggregations.get(MAX_TAG);
            List<Terms.Bucket> buckets2 = (List<Terms.Bucket>) term2.getBuckets();
            for (Terms.Bucket bucket : buckets2) {
                String key = (String) bucket.getKey();
                long docCount = bucket.getDocCount();
                areaAndTag.setTag(key);
                log.info("用户常逛标签:" + key + "，数量为：" + docCount);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询用户:{}的分区名和标签出现次数最多失败" , userId);
        }
        return areaAndTag;
    }

    /**
     * 统计热词
     *
     * @param searchParam
     */
    @Override
    public void saveHotWord(SearchParam searchParam) throws IOException {
        HotWordVo hotWordVo = new HotWordVo();
        hotWordVo.setHotWord(searchParam.getKeyword());
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME_HOT).id(UUID.randomUUID().toString().replace("-", ""));
        indexRequest.source(JSON.toJSONString(hotWordVo), XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("插入热搜,response为{}", response);
    }

    /**
     * 封装response的高亮字段：title、description、tag、areaName
     *
     * @param response
     * @return
     */
    private SearchResultTo buildSearchResult(SearchResponse response) {
        SearchResultTo result = new SearchResultTo();
        SearchHits hits = response.getHits();
        List<QuestionEntity> collect = Arrays.stream(hits.getHits()).map(hit -> {
            String sourceAsString = hit.getSourceAsString();
            QuestionEntity questionEntity = JSON.parseObject(sourceAsString, QuestionEntity.class);
            HighlightField title = hit.getHighlightFields().get("title");
            if (title != null) {
                String s = title.getFragments()[0].toString();
                questionEntity.setTitle(s);
            }
            HighlightField description = hit.getHighlightFields().get("description");
            if (description != null) {
                String s = description.getFragments()[0].toString();
                questionEntity.setDescription(s);
            }
            HighlightField tag = hit.getHighlightFields().get("tag");
            if (tag != null) {
                String s = tag.getFragments()[0].toString();
                questionEntity.setTag(s);
            }
            HighlightField areaName = hit.getHighlightFields().get("areaName");
            if (areaName != null) {
                String s = areaName.getFragments()[0].toString();
                questionEntity.setDescription(s);
            }
            return questionEntity;
        }).collect(Collectors.toList());
        result.setQuestionEntityList(collect);
        /**
         * 分页信息
         */
        // 总记录数
        result.setTotalCount(hits.getTotalHits().value);
        return result;
    }

    /**
     * 需要传入参数：关键字、分区、标签；索引自带：点赞数和浏览次数
     * 基于function_score，综合社区帖子点赞和浏览数,用户历史浏览搜索数据（关键字，分区和标签）
     *
     * @param param keyword areaName tag
     * @return
     */
    private SearchRequest buildSearchRequestMulti(SearchParam param) {
        String keyword = param.getKeyword();
        String areaName = param.getAreaName();
        String tag = param.getTag();

        Integer from = param.getFrom();
        Integer size = param.getFrom();

        // 如果分区和标签为空，采用历史记录
        AreaAndTagEntity areaAndTag = getAreaAndTag(param.getUserId());
        if(!StringUtils.hasLength(areaName)){
            areaName=areaAndTag.getAreaName();
            param.setAreaName(areaName);
        }
        if(!StringUtils.hasLength(tag)){
            tag=areaAndTag.getTag();
            param.setTag(tag);
        }

        log.info("查询参数：" + param);

        // 1.指定索引
        SearchRequest request = new SearchRequest(QuestionConstants.INDEX_NAME_QUESTION);
        // 2 构建查询条件，包含functionScoreQuery
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 3 构建bool作为functionScoreQuery的原始查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(StringUtils.hasLength(keyword)){
            boolQuery.must(QueryBuilders.matchQuery("all",keyword));
        }else {
            boolQuery.must(QueryBuilders.matchAllQuery());
        }

        // 4、添加加强函数：点赞次数、评论次数的分值，分区，标签
        FunctionScoreQueryBuilder functionScoreQuery =
                QueryBuilders.functionScoreQuery(
                        // 原始查询
                        boolQuery,
                        // 加强函数列表
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                        ScoreFunctionBuilders.fieldValueFactorFunction("likeCount")
                                                .missing(0).factor(5).setWeight(5)
                                ),
                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                        ScoreFunctionBuilders.fieldValueFactorFunction("commentCount")
                                                .missing(0).factor(5).setWeight(5)
                                ),
                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                        QueryBuilders.matchQuery("areaName", areaName),
                                        ScoreFunctionBuilders.weightFactorFunction(20)
                                ),
                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                        QueryBuilders.matchQuery("tag", tag),
                                        ScoreFunctionBuilders.weightFactorFunction(20)
                                )
                        }
                        // 算分函数function score数组
                        ).scoreMode(FunctionScoreQuery.ScoreMode.SUM)  //决定加强函数怎么合并
                        .boostMode(CombineFunction.SUM); // 原始得分和算分得分的合并方式

        // 5、构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("*")
                .preTags("<p class='highLight'>").postTags("</p>").requireFieldMatch(false);
        builder.highlighter(highlightBuilder);

        // 6、分页信息
        request.source().from(from);
        request.source().size(size);

        // 开始查询
        builder.query(functionScoreQuery);
        request.source(builder);
        return request;
    }

    /**
     * 构建请求
     *
     * @param param
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParam param) {
        SearchRequest request = new SearchRequest();
        // 1.指定索引
        request.indices(QuestionConstants.INDEX_NAME_QUESTION);
        // 2.构建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 2.1构建bool查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        String keyword = param.getKeyword();
        if (!StringUtils.isEmpty(keyword)) {
            // 效果一样，多字段查询性能差点
//            MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.
//                    multiMatchQuery(keyword,"title",  keyword, "description", keyword, "tag",keyword, "areaName");
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("all", keyword);
            boolQuery.should(matchQueryBuilder);
        }
        // 第一部分布尔结束
        builder.query(boolQuery);

        // 2.2构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("description").field("tag").field("areaName")
                .preTags("<b style='color:red'>").postTags("</b>");
        builder.highlighter(highlightBuilder);

        request.source(builder);
        return request;
    }

    /**
     * 根据id删除文档
     * @param id
     * @return
     */
    @Override
    public DeleteResponse deleteById(Long id) {
        // 1.准备Request
        DeleteRequest request = new DeleteRequest(QuestionConstants.INDEX_NAME_QUESTION, id.toString());
        // 2.发送请求
        try {
            return client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

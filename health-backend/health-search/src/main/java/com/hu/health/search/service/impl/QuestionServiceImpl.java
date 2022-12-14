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
     * ??????????????????
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
     * ???????????????
     *
     * @throws IOException
     */
    @Override
    public void createIndex(String indexName, String mappingTemplate) throws IOException {
        // 1.??????Request??????
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 2.???????????????MAPPER_TEMPLATE?????????????????????DSL?????????
        request.source(mappingTemplate, XContentType.JSON);
        // 3.????????????
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * ???????????????
     *
     * @throws IOException
     */
    @Override
    public void deleteHotelIndex() throws IOException {
        // 1.??????request??????
        DeleteIndexRequest request = new DeleteIndexRequest(QuestionConstants.INDEX_NAME_QUESTION);
        // 2.????????????
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }


    /**
     * ??????????????????
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
     * ???????????????????????????user_record?????????
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
        log.info("????????????{}", request);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            log.info("??????{}", response);
            SearchResultTo buildSearchResult = buildSearchResult(response);
            log.info("????????????{}", buildSearchResult);
            return buildSearchResult;
        } catch (IOException e) {
            log.error("{}", e.getStackTrace());
        }
        return null;
    }

    /**
     * ?????????????????????
     * ??????????????????????????????????????????????????????????????????????????????????????????
     * ??????ElasticSearch???function_score????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param param
     * @return
     */
    @Override
    public SearchResultTo searchMulti(SearchParam param) {
        // ?????????????????????
        SearchRequest request = buildSearchRequestMulti(param);
        log.info("????????????{}", request);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            log.info("??????{}", response);
            SearchResultTo buildSearchResult = buildSearchResult(response);
            log.info("????????????{}", buildSearchResult);
            return buildSearchResult;
        } catch (IOException e) {
            log.error("{}", e.getStackTrace());
        }
        return null;
    }

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @Override
    public List<String> hotWords() {
        try {
            SearchRequest searchRequest = new SearchRequest(INDEX_NAME_HOT);//indexName???????????????
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);
            //???????????????????????????????????????10????????????hotWord??????????????????name???es????????????  BucketOrder.count(false):??????
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
                log.info("??????:" + key + "????????????" + docCount);
            }
            return hotWords;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("??????????????????");
        }
        return null;
    }

    /**
     * ???????????????????????????userId????????????????????????????????????areaName?????????tag
     */
    public AreaAndTagEntity getAreaAndTag(Long userId) {
        AreaAndTagEntity areaAndTag = new AreaAndTagEntity();
        try {
            // ?????????????????????user_record
            SearchRequest searchRequest = new SearchRequest(QuestionConstants.INDEX_NAME_USER_RECORD);//indexName???????????????
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 1???term?????????????????????id??????term??????
            searchSourceBuilder.query(QueryBuilders.termQuery("userId", userId));
            searchRequest.source(searchSourceBuilder);
            final String MAX_AREA_NAME = "last_three_day_max_areaName", MAX_TAG = "last_three_day_max_tag";
            // 2????????????????????????????????????????????????????????????term?????????????????????????????????????????????
            TermsAggregationBuilder m_areaName = AggregationBuilders.terms(MAX_AREA_NAME).field("areaName").size(1).order(BucketOrder.count(false));
            TermsAggregationBuilder m_tag = AggregationBuilders.terms(MAX_TAG).field("tag").size(1).order(BucketOrder.count(false));
            searchSourceBuilder.aggregation(m_areaName).aggregation(m_tag);
            searchRequest.source(searchSourceBuilder);

            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = response.getAggregations();

            // ?????????????????????????????????????????????
            Terms term = aggregations.get(MAX_AREA_NAME);
            List<Terms.Bucket> buckets = (List<Terms.Bucket>) term.getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String key = (String) bucket.getKey();
                long docCount = bucket.getDocCount();
                areaAndTag.setAreaName(key);
                log.info("??????????????????:" + key + "???????????????" + docCount);
                break;
            }
            // ???????????????????????????????????????
            Terms term2 = aggregations.get(MAX_TAG);
            List<Terms.Bucket> buckets2 = (List<Terms.Bucket>) term2.getBuckets();
            for (Terms.Bucket bucket : buckets2) {
                String key = (String) bucket.getKey();
                long docCount = bucket.getDocCount();
                areaAndTag.setTag(key);
                log.info("??????????????????:" + key + "???????????????" + docCount);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("????????????:{}?????????????????????????????????????????????" , userId);
        }
        return areaAndTag;
    }

    /**
     * ????????????
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
        log.info("????????????,response???{}", response);
    }

    /**
     * ??????response??????????????????title???description???tag???areaName
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
         * ????????????
         */
        // ????????????
        result.setTotalCount(hits.getTotalHits().value);
        return result;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????
     * ??????function_score???????????????????????????????????????,???????????????????????????????????????????????????????????????
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

        // ????????????????????????????????????????????????
        AreaAndTagEntity areaAndTag = getAreaAndTag(param.getUserId());
        if(!StringUtils.hasLength(areaName)){
            areaName=areaAndTag.getAreaName();
            param.setAreaName(areaName);
        }
        if(!StringUtils.hasLength(tag)){
            tag=areaAndTag.getTag();
            param.setTag(tag);
        }

        log.info("???????????????" + param);

        // 1.????????????
        SearchRequest request = new SearchRequest(QuestionConstants.INDEX_NAME_QUESTION);
        // 2 ???????????????????????????functionScoreQuery
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 3 ??????bool??????functionScoreQuery???????????????
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(StringUtils.hasLength(keyword)){
            boolQuery.must(QueryBuilders.matchQuery("all",keyword));
        }else {
            boolQuery.must(QueryBuilders.matchAllQuery());
        }

        // 4??????????????????????????????????????????????????????????????????????????????
        FunctionScoreQueryBuilder functionScoreQuery =
                QueryBuilders.functionScoreQuery(
                        // ????????????
                        boolQuery,
                        // ??????????????????
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
                        // ????????????function score??????
                        ).scoreMode(FunctionScoreQuery.ScoreMode.SUM)  //??????????????????????????????
                        .boostMode(CombineFunction.SUM); // ??????????????????????????????????????????

        // 5???????????????
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("*")
                .preTags("<p class='highLight'>").postTags("</p>").requireFieldMatch(false);
        builder.highlighter(highlightBuilder);

        // 6???????????????
        request.source().from(from);
        request.source().size(size);

        // ????????????
        builder.query(functionScoreQuery);
        request.source(builder);
        return request;
    }

    /**
     * ????????????
     *
     * @param param
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParam param) {
        SearchRequest request = new SearchRequest();
        // 1.????????????
        request.indices(QuestionConstants.INDEX_NAME_QUESTION);
        // 2.??????????????????
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 2.1??????bool??????
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        String keyword = param.getKeyword();
        if (!StringUtils.isEmpty(keyword)) {
            // ??????????????????????????????????????????
//            MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.
//                    multiMatchQuery(keyword,"title",  keyword, "description", keyword, "tag",keyword, "areaName");
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("all", keyword);
            boolQuery.should(matchQueryBuilder);
        }
        // ????????????????????????
        builder.query(boolQuery);

        // 2.2????????????
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("description").field("tag").field("areaName")
                .preTags("<b style='color:red'>").postTags("</b>");
        builder.highlighter(highlightBuilder);

        request.source(builder);
        return request;
    }

    /**
     * ??????id????????????
     * @param id
     * @return
     */
    @Override
    public DeleteResponse deleteById(Long id) {
        // 1.??????Request
        DeleteRequest request = new DeleteRequest(QuestionConstants.INDEX_NAME_QUESTION, id.toString());
        // 2.????????????
        try {
            return client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

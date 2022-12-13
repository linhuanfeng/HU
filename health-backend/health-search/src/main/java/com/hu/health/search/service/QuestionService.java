package com.hu.health.search.service;

import com.hu.health.common.pojo.community.QuestionEntity;
import com.hu.health.search.entity.AreaAndTagEntity;
import com.hu.health.search.entity.UserRecordEntity;
import com.hu.health.search.to.SearchResultTo;
import com.hu.health.common.pojo.SearchParam;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;

import java.io.IOException;
import java.util.List;

public interface QuestionService  {
    /**
     * 根据关键字搜索酒店信息
     * @return 酒店文档列表
     */
//    PageResult search(RequestParams params);

//    Map<String, List<String>> getFilters(RequestParams params);

//    List<String> getSuggestions(String prefix);

    void createIndex(String indexName,String mappingTemplate) throws IOException;

    void deleteHotelIndex() throws IOException;

    BulkResponse insert(List<QuestionEntity> questionEntities) throws IOException;
    BulkResponse insertUserRecords(List<UserRecordEntity> entities) throws IOException;

    DeleteResponse deleteById(Long id);

    SearchResultTo search(SearchParam param);
    SearchResultTo searchMulti(SearchParam param);

    List<String> hotWords();

    void saveHotWord(SearchParam searchParam) throws IOException;

    List<String> getSuggestions(String prefix) throws IOException;

    AreaAndTagEntity getAreaAndTag(Long userId);
}

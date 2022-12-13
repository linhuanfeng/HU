package com.hu.health.search;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class HealthSearchApplicationTests {

    private

    /**
     * 插入文档
     * @throws IOException
     */
    @Test
    void testAddDocument() throws IOException {
//        // 1.根据id查询酒店数据
//        Hotel hotel = hotelService.getById(61083L);
//        // 2.转换为文档类型
//        HotelDoc hotelDoc = new HotelDoc(hotel);
//        // 3.将hotelDoc转json
//        String json = JSON.toJSONString(hotelDoc);
//
//        // 1.准备request对象
//        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
//        // 2.准备json文档
//        request.source(json, XContentType.JSON);
//        // 3.发送请求
//        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//        System.out.println(response);
    }


}

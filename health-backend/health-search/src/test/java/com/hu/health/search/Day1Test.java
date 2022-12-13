package com.hu.health.search;


import com.alibaba.fastjson.JSON;
import com.hu.health.search.constants.QuestionConstants;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class Day1Test {

    private RestHighLevelClient client;

    @Autowired
//    private IHotelService hotelService;

    @Test
    void test() {
        System.out.println(client);
    }

    /**
     * 创建索引库
     * @throws IOException
     */
    @Test
    void testCreateHotelIndex() throws IOException {
        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        // 2.请求参数，MAPPER_TEMPLATE是创建索引库的DSL字符串
//        request.source(QuestionConstants.MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 删除索引库
     * @throws IOException
     */
    @Test
    void testDeleteHotelIndex() throws IOException {
        // 1.创建request对象
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        // 2.发送请求
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }

    /**
     * 判断索引库是否存在
     * @throws IOException
     */
    @Test
    void testExistsHotelIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("hotel");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists?"索引库已经存在":"索引库不存在");
    }

    /**
     * 新增文档
     * @throws IOException
     */
    @Test
    void testIndexDocument() throws IOException {
        IndexRequest request = new IndexRequest("hotel").id("1");
        request.source("{\"name\":\"jack\",\"age\":21}", XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 插入文档
     * @throws IOException
     */
    @Test
    void testAddDocument() throws IOException {
        // 1.根据id查询酒店数据
//        Hotel hotel = hotelService.getById(61083L);
        // 2.转换为文档类型
//        HotelDoc hotelDoc = new HotelDoc(hotel);
        // 3.将hotelDoc转json
//        String json = JSON.toJSONString(hotelDoc);

        // 1.准备request对象
//        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
        // 2.准备json文档
//        request.source(json, XContentType.JSON);
        // 3.发送请求
//        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//        System.out.println(response);
    }

    /**
     * 查询文档
     * @throws IOException
     */
    @Test
    void testGetDocumentBuId() throws IOException {
        // 1.准备request
        GetRequest request = new GetRequest("hotel", "61083");
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 3.解析响应结果
        String json = response.getSourceAsString();
//        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
//        System.out.println(hotelDoc);
    }

    /**
     * 修改文档
     * @throws IOException
     */
    @Test
    void testUpdateDocument() throws IOException {
        // 1.准备Request
        UpdateRequest request = new UpdateRequest("hotel", "61083");
        // 2.准备请求参数
        request.doc(
                "price", "952",
                "starName", "四钻"
        );
        // 3.发送请求
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 删除文档
     * @throws IOException
     */
    @Test
    void testDeleteDocument() throws IOException {
        // 1.准备Request
        DeleteRequest request = new DeleteRequest("hotel", "61083");
        // 2.发送请求
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 批量操作
     * @throws IOException
     */
    @Test
    void testBulkRequest() throws IOException {
        // 批量查询酒店数据
//        List<Hotel> hotels = hotelService.list();

        // 1.创建request
        BulkRequest request = new BulkRequest();
        // 2.准备参数，添加新增文档类型request对象
//        for (Hotel hotel : hotels) {
//            HotelDoc hotelDoc = new HotelDoc(hotel);
//            request.add(new IndexRequest("hotel")
//                        .id(hotelDoc.getId().toString())
//                        .source(JSON.toJSONString(hotelDoc),
//                    XContentType.JSON));
//        }
        // 3.发送请求
//        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
//        System.out.println(response);
    }

    /**
     * 连接数据源
     */
    @BeforeEach
    void contextLoads() {
        client=new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://114.132.44.209:9200")
        ));
    }

    /**
     * 关闭数据源
     * @throws IOException
     */
    @AfterEach
    void testDown() throws IOException {
        client.close();
    }

}

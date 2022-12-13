package com.hu.health.community;

import com.hu.health.community.service.QuestionService;
import io.swagger.models.auth.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@SpringBootTest(classes = HealthCommunityApplication.class)
@RunWith(SpringRunner.class)
public class QuestionCountTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private QuestionService questionService;

    /**
     * 模糊匹配，找到将所有的帖子点赞评论数同步到mysql
     */
//    @Test
//    public void sync() {
//        String keys = "hash:question:*";
//        Set<String> set = redisTemplate.keys(keys);
//
//        for (String key : set) {
//            BoundHashOperations<String, String, Object> hashOps = redisTemplate.boundHashOps(key);
//
//            Map<String, Object> map = hashOps.entries();
//
//            Object view_count = map.get("view_count");
//            if(view_count!=null){
//                Integer count=(Integer) view_count;
//            }
//
//            Object comment_count = map.get("comment_count");
//            if (comment_count != null) {
//                Integer count = (Integer) comment_count;
//                if (count != 0) {
//                    questionService.comment(52l, count);
//                    // 反向减少
//                    hashOps.increment("comment_count", count * -1);
//                }
//            }
//
//            Object like_count = map.get("like_count");
//            if (like_count != null) {
//                Integer count = (Integer) like_count;
//                if (count != 0) {
//                    questionService.thumb(52l, count);
////                    hashOps.increment("like_count", count * -1);
//                }
//            }
//        }
//    }
}

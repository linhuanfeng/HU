package com.hu.health.sport.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.hu.health.sport.constants.SportConstants;
import com.hu.health.sport.service.TypeService;
import com.lhf.starter.canal.annotation.CanalEventListener;
import com.lhf.starter.canal.annotation.ListenPoint;
import com.lhf.starter.canal.to.CanalMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@CanalEventListener
public class CanalListener {

    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource
    private TypeService typeService;

    /**
     * type改变，删除缓存
     * @param canalMsg
     * @param rowChange
     */
    @ListenPoint(schema = "health_sport",table = "sport_type")
    public void deleteRedisType(CanalMessage canalMsg, CanalEntry.RowChange rowChange){
        log.error("health_sport=>sport_type"+canalMsg+"EventType"+rowChange.getEventType());
        // 删除缓存
        redisTemplate.delete(SportConstants.SPORT_TYPE);
        // 主动触发
        Map<String,Object> params=new HashMap<>();
        params.put("page","1");
        params.put("limit","20");
        typeService.queryPage(params);
    }
}

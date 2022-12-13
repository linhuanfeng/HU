package com.hu.health.community;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Scanner;

/**
 * 这个叫做clientApi,也就是从canal-server获取bin-log
 * canal-adapter是更强的client封装，支持es,mq等
 *
 * 注意，现在一个server instance只能被一个client消费
 */
@SpringBootTest
public class CanalTest {

    @Test
    public void canalT() throws InterruptedException {
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("114.132.44.209", 11111),
                "rabbitmq", "", "");
        Scanner sc = new Scanner(System.in);
        while (true){
            connector.connect();
            connector.subscribe("health_community.*");
            Message message = connector.get(100);
            List<CanalEntry.Entry> entries = message.getEntries();
            System.out.println("message"+message);
            if(entries.isEmpty()){
                Thread.sleep(3000);
            }else {
                for (CanalEntry.Entry entry : entries) {
                    System.out.println(entry);
                }
            }
        }
    }
}

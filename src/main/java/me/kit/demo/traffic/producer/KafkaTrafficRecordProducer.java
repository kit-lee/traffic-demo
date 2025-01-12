package me.kit.demo.traffic.producer;

import me.kit.demo.traffic.bo.TrafficRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaTrafficRecordProducer {
    //@Autowired
    //private KafkaTemplate<String, TrafficRecord> kafkaTemplate;

    public void sentTopic(String userId, String api){
        //kafkaTemplate.send("traffic-topic", userId, new TrafficRecord(userId, api, System.currentTimeMillis()));
        // 本地装kafka失败，先注释kafka相关代码跑test case
    }
}

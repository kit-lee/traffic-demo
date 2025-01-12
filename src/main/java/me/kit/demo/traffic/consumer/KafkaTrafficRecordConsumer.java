package me.kit.demo.traffic.consumer;

import me.kit.demo.traffic.bo.TrafficRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
public class KafkaTrafficRecordConsumer {
    @KafkaListener(topics = "traffic-topic", groupId = "my-group")
    public void listen(TrafficRecord record) throws Exception {
        System.out.println("Receive traffic record, userId:" + record.getUserId()+", endpoint: "+record.getApiEndpoint());
        // TODO 持久化到数据库中，例如mongodb，代码略

    }
}

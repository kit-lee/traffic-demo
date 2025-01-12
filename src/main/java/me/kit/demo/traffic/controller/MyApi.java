package me.kit.demo.traffic.controller;

import me.kit.demo.traffic.producer.KafkaTrafficRecordProducer;
import me.kit.demo.traffic.service.TrafficLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apis")
public class MyApi {

    @Autowired
    private TrafficLimiter trafficLimiter;

    @Autowired
    private KafkaTrafficRecordProducer trafficRecordProducer;

    @GetMapping("/api1")
    public ResponseEntity<String> api1(@RequestParam String userId) {
        if(!trafficLimiter.isAllow(userId)){
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
        }

        trafficRecordProducer.sentTopic(userId, "api1");
        return ResponseEntity.status(HttpStatus.OK).body("hello! here is api1");
    }

    @PostMapping("/api2")
    public ResponseEntity<String> api2(@RequestParam String userId) {
        if(!trafficLimiter.isAllow(userId)){
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
        }

        trafficRecordProducer.sentTopic(userId, "api2");
        return ResponseEntity.status(HttpStatus.OK).body("hello! here is api2");
    }

    @PutMapping("/api3")
    public ResponseEntity<String> api3(@RequestParam String userId) {
        if(!trafficLimiter.isAllow(userId)){
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
        }

        trafficRecordProducer.sentTopic(userId, "api3");
        return ResponseEntity.status(HttpStatus.OK).body("hello! here is api3");
    }
}

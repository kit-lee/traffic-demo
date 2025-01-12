package me.kit.demo.traffic.service;

/**
 * 限流器
 * @author kit
 */
public interface TrafficLimiter {
    /**
     * 判断该次请求是否通过限流，实现类为具体限流算法
     * @param userId
     * @return
     */
    boolean isAllow(String userId);
}

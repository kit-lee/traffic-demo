package me.kit.demo.traffic.service;

import me.kit.demo.traffic.bo.UserProfile;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * 用redis实现一个滑动时间窗口限流
 * @author kit
 */
public class SliderTimeWindowTrafficLimiter implements TrafficLimiter{
    private RedisTemplate<String, Long> redisTemplate;
    private UserProfileService userProfileService;
    private static final String REDIS_KEY_PREFIX = "traffic:limit:";

    @Override
    public boolean isAllow(String userId) {
        String key = REDIS_KEY_PREFIX + userId;
        long currentTime = System.currentTimeMillis();

        UserProfile userProfile = userProfileService.getUserProfile(userId);

        // 删除过期的记录
        redisTemplate.opsForZSet().removeRangeByScore(key, 0, currentTime - userProfile.getWindowSizeMillis());

        // 获取当前窗口内的计数
        Set<Long> timestamps = redisTemplate.opsForZSet().rangeByScore(key, currentTime - userProfile.getWindowSizeMillis(), currentTime);
        // 超过窗口限流数
        if (timestamps.size() >= userProfile.getMaxRequestsPerWindow()) {
            return false;
        }

        // 添加新的请求记录
        redisTemplate.opsForZSet().add(key, currentTime, currentTime);
        return true;
    }

    public SliderTimeWindowTrafficLimiter(RedisTemplate<String, Long> redisTemplate, UserProfileService userProfileService) {
        this.redisTemplate = redisTemplate;
        this.userProfileService = userProfileService;
    }
}

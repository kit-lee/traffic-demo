package me.kit.demo.traffic.service;

import me.kit.demo.traffic.bo.TrafficInfo;
import me.kit.demo.traffic.bo.UserProfile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用内存实现简单的时间窗口限流
 * @author kit
 */
public class TimeWindowTrafficLimiter implements TrafficLimiter {
    private final Map<String, TrafficInfo> userTrafficMap = new ConcurrentHashMap<>();
    private UserProfileService userProfileService;

    @Override
    public boolean isAllow(String userId) {
        long currentTime = System.currentTimeMillis();

        TrafficInfo window = userTrafficMap.computeIfAbsent(userId, k -> new TrafficInfo(currentTime, 0));
        UserProfile userProfile = userProfileService.getUserProfile(userId);

        long currentWindowStart = window.getStartTime();
        long nextWindowStart = currentWindowStart + userProfile.getWindowSizeMillis();
        // 超过窗口限流数
        if (currentTime >= nextWindowStart) {
            window.reset(currentTime);
        }

        if (window.getCount() >= userProfile.getMaxRequestsPerWindow()) {
            return false;
        }

        // 请求数+1
        window.counter();
        return true;
    }

    public TimeWindowTrafficLimiter(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }
}

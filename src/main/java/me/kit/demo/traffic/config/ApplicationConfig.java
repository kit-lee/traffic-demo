package me.kit.demo.traffic.config;

import me.kit.demo.traffic.bo.UserProfile;
import me.kit.demo.traffic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserProfileService userProfileService(){
        return new MemoryUserProfileService();
    }

    // 也可以使用redis实现
//    @Bean
//    public UserProfileService redisProfileService(@Autowired RedisTemplate<String, UserProfile> redisTemplate){
//        return new RedisUserProfileService(redisTemplate);
//    }

    @Bean
    public TrafficLimiter trafficLimiter(){
        return new TimeWindowTrafficLimiter(userProfileService());
    }

    // 滑动时间窗口算法实现
//    @Bean
//    public TrafficLimiter sliderTimeWindowTrafficLimiter(@Autowired RedisTemplate<String, Long> redisTemplate,
//                                                         @Autowired UserProfileService userProfileService){
//        return new SliderTimeWindowTrafficLimiter(redisTemplate, userProfileService);
//    }

}

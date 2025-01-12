package me.kit.demo.traffic.service;

import jakarta.annotation.PostConstruct;
import me.kit.demo.traffic.bo.UserProfile;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Optional;

public class RedisUserProfileService implements UserProfileService, ApplicationRunner {
    private static final String USER_PROFILE_KEY_PREFIX = "user:profile:";
    private RedisTemplate<String, UserProfile> redisTemplate;

    public RedisUserProfileService(RedisTemplate<String, UserProfile> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserProfile getUserProfile(String userId) {
        Optional<UserProfile> profile = Optional.ofNullable(redisTemplate.opsForValue().get(USER_PROFILE_KEY_PREFIX+userId));
        return profile.orElse(new UserProfile(60000, 0));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisTemplate.opsForValue().set(USER_PROFILE_KEY_PREFIX+"user1", new UserProfile(60000,10000));
        redisTemplate.opsForValue().set(USER_PROFILE_KEY_PREFIX+"user2", new UserProfile(60000,500));
        redisTemplate.opsForValue().set(USER_PROFILE_KEY_PREFIX+"user3", new UserProfile(60000,400));
        redisTemplate.opsForValue().set(USER_PROFILE_KEY_PREFIX+"user4", new UserProfile(60000,300));
    }
}

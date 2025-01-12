package me.kit.demo.traffic.service;

import me.kit.demo.traffic.bo.UserProfile;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取用户限流配置的服务类
 * @author kit
 */
public class MemoryUserProfileService implements UserProfileService {
    /**
     * 在这个例子中我们将所有用户的限流配置存放在内存中，在分布式服务中应该使用redis代替
     * 当然分布式环境要加载到内存中也是可以的，只要考虑做好各实例间的同步
     */
    private static final Map<String, UserProfile> USER_PROFILE_MAP = new ConcurrentHashMap<>();

    /**
     * 加载数据，思路是微服务在启动时执行该方法，然后往内存或redis中加载数据，后边都是在内存或redis中获取所需数据
     * 题目中是提到可以用mongodb，所以完整版本应该从mongodb中读取这些用户配置，然后写入内存或redis
     */
    private void initData(){
        USER_PROFILE_MAP.put("user1", new UserProfile(60000,10000));
        USER_PROFILE_MAP.put("user2", new UserProfile(60000,500));
        USER_PROFILE_MAP.put("user3", new UserProfile(60000,400));
        USER_PROFILE_MAP.put("user4", new UserProfile(60000,300));
    }

    public MemoryUserProfileService() {
        initData();
    }

    @Override
    public UserProfile getUserProfile(String userId) {
        Optional<UserProfile> profile = Optional.ofNullable(USER_PROFILE_MAP.get(userId));
        return profile.orElse(new UserProfile(60000, 0));
    }
}

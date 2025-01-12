package me.kit.demo.traffic.service;

import me.kit.demo.traffic.bo.UserProfile;

public interface UserProfileService {
    /**
     * 获取一个用户配置
     * @param userId
     * @return
     */
    UserProfile getUserProfile(String userId);
}

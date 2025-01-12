package me.kit.demo.traffic.bo;

/**
 * @author kit
 */
public class UserProfile {
    /**
     * 单个时间窗口的大小
     * 单位：毫秒
     */
    private final long windowSizeMillis;
    /**
     * 每个时间窗口的最大请求数
     */
    private final int maxRequestsPerWindow;

    public long getWindowSizeMillis() {
        return windowSizeMillis;
    }

    public int getMaxRequestsPerWindow() {
        return maxRequestsPerWindow;
    }

    public UserProfile(long windowSizeMillis, int maxRequestsPerWindow) {
        this.windowSizeMillis = windowSizeMillis;
        this.maxRequestsPerWindow = maxRequestsPerWindow;
    }
}

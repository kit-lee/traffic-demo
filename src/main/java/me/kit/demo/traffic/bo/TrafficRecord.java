package me.kit.demo.traffic.bo;

/**
 * 用户的访问记录，后边可以作为实体类持久化到数据库中
 * @author kit
 */
public class TrafficRecord {
    private String userId;
    private String apiEndpoint;
    private Long timestamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public TrafficRecord(String userId, String apiEndpoint, Long timestamp) {
        this.userId = userId;
        this.apiEndpoint = apiEndpoint;
        this.timestamp = timestamp;
    }
}

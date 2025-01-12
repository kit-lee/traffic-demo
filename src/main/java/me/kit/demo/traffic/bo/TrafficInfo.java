package me.kit.demo.traffic.bo;

/**
 * 用于内存方式的时间窗口请求计数模型
 * @author kit
 */
public class TrafficInfo {
    // start time of current window
    private long startTime;
    // request count of current window
    private int count;

    public void reset(long currentTime) {
        startTime = currentTime;
        count = 0;
    }

    public TrafficInfo(long startTime, int count) {
        this.startTime = startTime;
        this.count = count;
    }

    public long getStartTime() {
        return startTime;
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized void counter(){
        this.count++;
    }
}

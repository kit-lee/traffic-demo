package me.kit.demo.traffic_accounting;

import me.kit.demo.traffic.TrafficAccountingDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {TrafficAccountingDemoApplication.class})
@AutoConfigureMockMvc
class TrafficAccountingDemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * 模拟user1到user4四个用户并发500个请求的情况
	 * 用户限流配置见MemoryUserProfileService 的 initData方法
	 * user1限流每分钟1000个请求，预计所有500个请求均通过
	 * user2限流每分钟500个请求，预计所有500个请求均通过
	 * user3限流每分钟400个请求，预计在第401个请求开始被限流
	 * user4限流每分钟300个请求，预诗在第301个请求开始被限流
	 * @throws InterruptedException
	 */
	@Test
	public void testcase() throws InterruptedException {
		int numberOfUsers = 4;
		int numberOfRequestsPerUser = 500;
		long startTime = System.currentTimeMillis();
		CountDownLatch latch = new CountDownLatch(numberOfUsers * numberOfRequestsPerUser);
		final Map<String, Integer> passCountMap = new ConcurrentHashMap<>();

		for (int i = 1; i <= numberOfUsers; i++) {
			final String userId = "user"+i;
			passCountMap.put(userId, 0);
			for (int j = 1; j <= numberOfRequestsPerUser; j++) {
				final int requestNumber = j;
				new Thread(() -> {
					try {
						int randomApi = (int) (Math.random() * 2) + 1;
						MvcResult result = switch (randomApi){
							case 1 -> mockMvc.perform(MockMvcRequestBuilders.get("/apis/api1?userId=" + userId))
										.andReturn();
							case 2 -> mockMvc.perform(MockMvcRequestBuilders.post("/apis/api2?userId=" + userId))
									.andReturn();
							case 3 -> mockMvc.perform(MockMvcRequestBuilders.put("/apis/api3?userId=" + userId))
									.andReturn();
							default -> null;
						};
						System.out.println(userId + ", request number:" + requestNumber + ", api" + randomApi + ":" + result.getResponse().getContentAsString());
						if(result.getResponse().getStatus()==200){
							passCountMap.computeIfPresent(userId, (k,v) -> v + 1);
						}
					} catch (Exception e) {
                        e.printStackTrace();
                    } finally {
						latch.countDown();
					}
				}).start();
			}
		}

		latch.await();
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;

        assertEquals(500, (int) passCountMap.get("user1"), "user1限流数不正常");
        assertEquals(500, (int) passCountMap.get("user2"), "user2限流数不正常");
        assertEquals(400, (int) passCountMap.get("user3"), "user3限流数不正常");
        assertEquals(300, (int) passCountMap.get("user4"), "user4限流数不正常");

		System.out.println("Total time taken: " + duration + " ms");

	}

}

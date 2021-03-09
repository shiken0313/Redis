package com.itcast;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;




import com.itcast.utils.RedisUtils;

@SpringBootTest
class Redis02SpringbootApplicationTests {

	@Autowired
	private RedisUtils redisUtils;

	@Test
	void contextLoads() {
		redisUtils.set("user", "hee");
		Object object = redisUtils.get("user");
		System.out.println(object);
	}

}

package com.didispace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.didispace.redis.RedisService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {
	  @Autowired
	    @Qualifier("siteRedisService")
	    private RedisService siteRedisService;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Override
	public void run(String... arg0) throws Exception {
		siteRedisService.set("q", "1");
		// TODO Auto-generated method stub
		LOGGER.info("初始化开始。。。" + siteRedisService.get("q"));
		LOGGER.info("初始化结束。。。");
	}
}

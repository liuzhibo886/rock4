package com.lzb.rock.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.lzb.rock.base.BaseApplication;

import lombok.extern.slf4j.Slf4j;

@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = { "com.lzb" })
@Slf4j
@EnableFeignClients(basePackages = { "com.lzb" })
@EnableMongoAuditing
public class NettyApplication extends BaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
		log.warn("==================启动成功==========================");
		log.info("==================启动成功==========================");
	}
}

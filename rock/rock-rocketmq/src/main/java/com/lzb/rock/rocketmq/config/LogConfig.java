package com.lzb.rock.rocketmq.config;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.lzb.rock.base.aop.annotation.MqProperties;
import com.lzb.rock.base.facade.IMqEnum;
import com.lzb.rock.rocketmq.facade.IRocketMqMessageListenerConcurrently;
import com.lzb.rock.rocketmq.facade.IRocketMqMessageListenerOrderBy;
import com.lzb.rock.rocketmq.properties.RocketMQProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 消费者
 * 
 * @author lzb
 * @date 2020年8月26日上午11:53:42
 */
@Slf4j
@Configuration
@Order(value = Integer.MIN_VALUE)
public class LogConfig implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.setProperty("rocketmq.client.logUseSlf4j", "true");
	}
}

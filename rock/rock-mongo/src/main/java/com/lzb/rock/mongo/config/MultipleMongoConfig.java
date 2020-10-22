package com.lzb.rock.mongo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import com.lzb.rock.base.util.UtilString;
import com.mongodb.ConnectionString;
import lombok.extern.slf4j.Slf4j;

/**
 * 多数据源开启
 * 
 * @author lzb
 * @date 2020年9月9日下午2:49:16
 */
@Configuration
@Slf4j
@ConditionalOnProperty(name = "spring.data.mongodb.two.uri", matchIfMissing = false)
public class MultipleMongoConfig {

	@Value(value = "${spring.data.mongodb.two.uri:}")
	String twoUri;

	@Value(value = "${spring.data.mongodb.uri:}")
	String defaultUri;

	@Bean("two")
	public MongoTemplate twoMongoTemplate() throws Exception {
		if (UtilString.isBlank(twoUri)) {
			return null;
		}
		log.info("开启第二个mongodb 数据源");
		SimpleMongoClientDbFactory factory = new SimpleMongoClientDbFactory(new ConnectionString(twoUri));
		return new MongoTemplate(factory);
	}

	@Bean
	@Primary
	public MongoTemplate defaulMongoTemplate() throws Exception {

		log.info("开启默认 mongodb 数据源");
		SimpleMongoClientDbFactory factory = new SimpleMongoClientDbFactory(new ConnectionString(defaultUri));

		return new MongoTemplate(factory);
	}

}

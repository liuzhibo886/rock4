package com.lzb.rock.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * 扫描dao或者是Mapper接口
 * 
 * @author lzb
 * @Date 2019年7月31日 下午5:03:28
 */
@Configuration
@MapperScan("com.**.mapper.mysql")
public class BaseMybatisPlusConfig {
	/**
	 * mybatis-plus 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}

	/**
	 * 乐观锁mybatis插件
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}

	/*
	 * 开启逻辑删除(3.1.1开始不再需要这一步)：
	 */
//	@Bean
//	public ISqlInjector sqlInjector() {
//		return new LogicSqlInjector();
//	}
}

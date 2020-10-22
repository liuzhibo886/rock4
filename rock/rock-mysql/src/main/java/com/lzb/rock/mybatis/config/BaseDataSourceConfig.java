package com.lzb.rock.mybatis.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.lzb.rock.base.enums.RockEnum;
import com.lzb.rock.base.exception.RockClientException;
import com.lzb.rock.base.properties.RockProperties;
import com.lzb.rock.mybatis.properties.DruidProperties;
import com.lzb.rock.mybatis.properties.MysqlMutiDatasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源配置 由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
 */
@Configuration
@Slf4j
@EnableTransactionManagement(order = 2)
public class BaseDataSourceConfig {

	@Autowired
	DruidProperties druidProperties;
	@Autowired
	RockProperties rockProperties;
	@Autowired
	MysqlMutiDatasource mutiDatasource;
	/**
	 * 初始化数据源集合
	 */
	public static HashMap<Object, Object> dataSourceMap = new HashMap<Object, Object>();

	/**
	 * 数据源配置，可能多个数据源 当前使用mycat，每一线程独立使用一个数据源 此处创建多个数据源
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		// 多数据源
		if (rockProperties.mutiDatasourceOnOff) {
			if (StringUtils.isBlank(rockProperties.defaultDatasource)) {
				throw new RockClientException(RockEnum.DATA_SOURCE_ERR, "默认数据源为空");
			}
			DynamicDataSource dynamicDataSource = new DynamicDataSource();

			Map<String, String> dataSourcePro = mutiDatasource.getDatasource();
			Set<String> keys = new TreeSet<String>();
			for (String dataSourceKey : dataSourcePro.keySet()) {
				if (StringUtils.isNotBlank(dataSourceKey)) {
					String[] arr = dataSourceKey.split("_");
					if (arr != null && arr.length == 2 && StringUtils.isNotBlank(arr[1])) {
						keys.add(arr[1]);
					}
				}
			}
			log.info("====================>启用多数据源初始化");
			for (String key : keys) {
				String url = dataSourcePro.get("url_" + key);
				String username = dataSourcePro.get("username_" + key);
				String password = dataSourcePro.get("password_" + key);
				String filters = dataSourcePro.get("filters_" + key);
				if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(url) && StringUtils.isNotBlank(username)
						&& StringUtils.isNotBlank(password) && StringUtils.isNotBlank(filters)) {
					DruidDataSource dataSource = new DruidDataSource();
					druidProperties.setUrl(url);
					druidProperties.setUsername(username);
					druidProperties.setPassword(password);
					druidProperties.setFilters(filters);

					druidProperties.config(dataSource);
					dataSourceMap.put(key, dataSource);

					log.info("初始化数据源;datasourcename={};username={};url={}", key, username, url);
				} else {
					throw new RockClientException(RockEnum.DATA_SOURCE_ERR,
							"默认数据源参数异常,datasourcename=" + key + ",username=" + username + ",filters=" + filters);
				}
			}
			// 判断默认数据源是否存在

			if (dataSourceMap.get(rockProperties.defaultDatasource) == null) {
				throw new RockClientException(RockEnum.DATA_SOURCE_ERR,
						"默认数据源:" + rockProperties.defaultDatasource + "未初始化");
			}
			// 设置默认数据源
			dynamicDataSource.setDefaultTargetDataSource(dataSourceMap.get(rockProperties.defaultDatasource));
			dynamicDataSource.setTargetDataSources(dataSourceMap);
			// dynamicDataSource.setResolvedDataSources(dataSourceMap);

			return dynamicDataSource;
		} else {// 单数据源

			log.info("====================>启用单数据源");
			DruidDataSource dataSource = new DruidDataSource();
			druidProperties.config(dataSource);
			return dataSource;
		}

	}

	/**
	 * 配置事物管理器
	 */
//	@Bean
	public SpringManagedTransactionFactory transactionManager() {
		SpringManagedTransactionFactory transactionFactory = new SpringManagedTransactionFactory();
		return transactionFactory;
	}

}

package com.lzb.rock.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.lzb.rock.base.properties.RockProperties;

import io.swagger.annotations.Api;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 启动基类
 * 
 * @author lzb 2018年2月8日 上午11:41:55
 */
@Api(tags = { "默认接口" })
public abstract class BaseApplication extends BaseControllerExceptionAdvice {

	@Autowired
	RockProperties rockProperties;

	@GetMapping("/home")
	@ResponseBody
	public String home() {
		return "Hello world";
	}

	@GetMapping("/info")
	@ResponseBody
	public String info() {
		return "info";
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RibbonRest initializationRibbonRest() {
		RibbonRest ribbonRest = new RibbonRest();
		return ribbonRest;
	}
}

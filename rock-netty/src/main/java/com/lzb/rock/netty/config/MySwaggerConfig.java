package com.lzb.rock.netty.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import lombok.extern.slf4j.Slf4j;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://127.0.0.1:8080/swagger
 */
@EnableSwagger2
@Configuration
@Slf4j
public class MySwaggerConfig {

	public static final String DEFAULT_PATH = "/rock/netty";

	@Value("${spring.profiles.active}")
	String active;

	/**
	 * 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
	 * http://127.0.0.1:15027/swagger-ui.html
	 * https://blog.csdn.net/u010963948/article/details/72476854
	 * 
	 * @return
	 */
	@Bean
	public Docket createRestApi() {// 创建API基本信息
		Docket docket = new Docket(DocumentationType.SWAGGER_2);

		/**
		 * 类型替换
		 */
		docket.directModelSubstitute(ObjectId.class, String.class);

		docket.apiInfo(apiInfo());
		log.info("active==============>{}", active);
		// 是否开启，false 关闭
		if (active.equals("prod")) {
			docket.enable(false);
		} else {
			docket.enable(true);
		}

		ApiSelectorBuilder apiSelectorBuilder = docket.select();
		// 扫描路径
		apiSelectorBuilder.apis(apis());
		apiSelectorBuilder.paths(PathSelectors.any());
		apiSelectorBuilder.paths(Predicates.not(PathSelectors.regex("/error.*")));
		apiSelectorBuilder.build();
		// docket.globalOperationParameters(getOperationParameters());
		return docket;
	}

	/**
	 * 设置扫描包路径
	 * 
	 * @return
	 */
	public Predicate<RequestHandler> apis() {
		Predicate<RequestHandler> selector = Predicates.or(RequestHandlerSelectors.basePackage("com.lzb.tell"),
				RequestHandlerSelectors.basePackage("com.tell"));
		return selector;
	}

	/**
	 * 创建API的基本信息，这些信息会在Swagger UI中进行显示
	 */
	public ApiInfo apiInfo() {

		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		// 联系人
		Contact contact = new Contact("lzb", "", "");
		// 标题
		apiInfoBuilder.title("tell netty模块");
		// 描述
		apiInfoBuilder.description("API描述");
		// 联系人
		apiInfoBuilder.contact(contact);
		// 版本
		apiInfoBuilder.version("1.0.0");

		return apiInfoBuilder.build();
	}

	/**
	 * 配置全局默认参数
	 * 
	 * @date 2020年7月17日下午8:04:25
	 * @return
	 */
	public List<Parameter> getOperationParameters() {
		ParameterBuilder tokenBuilder = new ParameterBuilder();
		Parameter tokenParameter = tokenBuilder.name("x-csrf-token").description("token")
				.modelRef(new ModelRef("string")).parameterType("header").required(true)
				.defaultValue("5bc054f9ba7f837988e0ad20").build();

		ParameterBuilder versionBuilder = new ParameterBuilder();
		Parameter versionParameter = versionBuilder.name("x-client-version").description("app版本")
				.modelRef(new ModelRef("string")).parameterType("header").required(true).defaultValue("1.0").build();

		ParameterBuilder platformBuilder = new ParameterBuilder();
		Parameter platformParameter = platformBuilder.name("x-client-platform").description("平台")
				.modelRef(new ModelRef("string")).parameterType("header").required(true).defaultValue("android")
				.build();

		List<Parameter> operationParameters = new ArrayList<Parameter>();
		operationParameters.add(tokenParameter);
		operationParameters.add(versionParameter);
		operationParameters.add(platformParameter);
		return operationParameters;
	}

	/**
	 * SwaggerUI资源访问
	 *
	 * @param servletContext
	 * @param order
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SimpleUrlHandlerMapping swaggerUrlHandlerMapping(ServletContext servletContext,
			@Value("${swagger.mapping.order:10}") int order) throws Exception {
		SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
		Map<String, ResourceHttpRequestHandler> urlMap = new HashMap<>();
		{
			PathResourceResolver pathResourceResolver = new PathResourceResolver();
			pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/webjars/"));
			pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

			ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
			resourceHttpRequestHandler
					.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/webjars/")));
			resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
			resourceHttpRequestHandler.setServletContext(servletContext);
			resourceHttpRequestHandler.afterPropertiesSet();
			// 设置新的路径
			urlMap.put(DEFAULT_PATH + "/webjars/**", resourceHttpRequestHandler);
		}
		{
			PathResourceResolver pathResourceResolver = new PathResourceResolver();
			pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/"));
			pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

			ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
			resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/")));
			resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
			resourceHttpRequestHandler.setServletContext(servletContext);
			resourceHttpRequestHandler.afterPropertiesSet();
			// 设置新的路径
			urlMap.put(DEFAULT_PATH + "/**", resourceHttpRequestHandler);
		}
		urlHandlerMapping.setUrlMap(urlMap);
		// 调整DispatcherServlet关于SimpleUrlHandlerMapping的排序
		urlHandlerMapping.setOrder(order);
		return urlHandlerMapping;
	}

}

package com.lzb.rock.base.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import io.swagger.models.Swagger;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl;

/**
 * 获取文档的时候修改
 * 
 * @author lzb
 * @date 2020年8月18日下午3:03:17
 */
@Primary
@Component("ServiceModelToSwagger2Mapper")
@ConditionalOnBean(DescriptionResolver.class)
public class MySwaggerServiceModelToSwagger2MapperImpl extends ServiceModelToSwagger2MapperImpl {
	@Override
	public Swagger mapDocumentation(Documentation from) {

		return super.mapDocumentation(from);
	}
}

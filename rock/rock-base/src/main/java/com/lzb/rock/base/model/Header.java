package com.lzb.rock.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 请求头内容
 * 
 * @author lzb
 * @date 2020年7月16日上午11:07:00
 */
@Data
public class Header {

	@ApiModelProperty(value = "登录token")
	String token;

	@ApiModelProperty(value = "登录用户ID")
	String userId;

	@ApiModelProperty(value = "app版本")
	String appVersion;

	@ApiModelProperty(value = "来源，android，ios")
	String platform;
	
	@ApiModelProperty(value = "设备唯一编码")
	String deviceId;
	
	@ApiModelProperty(value = "创建时间")
	String createTime;

}

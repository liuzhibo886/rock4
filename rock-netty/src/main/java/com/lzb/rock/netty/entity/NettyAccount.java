package com.lzb.rock.netty.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * netty账号对象
 * 
 * @author liuzhibo
 *
 */
@Data
@Document(collection = "nettyAccount")
public class NettyAccount {

	@ApiModelProperty(value = "账号")
	@Indexed
	String account;

	@ApiModelProperty(value = "绑定的ChannelId")
	@Indexed
	String channelId;

	@ApiModelProperty(value = "加入的房间ID")
	List<String> roomIds;

	@ApiModelProperty(value = "账号状态,0 在线,1断开连接,2离线")
	Integer state;

	@ApiModelProperty(value = "登录平台")
	String platform;

	@ApiModelProperty(value = "设备唯一标识")
	String deviceId;

	@ApiModelProperty(value = "版本号")
	String version;

	@ApiModelProperty(value = "建立连接次数")
	Long count0;

	@ApiModelProperty(value = "断开次数")
	Long count1;

	@ApiModelProperty(value = "离线次数")
	Long count2;

	@ApiModelProperty(value = "加入房间次数")
	Long count3;

	@ApiModelProperty(value = "退出房间次数")
	Long count4;

	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	Date createTime;

	@ApiModelProperty(value = "最后修改时间")
	@LastModifiedDate
	Date lastTime;

}

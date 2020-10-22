package com.lzb.rock.netty.dto;

import com.lzb.rock.base.aop.annotation.RelEnum;
import com.lzb.rock.netty.enums.EventEnum;
import com.lzb.rock.netty.enums.PubTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息发送对象
 * 
 * @author lzb
 * @Date 2019年9月30日 下午6:00:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NettyMsg {

	public NettyMsg(String pubType, String event, String body, String sendAccount, String receiveAccount) {
		super();
		this.pubType = pubType;
		this.event = event;
		this.body = body;
		this.sendAccount = sendAccount;
		this.receiveAccount = receiveAccount;
	}

	@ApiModelProperty(value = "消息ID")
	String msgId;
	
	@ApiModelProperty(value = "MQ消息ID")
	String mqId;

	@ApiModelProperty(value = "发布类型")
	@RelEnum(PubTypeEnum.class)
	String pubType;

	@ApiModelProperty(value = "事件")
	@RelEnum(EventEnum.class)
	String event;

	@ApiModelProperty(value = "消息内容")
	String body;

	@ApiModelProperty(value = "发送者账号")
	String sendAccount;

	@ApiModelProperty(value = "接收消息账号")
	String receiveAccount;

	@ApiModelProperty(value = "超时时间,秒")
	Long overtime = 300L;

}

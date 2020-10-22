package com.lzb.rock.netty.enums;

import com.lzb.rock.base.facade.IMqEnum;

/**
 * 消息枚举
 * 
 * @author lzb
 * @date 2020年9月17日下午5:42:39
 */
public enum MqEnum implements IMqEnum {

	/**
	 * netty 消息
	 */
	NETTY_MSG("NETTY_MSG", "NETTY_MSG"),

	;

	private String topic;

	private String tag;

	MqEnum(String topic, String tag) {
		this.topic = topic;
		this.tag = tag;
	}

	@Override
	public String getTopic() {
		return topic;
	}

	@Override
	public String getTag() {
		return tag;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}

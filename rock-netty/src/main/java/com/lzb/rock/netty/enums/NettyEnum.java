package com.lzb.rock.netty.enums;

import com.google.common.collect.ImmutableMap;
import com.lzb.rock.base.facade.IEnum;

/**
 * 状态枚举类
 * 
 * @author lzb 2018年2月1日 下午3:50:38
 */
public enum NettyEnum implements IEnum {

	// 系统级别错误码,系统级别错误码小于1000
	JOIN_ROOM_ERR("3001", "加入房间失败"), 
	
	LEAVE_ROOM_ERR("3002", "退出房间失败"),
	
	IS_ONT_ON_LINE("3003", "用户不在线"),
	
	BIND_ERR("3004", "绑定失败"),

	
	
	
	
	
	
	
	
	
	
	
	
	;


	private String code;
	private String msg;

	NettyEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 枚举类型的判断和获取
	 * 
	 * @param code 错误码
	 * @return 返回错误码对应的枚举信息
	 */
	public static NettyEnum statusOf(String code) {
		for (NettyEnum resultEnum : values()) {
			if (resultEnum.getCode().equals(code)) {
				return resultEnum;
			}
		}
		return null;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg == null ? "" : msg.trim();
	}

	/**
	 * 枚举转换为MAP
	 * 
	 * @return
	 */
	public ImmutableMap<String, String> toMap() {
		return ImmutableMap.<String, String>builder().put("msg", msg).put("code", code).build();
	}
}

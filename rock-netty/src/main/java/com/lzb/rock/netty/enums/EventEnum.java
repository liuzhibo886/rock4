package com.lzb.rock.netty.enums;

import com.lzb.rock.base.facade.IEnum;

/**
 * 事件枚举
 * 
 * @author admin
 * @Date 2019年9月30日 下午6:07:44
 */
public enum EventEnum implements IEnum {

	HEART("HEART", "心跳"),

	HEART_NOT("HEART_NOT", "回应心跳"),

	JOINROOM("JOINROOM", "加入房间"),

	LEAVEROOM("LEAVEROOM", "退出房间"),

	CLOSEROOM("CLOSEROOM", "关闭房间"),

	DISCONNECT("DISCONNECT", "关闭连接"),

	REGISTER("REGISTER", "注册消息"),

	;

	private String code;
	private String msg;

	EventEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param text the text to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}

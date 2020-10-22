package com.lzb.rock.netty.enums;

import com.lzb.rock.base.facade.IEnum;

/**
 * 广播类型
 * 
 * @author admin
 * @Date 2019年9月30日 下午6:07:44
 */
public enum PubTypeEnum implements IEnum{

	NO("NO", "不广播"),

	ALL("ALL", "全员"),

	ROOM("ROOM", "房间"),

	ONE("ONE", "点对点");

	private String code;
	private String msg;

	PubTypeEnum(String code, String msg) {
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

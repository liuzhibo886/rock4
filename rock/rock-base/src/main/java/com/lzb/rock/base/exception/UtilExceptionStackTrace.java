package com.lzb.rock.base.exception;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局异常处理器
 * 
 * @author lzb 2018年1月12日 上午10:43:27
 */
public class UtilExceptionStackTrace {
	private Logger log = LoggerFactory.getLogger(UtilExceptionStackTrace.class);

	/**
	 * 获取异常堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Exception e) {
		String exception = ExceptionUtils.getFullStackTrace(e);
		return exception;
	}

}

package com.lzb.rock.base.util;

import java.util.List;

import org.apache.commons.collections.ListUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * list 操作公共类
 * @author liuzhibo
 *
 */
public class UtilList extends ListUtils{


	/**
	 * 复制list，属性名称必须保持一致
	 * 
	 * @param source
	 * @param targetClass
	 * @return
	 */
	public static <T> List<T> javaList(Object source, Class<T> targetClass) {
		List<T> list = null;
		if (source != null) {
			String jsonStr = UtilJson.getStr(source);
			JSONArray obj = (JSONArray) JSON.parse(jsonStr);
			list = obj.toJavaList(targetClass);

		}
		return list;
	}

}

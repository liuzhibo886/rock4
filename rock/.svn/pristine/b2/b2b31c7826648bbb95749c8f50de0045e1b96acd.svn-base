package com.lzb.rock.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.lzb.rock.base.enums.RockEnum;
import com.lzb.rock.base.exception.RockException;

import lombok.extern.slf4j.Slf4j;

/**
 * 反射操作类
 * 
 * @author liuzhibo
 *
 *         2020年1月8日 下午3:20:44
 */
@Slf4j
public class UtilClass {

	/**
	 * 获取对象中所有的Field
	 * 
	 * @author liuzhibo
	 * @date 2020年1月8日 下午3:29:11
	 * @param clazz
	 */
	public static Field[] getDeclaredFields(Class<? extends Object> clazz) {

		Field[] fields = clazz.getDeclaredFields();
		return fields;
	}

	/**
	 * 反射获取对象属性值
	 * 
	 * @author liuzhibo
	 * @date 2019年12月23日 下午6:39:06
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static <T> T getFieldValue(String fieldName, Object obj) {

		Class<? extends Object> clazz = obj.getClass();
		String methodName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
		T value = null;
		Object objValue = null;
		try {
			Method methodGet = clazz.getMethod("get" + methodName);
			objValue = methodGet.invoke(obj);
		} catch (Exception e) {
			log.info("fieldName：{}不存在", fieldName);
		}

		if (objValue != null) {
			value = (T) objValue;
		}

		return value;
	}

	/**
	 * 反射注入取对象属性值
	 * 
	 * @author liuzhibo
	 * @date 2019年12月23日 下午6:39:06
	 * @param <T>
	 * @param obj
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static void setFieldValue(String fieldName, Object fieldValue, Object obj) {
		Class<? extends Object> clazz = obj.getClass();
		Field field;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 动态修改注解的值
	 * 
	 * @author liuzhibo
	 * @date 2020年1月20日 上午11:35:25
	 * @param fieldName
	 * @param fieldValue
	 * @param obj
	 */
	public static void setAnnotationFieldValue(String fieldName, Object fieldValue, Object obj) {

		InvocationHandler ih = Proxy.getInvocationHandler(obj);
		try {
			Field memberValuesField = ih.getClass().getDeclaredField("memberValues");
			memberValuesField.setAccessible(true);
			Map memberValues = (Map) memberValuesField.get(ih);
			memberValues.put(fieldName, fieldValue); // set value to false
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 字符串转换对应对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static Object getJavaBeanByString(String str, Type type) {
		if (str == null) {
			return null;
		}
		// 判断是不是json字符串
		boolean isJson = UtilJson.isJsonString(str);
		// JSON字符串
		if (isJson) {
			Object jsonObj = JSON.parse(str);
			if (jsonObj instanceof JSONObject) {
				JSONObject obj = (JSONObject) jsonObj;
				return obj.toJavaObject(type);
			} else if (jsonObj instanceof JSONArray) {
				JSONArray obj = (JSONArray) jsonObj;
				return obj.toJavaObject(type);
			}
		}
		// 非JSON字符串
		if (!isJson) {
			String typeName = type.getTypeName();
			if (typeName.equals("int") || typeName.equals("java.lang.Integer")) {
				if (StringUtils.isNotBlank(str)) {
					return Integer.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.String")) {
				return str;
			} else if (typeName.equals("java.lang.Double") || typeName.equals("double")) {
				if (StringUtils.isNotBlank(str)) {
					return Double.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Long") || typeName.equals("long")) {
				if (StringUtils.isNotBlank(str)) {
					return Long.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Short") || typeName.equals("short")) {
				if (StringUtils.isNotBlank(str)) {
					return Short.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Byte") || typeName.equals("byte")) {
				if (StringUtils.isNotBlank(str)) {
					return Byte.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Float") || typeName.equals("float")) {
				if (StringUtils.isNotBlank(str)) {
					return Float.valueOf(str);
				} else {
					return null;
				}
			} else if (typeName.equals("java.lang.Boolean") || typeName.equals("boolean")) {
				if (StringUtils.isNotBlank(str)) {
					return Boolean.valueOf(str);
				} else {
					return false;
				}
			} else if (typeName.equals("java.math.BigDecimal")) {
				if (StringUtils.isNotBlank(str)) {
					return new BigDecimal(str);
				} else {
					return null;
				}
			} else {
				throw new RockException(RockEnum.TYPE_ERR, "类型异常" + typeName);
			}
		}
		throw new RockException(RockEnum.TYPE_ERR, "类型异常" + type.getTypeName());
	}

//	public static <T> T getJavaBeanByString(String str, Class<T> clazz) {
//
//		if (str == null) {
//			return null;
//		}
//		// 判断是不是json字符串
//		boolean isJson = UtilJson.isJsonString(str);
//		// JSON字符串
//		if (isJson) {
//			Object jsonObj = JSON.parse(str);
//			if (jsonObj instanceof JSONObject) {
//				JSONObject obj = (JSONObject) jsonObj;
//				return obj.toJavaObject(clazz);
//			} else if (jsonObj instanceof JSONArray) {
//				JSONArray obj = (JSONArray) jsonObj;
//				return obj.toJavaObject(clazz);
//			}
//		}
//
//		return TypeUtils.cast(str, clazz, ParserConfig.getGlobalInstance());
//	}

	/**
	 * 通过spring的LocalVariableTableParameterNameDiscoverer 获取所有参数值，有构造方法的时候会有问题
	 * 
	 * @param className
	 * @param methodName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static String[] getFieldsNameBySpring(String className, String methodName) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (methodName.equals(method.getName())) {
				String[] params = u.getParameterNames(method);
				return params;
			}
		}
		return null;
	}

}

package com.lzb.rock.netty.controller;
//package com.lzb.tell.netty.controller;
//
//import java.lang.management.BufferPoolMXBean;
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.atomic.AtomicLong;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//import com.lzb.rock.base.holder.SpringContextHolder;
//import com.lzb.rock.base.model.Result;
//import com.lzb.rock.base.util.UtilJson;
//import com.lzb.tell.netty.dto.NettyContext;
//import com.lzb.tell.netty.dto.NettyMsg;
//import com.lzb.tell.netty.server.INettyService;
//import com.lzb.tell.netty.util.MyNettyContext;
//
//import io.netty.util.internal.PlatformDependent;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import sun.management.ManagementFactoryHelper;
//
//@RequestMapping("/tell/netty/log")
//@RestController
//@Api(tags = { "netty控制器" })
//@Slf4j
//public class NettyLogController {
//
//	@GetMapping("/all")
//	@ApiOperation(value = "获取netty 内存数据信息")
//	public Result<JSONObject> all() {
//		ConcurrentMap<String, Set<String>> roomGroup = MyNettyContext.getRoomGroup();
//		Map<String, NettyContext> channelAccountOffLine = MyNettyContext.getChannelAccountOffLine();
//		Map<String, NettyContext> accountChannel = MyNettyContext.getAccountChannel();
//		JSONObject obj = new JSONObject();
//
//		JSONObject cacheNameObj = new JSONObject();
//
//		obj.put("roomGroup", roomGroup);
//		obj.put("channelAccountOffLine", channelAccountOffLine);
//		obj.put("accountChannel", accountChannel);
//
//		cacheNameObj.put("roomGroupSize", roomGroup.size());
//		cacheNameObj.put("channelAccountOffLineSize", channelAccountOffLine.size());
//		cacheNameObj.put("accountChannelSize", accountChannel.size());
//		Integer roomGroupSetSize = 0;
//		for (Set<String> set : roomGroup.values()) {
//			if (set != null) {
//				roomGroupSetSize = roomGroupSetSize + set.size();
//			}
//		}
//		cacheNameObj.put("roomGroupSetSize", roomGroupSetSize);
//
//		EhCacheMapper ehCacheMapper = SpringContextHolder.getBean(EhCacheMapper.class);
//		if (ehCacheMapper != null) {
//			List keys = ehCacheMapper.getKeys("TOKEN_BLACK_LIST");
//			obj.put("blackToken", keys);
//			String[] cacheNames = ehCacheMapper.getCacheNames();
//			for (String cacheName : cacheNames) {
//				keys = ehCacheMapper.getKeys(cacheName);
//				if (keys != null) {
//					cacheNameObj.put(cacheName, keys.size());
//				} else {
//					cacheNameObj.put(cacheName, keys);
//				}
//			}
//		}
//
//		// 最大堆外内存
//		long maxDirectMemory = PlatformDependent.maxDirectMemory();
//		BigDecimal maxDirectMemoryText = new BigDecimal(maxDirectMemory).divide(new BigDecimal(1024 * 1024), 2,
//				BigDecimal.ROUND_HALF_UP);
//		cacheNameObj.put("maxDirectMemory", maxDirectMemoryText + "M");
//
//		AtomicLong directMem = new AtomicLong();
//		Field field = ReflectionUtils.findField(PlatformDependent.class, "DIRECT_MEMORY_COUNTER");
//		field.setAccessible(true);
//		try {
//			directMem = (AtomicLong) field.get(PlatformDependent.class);
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		long directMemGet = directMem.get();
//		BigDecimal directMemGetText = new BigDecimal(directMemGet).divide(new BigDecimal(1024 * 1024), 2,
//				BigDecimal.ROUND_HALF_UP);
//		cacheNameObj.put("directMemGet", directMemGetText + "M");
//
//		// 对于hasCleaner策略的DirectByteBuffer，java.nio.Bits类是有记录堆外内存的使用情况，但是该类是包级别的访问权限，不能直接获取，可以通过MXBean来获取
//		List<BufferPoolMXBean> bufferPoolMXBeans = ManagementFactoryHelper.getBufferPoolMXBeans();
//
//		Integer index = 0;
//		for (BufferPoolMXBean bufferPoolMXBean : bufferPoolMXBeans) {
//
//			long directBufferSize = bufferPoolMXBean.getCount();
//			// hasCleaner的DirectBuffer的堆外内存占用大小，单位字节
//			long directBufferMemoryUsed = bufferPoolMXBean.getMemoryUsed();
//			BigDecimal directBufferMemoryUsedText = new BigDecimal(directBufferMemoryUsed)
//					.divide(new BigDecimal(1024 * 1024), 2, BigDecimal.ROUND_HALF_UP);
//
//			cacheNameObj.put("directBufferSize_" + index, directBufferSize);
//			cacheNameObj.put("directBufferMemoryUsed_" + index, directBufferMemoryUsedText.toString() + "M");
//			index++;
//		}
//		// BufferPoolMXBean directBufferMXBean = bufferPoolMXBeans.get(0);
//		// hasCleaner的DirectBuffer的数量
//
//		obj.put("sizeList", cacheNameObj);
//		return new Result<JSONObject>(obj);
//	}
//
//	@GetMapping("/log")
//	@ApiOperation(value = "日志接口")
//	public Result<Void> log() {
//
//		log.info("当前在线人数：{},离线人数：{}", MyNettyContext.getAccountChannel().size(),
//				MyNettyContext.getChannelAccountOffLine().size());
//		for (NettyContext nettyContext : MyNettyContext.getAccountChannel().values()) {
//			log.info("当前在线用户:{};所在房间:{}", nettyContext.getAccount(), UtilJson.getStr(nettyContext.getRoomIds()));
//		}
//		for (Entry<String, Set<String>> entry : MyNettyContext.getRoomGroup().entrySet()) {
//			log.info("房间:{}有{}用户", entry.getKey(), entry.getValue().size());
//		}
//
//		EhCacheMapper ehCacheMapper = SpringContextHolder.getBean(EhCacheMapper.class);
//		if (ehCacheMapper != null) {
//			List keys = ehCacheMapper.getKeys("TOKEN_BLACK_LIST");
//			log.debug("黑名单token:{}", keys);
//		}
//
//		return new Result<Void>();
//	}
//}

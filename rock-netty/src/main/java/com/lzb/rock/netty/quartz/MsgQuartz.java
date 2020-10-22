package com.lzb.rock.netty.quartz;
//package com.lzb.tell.netty.quartz;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.lzb.tell.netty.dto.NettyMsg;
//import com.lzb.tell.netty.util.MyNettyContext;
//
///**
// * 定时检测是否已下线
// * 
// * @author lzb
// * @date 2020年7月23日下午5:04:25
// */
//@Component
//@Async
//public class MsgQuartz {
//
//	@Scheduled(cron = "${quartz.netty.msg:* */3 * * * ?}")
//	@Async
//	public void checkMsg() {
//
//		long thisTime = System.currentTimeMillis();
//
//		Map<String, List<NettyMsg>> msgs = MyNettyContext.getMsgs();
//		Set<String> accounts = msgs.keySet();
//		for (String account : accounts) {
//			List<NettyMsg> msgList = MyNettyContext.getMsg(account);
//			if (msgList == null || msgList.size() < 1) {
//				continue;
//			}
//			List<NettyMsg> msgListNew = new ArrayList<NettyMsg>();
//			for (NettyMsg msg : msgList) {
//				Long maxTime = 1000L * 60L * 5L;
//				Long timeOld = msg.getCreateTime();
//				Long overtime = msg.getOvertime();
//				if (overtime != null && overtime > 0L) {
//					maxTime = overtime * 1000;
//				}
//
//				if (timeOld == null) {
//					timeOld = System.currentTimeMillis();
//					msg.setCreateTime(timeOld);
//				}
//				if (thisTime - timeOld < maxTime) {
//					msgListNew.add(msg);
//				}
//
//			}
//			if (msgListNew.size() > 0) {
//				MyNettyContext.repAllMsg(account, msgListNew);
//			} else {
//				MyNettyContext.delAllMsg(account);
//			}
//		}
//
//	}
//
//}

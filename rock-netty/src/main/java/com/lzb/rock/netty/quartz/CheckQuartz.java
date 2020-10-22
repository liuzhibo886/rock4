package com.lzb.rock.netty.quartz;
//package com.lzb.tell.netty.quartz;
//
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.lzb.rock.base.util.UtilDate;
//import com.lzb.tell.netty.util.MyNettyContext;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 定时检测是否已下线
// * 
// * @author lzb
// * @date 2020年7月23日下午5:04:25
// */
//@Component
//@Async
//public class CheckQuartz {
//
//	@Scheduled(cron = "${netty.checkOffLine:*/10 * * * * ?}")
//	@Async
//	public void checkOffLine() {
//		MyNettyContext.checkOffLine();
//	}
//
//	@Scheduled(cron = "${netty.checkDisconnect:*/10 * * * * ?}")
//	@Async
//	public void checkDisconnect() {
//		MyNettyContext.checkDisconnect();
//	}
//
//	@Scheduled(cron = "${netty.check.log:*/10 * * * * ?}")
//	@Async
//	public void log() {
//		MyNettyContext.log();
//	}
//
//}

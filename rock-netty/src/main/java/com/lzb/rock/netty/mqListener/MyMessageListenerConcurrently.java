//package com.lzb.rock.netty.mqListener;
//
//import java.util.List;
//
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.lzb.rock.base.aop.annotation.Log;
//import com.lzb.rock.base.aop.annotation.MqProperties;
//import com.lzb.rock.base.enums.MessageEnum;
//import com.lzb.rock.base.facade.IMqEnum;
//import com.lzb.rock.base.util.UtilJson;
//import com.lzb.rock.base.util.UtilString;
//import com.lzb.rock.netty.dto.NettyMsg;
//import com.lzb.rock.netty.enums.MqEnum;
//import com.lzb.rock.netty.server.INettyService;
//import com.lzb.rock.rocketmq.facade.IRocketMqMessageListenerConcurrently;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 并发消费
// * 
// * @author lzb
// * @date 2020年8月25日上午10:26:46
// */
//@Slf4j
//@Component
//@MqProperties(consumeThreadMin = 10, consumerConsumeThreadMax = 100, consumeMessageBatchMaxSiz = 10, messageEnum = MessageEnum.BROADCASTING)
//public class MyMessageListenerConcurrently implements IRocketMqMessageListenerConcurrently {
//
//	@Autowired
//	INettyService nettyService;
//
//	@Override
//	@Log(before = false, end = false)
//	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//		for (MessageExt messageExt : msgs) {
//			String message = UtilString.toString(messageExt.getBody());
//			log.debug(message);
//			if (UtilString.isBlank(message)) {
//				log.info("消息体为空 msgId:{},topic:{},tags:{},body", messageExt.getMsgId(), messageExt.getTopic(),
//						messageExt.getTags(), messageExt.getBody());
//			}
//			NettyMsg msg = UtilJson.getJavaBean(message, NettyMsg.class);
//			msg.setMqId(messageExt.getMsgId());
//			nettyService.push(msg);
//		}
//		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//	}
//
//	@Override
//	public IMqEnum getMqEnum() {
//		return MqEnum.NETTY_MSG;
//	}
//
//}

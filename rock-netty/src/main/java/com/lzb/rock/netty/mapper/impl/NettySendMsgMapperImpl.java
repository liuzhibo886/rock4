package com.lzb.rock.netty.mapper.impl;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.lzb.rock.netty.entity.NettyRecMsg;
import com.lzb.rock.netty.entity.NettySendMsg;
import com.lzb.rock.netty.mapper.NettySendMsgMapper;
import com.mongodb.client.result.UpdateResult;

@Component
public class NettySendMsgMapperImpl implements NettySendMsgMapper {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public NettySendMsg insert(NettySendMsg nettyMsg) {
		if (nettyMsg.getMsgState() == null) {
			nettyMsg.setMsgState(0);
		}
		if (nettyMsg.getReceipt() == null) {
			nettyMsg.setReceipt(0);
		}
		if (nettyMsg.getSendCount() == null) {
			nettyMsg.setSendCount(0);
		}
		nettyMsg.setCreateTime(new Date());
		nettyMsg.setLastTime(new Date());
		return mongoTemplate.insert(nettyMsg);
	}

	@Override
	public Boolean incSendCountByMsgId(ObjectId msgId, Integer inc) {
		Query query = new Query(Criteria.where("msgId").is(msgId));
		Update update = new Update();
		update.inc("sendCount", inc);
		UpdateResult rs = mongoTemplate.updateFirst(query, update, NettyRecMsg.class);
		return rs.wasAcknowledged();
	}

}

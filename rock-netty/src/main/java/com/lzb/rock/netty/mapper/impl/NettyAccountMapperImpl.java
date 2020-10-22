package com.lzb.rock.netty.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.lzb.rock.mongo.util.UtilMongoUpdate;
import com.lzb.rock.netty.entity.NettyAccount;
import com.lzb.rock.netty.mapper.NettyAccountMapper;
import com.mongodb.client.result.UpdateResult;

@Component
public class NettyAccountMapperImpl implements NettyAccountMapper {
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Boolean upsert(NettyAccount nettyAccount) {
		Query query = new Query(Criteria.where("account").is(nettyAccount.getAccount()));

		Update update = UtilMongoUpdate.set(nettyAccount, "account");
		if (0 == nettyAccount.getState()) {
			update.inc("count0", 1L);
		} else if (1 == nettyAccount.getState()) {
			update.inc("count1", 1L);
		} else if (2 == nettyAccount.getState()) {
			update.inc("count2", 1L);
		}
		UpdateResult rs = mongoTemplate.upsert(query, update, NettyAccount.class);
		return rs.wasAcknowledged();
	}

	@Override
	public NettyAccount findByAccount(String account) {
		Query query = new Query(Criteria.where("account").is(account));
		return mongoTemplate.findOne(query, NettyAccount.class);
	}

	@Override
	public Boolean setRoomId(String account, String roomId) {
		Query query = new Query(Criteria.where("account").is(account));
		Update update = new Update();
		update.inc("count3", 1);
		update.addToSet("roomIds", roomId);
		UpdateResult rs = mongoTemplate.upsert(query, update, NettyAccount.class);
		return rs.wasAcknowledged();
	}

	@Override
	public Boolean pullRoomId(String account, String roomId) {
		Query query = new Query(Criteria.where("account").is(account));
		Update update = new Update();
		update.pull("roomIds", roomId);
		update.inc("count4", 1);
		UpdateResult rs = mongoTemplate.upsert(query, update, NettyAccount.class);
		return rs.wasAcknowledged();
	}

}

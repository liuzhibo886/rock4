package com.lzb.rock.netty.mapper.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.lzb.rock.netty.entity.NettyRecMsg;
import com.lzb.rock.netty.mapper.NettyRecMsgMapper;

@Component
public class NettyRecMsgMapperImpl implements NettyRecMsgMapper {
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public NettyRecMsg insert(NettyRecMsg nettyMsg) {
		nettyMsg.setCreateTime(new Date());
		return mongoTemplate.insert(nettyMsg);
	}

}

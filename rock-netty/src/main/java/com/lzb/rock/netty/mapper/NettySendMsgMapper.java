package com.lzb.rock.netty.mapper;

import org.bson.types.ObjectId;

import com.lzb.rock.netty.entity.NettySendMsg;

public interface NettySendMsgMapper {

	public NettySendMsg insert(NettySendMsg nettyMsg);

	public Boolean incSendCountByMsgId(ObjectId msgId, Integer inc);

}

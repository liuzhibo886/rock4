package com.lzb.rock.netty.mapper;

import com.lzb.rock.netty.entity.NettyAccount;

public interface NettyAccountMapper {

	public Boolean upsert(NettyAccount NettyAccount);
	
	public NettyAccount findByAccount(String account);
	
	public Boolean setRoomId(String account,String roomId);
	
	public Boolean pullRoomId(String account,String roomId);

}

package com.lzb.rock.netty.server.impl;

import org.springframework.stereotype.Service;

import com.lzb.rock.base.model.Result;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.server.INettyPubService;

/**
 * 不广播消息
 * 
 * @author lzb
 * @date 2020年8月17日下午4:42:22
 */
@Service("NO")
public class NettyPubNoServiceImpl implements INettyPubService {

	@Override
	public Result<Void> run(NettyMsg msg) {
		return new Result<Void>();
	}

}

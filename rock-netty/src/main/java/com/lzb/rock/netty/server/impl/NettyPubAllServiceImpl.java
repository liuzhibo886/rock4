package com.lzb.rock.netty.server.impl;

import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzb.rock.base.model.Result;
import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.server.INettyPubService;
import com.lzb.rock.netty.server.INettyService;
import com.lzb.rock.netty.util.MyNettyContext;

import io.netty.channel.ChannelHandlerContext;

/**
 * 全员广播消息
 * 
 * @author lzb
 * @date 2020年8月17日下午4:42:03
 */
@Service("ALL")
public class NettyPubAllServiceImpl implements INettyPubService {

	@Autowired
	INettyService nettyService;

	@Override
	public Result<Void> run(NettyMsg msg) {
		ConcurrentMap<String, ChannelHandlerContext> accountGroup = MyNettyContext.getAccountGroup();
		for (ChannelHandlerContext ctx : accountGroup.values()) {
			nettyService.writeAndFlush(ctx, msg);
		}
		return new Result<Void>();
	}

}

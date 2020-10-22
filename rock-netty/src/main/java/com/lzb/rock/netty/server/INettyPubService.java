package com.lzb.rock.netty.server;

import com.lzb.rock.base.model.Result;
import com.lzb.rock.netty.dto.NettyMsg;

/**
 * 消息广播处理类
 * 
 * @author lzb
 *
 *         2020 0:27:40
 */
public interface INettyPubService {

	public Result<Void> run(NettyMsg msg);
}

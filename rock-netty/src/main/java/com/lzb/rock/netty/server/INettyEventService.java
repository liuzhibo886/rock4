package com.lzb.rock.netty.server;

import com.lzb.rock.base.model.Result;
import com.lzb.rock.netty.dto.NettyMsg;
/**
 * 事件处理类
 * @author lzb
 *
 *2020 0:27:21
 */
public interface INettyEventService {

	public Result<Void> run(NettyMsg msg);
}

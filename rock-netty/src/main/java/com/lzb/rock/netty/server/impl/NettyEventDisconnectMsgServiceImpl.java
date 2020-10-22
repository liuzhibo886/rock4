package com.lzb.rock.netty.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzb.rock.base.model.Result;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.server.INettyEventService;
import com.lzb.rock.netty.server.INettyService;

/**
 * 关闭连接
 * 
 * @author lzb
 * @date 2020年8月17日下午4:41:41
 */
@Service("DISCONNECT")
public class NettyEventDisconnectMsgServiceImpl implements INettyEventService {

	@Autowired
	INettyService nettyService;

	@Override
	public Result<Void> run(NettyMsg msg) {
		Result<Void> rs = nettyService.checkPush(msg);
		if (!rs.check()) {
			return rs;
		}
		String account = msg.getSendAccount();

		return nettyService.disconnect(account);
	}

}

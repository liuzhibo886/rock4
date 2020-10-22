package com.lzb.rock.netty.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzb.rock.base.enums.RockEnum;
import com.lzb.rock.base.model.Result;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.server.INettyPubService;
import com.lzb.rock.netty.server.INettyService;
import com.lzb.rock.netty.util.MyNettyContext;

/**
 * 点对点消息
 * 
 * @author lzb
 * @date 2020年8月17日下午4:42:38
 */
@Service("ONE")
public class NettyPubOneServiceImpl implements INettyPubService {

	@Autowired
	INettyService nettyService;

	@Override
	public Result<Void> run(NettyMsg msg) {
		Result<Void> rs = new Result<Void>();
		if (UtilString.isBlank(msg.getReceiveAccount())) {
			rs.error(RockEnum.PAEAM_ERR, "接收账号不能为空");
			return rs;
		}
		// 判断用户是否在线
		String[] receiveAccounts = msg.getReceiveAccount().split(";");

		for (String account : receiveAccounts) {
			nettyService.writeAndFlush(MyNettyContext.getCtxByAccount(account), msg);
		}

		return rs;
	}

}

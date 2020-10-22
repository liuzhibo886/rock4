package com.lzb.rock.netty.server.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzb.rock.base.enums.RockEnum;
import com.lzb.rock.base.model.Result;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.server.INettyPubService;
import com.lzb.rock.netty.server.INettyService;
import com.lzb.rock.netty.util.MyNettyContext;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 房间消息
 * 
 * @author lzb
 * @date 2020年8月17日下午4:42:48
 */
@Service("ROOM")
@Slf4j
public class NettyPubRoomServiceImpl implements INettyPubService {

	@Autowired
	INettyService nettyService;

	@Override
	public Result<Void> run(NettyMsg msg) {
		Result<Void> rs = new Result<Void>();

		if (UtilString.isBlank(msg.getReceiveAccount())) {
			log.debug("发送房间消息rooId不能为空:{}", msg);
			rs.error(RockEnum.PAEAM_ERR, "接收账号不能为空");
			return rs;
		}
		Set<ChannelHandlerContext> ctxs = MyNettyContext.getCtxsByRoomId(msg.getReceiveAccount());
		if (ctxs != null) {
			for (ChannelHandlerContext ctx : ctxs) {
				nettyService.writeAndFlush(ctx, msg);
			}
		}

		return rs;
	}

}

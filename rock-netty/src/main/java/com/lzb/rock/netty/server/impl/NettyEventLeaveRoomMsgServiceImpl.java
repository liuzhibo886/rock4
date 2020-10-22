package com.lzb.rock.netty.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.enums.RockEnum;
import com.lzb.rock.base.model.Result;
import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.server.INettyEventService;
import com.lzb.rock.netty.server.INettyService;
import com.lzb.rock.netty.util.MyNettyContext;

/**
 * 离开房间
 * 
 * @author lzb
 * @date 2020年8月17日下午4:41:55
 */
@Service("LEAVEROOM")
public class NettyEventLeaveRoomMsgServiceImpl implements INettyEventService {
	@Autowired
	INettyService nettyService;

	@Override
	public Result<Void> run(NettyMsg msg) {
		Result<Void> rs = nettyService.checkPush(msg);
		if (!rs.check()) {
			return rs;
		}
		JSONObject body = UtilJson.getJsonObject(msg.getBody());
		String roomId = null;
		if (body != null) {
			roomId = body.getString("roomId");
			if (UtilString.isBlank(roomId)) {
				roomId = body.getString("bonfireId");
			}
		}
		return nettyService.leaveRoom(roomId, msg.getSendAccount());
	}

}

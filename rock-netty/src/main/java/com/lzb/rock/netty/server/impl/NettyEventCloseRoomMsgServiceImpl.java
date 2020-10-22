package com.lzb.rock.netty.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.model.Result;
import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.server.INettyEventService;
import com.lzb.rock.netty.server.INettyService;

/**
 * 关闭房间
 * 
 * @author lzb
 * @date 2020年8月17日下午4:41:30
 */
@Service("CLOSEROOM")
public class NettyEventCloseRoomMsgServiceImpl implements INettyEventService {

	@Autowired
	INettyService nettyService;

	@Override
	public Result<Void> run(NettyMsg msg) {

		JSONObject body = UtilJson.getJsonObject(msg.getBody());
		String roomId = null;
		if (body != null) {
			roomId = body.getString("roomId");
			if (UtilString.isBlank(roomId)) {
				roomId = body.getString("bonfireId");
			}
		}

		return nettyService.closeRoom(roomId);
	}

}

package com.lzb.rock.netty.server.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.enums.RockEnum;
import com.lzb.rock.base.model.Result;
import com.lzb.rock.base.util.UtilBean;
import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.entity.NettyAccount;
import com.lzb.rock.netty.entity.NettyRecMsg;
import com.lzb.rock.netty.entity.NettySendMsg;
import com.lzb.rock.netty.enums.EventEnum;
import com.lzb.rock.netty.enums.NettyEnum;
import com.lzb.rock.netty.enums.PubTypeEnum;
import com.lzb.rock.netty.mapper.NettyAccountMapper;
import com.lzb.rock.netty.mapper.NettyRecMsgMapper;
import com.lzb.rock.netty.mapper.NettySendMsgMapper;
import com.lzb.rock.netty.server.INettyEventService;
import com.lzb.rock.netty.server.INettyPubService;
import com.lzb.rock.netty.server.INettyService;
import com.lzb.rock.netty.util.MyNettyContext;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NettyServiceImpl implements INettyService {

	@Autowired
	Map<String, INettyEventService> event;

	@Autowired
	Map<String, INettyPubService> pub;

	@Autowired
	NettyAccountMapper nettyAccountMapper;

	@Autowired
	NettySendMsgMapper nettySendMsgMapper;

	@Autowired
	NettyRecMsgMapper nettyRecMsgMapper;

	@Override
	public Result<Void> push(NettyMsg msg) {
		// 消息保存到数据库
		NettyRecMsg nettyMsg = UtilBean.copyProperties(NettyRecMsg.class, msg);
		nettyRecMsgMapper.insert(nettyMsg);
		msg.setMsgId(nettyMsg.getMsgId().toString());
		Result<Void> rs = new Result<Void>();
		rs = checkPush(msg);
		if (!rs.check()) {
			return rs;
		}
		INettyPubService nettyPubService = pub.get(msg.getPubType());
		if (nettyPubService != null) {
			rs = nettyPubService.run(msg);
			log.debug("onMessage:{}", rs);
		} else {
			log.info("广播处理service未找到");
		}
		/**
		 * 处理事件
		 */
		INettyEventService nettyEventService = event.get(msg.getEvent());
		if (nettyEventService != null) {
			rs = nettyEventService.run(msg);
			log.debug("onMessage:{}", rs);
		}

		return rs;
	}

	@Override
	public Result<Void> bind(String account, String platform, String deviceId, String version,
			ChannelHandlerContext ctx) {
		AttributeKey<String> accountKey = AttributeKey.valueOf("account");
		ctx.channel().attr(accountKey).set(account);
		MyNettyContext.bind(account, ctx);

		// 保存数据库
		NettyAccount nettyAccount = new NettyAccount();
		nettyAccount.setAccount(account);
		nettyAccount.setChannelId(ctx.channel().id().asLongText());
		nettyAccount.setPlatform(platform);
		nettyAccount.setDeviceId(deviceId);
		nettyAccount.setVersion(version);
		nettyAccount.setState(0);
		nettyAccount.setRoomIds(new ArrayList<String>());
		Boolean flag = nettyAccountMapper.upsert(nettyAccount);
		if (!flag) {
			return new Result<Void>(NettyEnum.BIND_ERR);
		}
		return new Result<Void>();
	}

	@Override
	public Result<Void> disconnect(String account) {

		NettyAccount nettyAccountOld = nettyAccountMapper.findByAccount(account);
		if (nettyAccountOld != null) {
			List<String> list = nettyAccountOld.getRoomIds();
			if (list != null) {
				for (String roomId : list) {
					MyNettyContext.leaveRoom(account, roomId);
				}
			}
		}
		MyNettyContext.disconnect(account);

		NettyAccount nettyAccount = new NettyAccount();
		nettyAccount.setAccount(account);
		nettyAccount.setState(1);
		Boolean flag = nettyAccountMapper.upsert(nettyAccount);
		if (!flag) {
			return new Result<Void>(NettyEnum.BIND_ERR);
		}
		return new Result<Void>();
	}

	@Override
	public Result<Void> disconnect(ChannelHandlerContext ctx) {

		AttributeKey<String> accountKey = AttributeKey.valueOf("account");

		String account = ctx.channel().attr(accountKey).get();
		if (UtilString.isBlank(account)) {
			log.error("disconnect绑定的信息不存在asLongText:{}", ctx.channel().id().asLongText());
			Result<Void> rs = new Result<Void>();
			rs.error(RockEnum.PAEAM_ERR, "绑定的信息不存在");
			return rs;
		}

		NettyAccount nettyAccountOld = nettyAccountMapper.findByAccount(account);
		if (nettyAccountOld != null) {
			List<String> list = nettyAccountOld.getRoomIds();
			if (list != null) {
				for (String roomId : list) {
					MyNettyContext.leaveRoom(account, roomId);
				}
			}
		}
		MyNettyContext.disconnect(account);
		NettyAccount nettyAccount = new NettyAccount();
		nettyAccount.setAccount(account);
		nettyAccount.setState(1);
		Boolean flag = nettyAccountMapper.upsert(nettyAccount);
		if (!flag) {
			return new Result<Void>(NettyEnum.BIND_ERR);
		}
		return new Result<Void>();
	}

	@Override
	public Result<Void> offLine(String account) {

		NettyAccount nettyAccountOld = nettyAccountMapper.findByAccount(account);
		if (nettyAccountOld != null) {
			List<String> list = nettyAccountOld.getRoomIds();
			if (list != null) {
				for (String roomId : list) {
					MyNettyContext.leaveRoom(account, roomId);
				}
			}
		}
		MyNettyContext.removeBind(account);

		NettyAccount nettyAccount = new NettyAccount();
		nettyAccount.setAccount(account);
		nettyAccount.setState(2);
		Boolean flag = nettyAccountMapper.upsert(nettyAccount);
		if (!flag) {
			return new Result<Void>(NettyEnum.BIND_ERR);
		}
		return new Result<Void>();

	}

	@Override
	public Result<Void> offLine(ChannelHandlerContext ctx) {

		AttributeKey<String> accountKey = AttributeKey.valueOf("account");

		String account = ctx.channel().attr(accountKey).get();
		if (UtilString.isBlank(account)) {
			log.warn("offLine绑定的信息不存在asLongText:{}", ctx.channel().id().asLongText());
			Result<Void> rs = new Result<Void>();
			rs.error(RockEnum.PAEAM_ERR, "绑定的信息不存在");
			return rs;
		}

		NettyAccount nettyAccountOld = nettyAccountMapper.findByAccount(account);
		if (nettyAccountOld != null) {
			List<String> list = nettyAccountOld.getRoomIds();
			if (list != null) {
				for (String roomId : list) {
					MyNettyContext.leaveRoom(account, roomId);
				}
			}
		}
		MyNettyContext.removeBind(account);

		NettyAccount nettyAccount = new NettyAccount();
		nettyAccount.setAccount(account);
		nettyAccount.setState(2);
		Boolean flag = nettyAccountMapper.upsert(nettyAccount);
		if (!flag) {
			return new Result<Void>(NettyEnum.BIND_ERR);
		}
		return new Result<Void>();
	}

	@Override
	public Result<Void> onLine(ChannelHandlerContext ctx) {

		AttributeKey<String> accountKey = AttributeKey.valueOf("account");

		String account = ctx.channel().attr(accountKey).get();
		if (UtilString.isBlank(account)) {
			log.warn("onLine绑定的信息不存在asLongText:{}", ctx.channel().id().asLongText());
			Result<Void> rs = new Result<Void>();
			rs.error(RockEnum.PAEAM_ERR, "绑定的信息不存在");
			MyNettyContext.disconnect(ctx);
			return rs;
		}

		ChannelHandlerContext ctx2 = MyNettyContext.getCtxByAccount(account);
		if (ctx2 == null) {
			NettyAccount nettyAccount = new NettyAccount();
			nettyAccount.setAccount(account);
			nettyAccount.setState(1);
			Boolean flag = nettyAccountMapper.upsert(nettyAccount);
			if (!flag) {
				return new Result<Void>(NettyEnum.BIND_ERR);
			}
		}
		return new Result<Void>();
	}

	@Override
	public Result<Void> joinRoom(String roomId, String account) {
		MyNettyContext.joinRoom(roomId, account);
		nettyAccountMapper.setRoomId(account, roomId);
		return new Result<Void>();
	}

	@Override
	public Result<Void> closeRoom(String roomId) {
		Set<ChannelHandlerContext> ctxs = MyNettyContext.getCtxsByRoomId(roomId);
		if (ctxs != null) {
			AttributeKey<String> accountKey = AttributeKey.valueOf("account");

			for (ChannelHandlerContext ctx : ctxs) {
				String account = ctx.channel().attr(accountKey).get();
				if (UtilString.isNotBlank(account)) {
					// leaveRoom(roomId, account);
					nettyAccountMapper.pullRoomId(account, roomId);
				} else {
					log.warn("closeRoom绑定的信息不存在asLongText:{}", ctx.channel().id().asLongText());
				}
			}
		}
		MyNettyContext.closeRoom(roomId);
		return new Result<Void>();
	}

	@Override
	public Result<Void> leaveRoom(String roomId, String account) {
		nettyAccountMapper.pullRoomId(account, roomId);
		MyNettyContext.leaveRoom(account, roomId);
		return new Result<Void>();
	}

	@Override
	public Result<Void> checkPush(NettyMsg msg) {
		Result<Void> rs = new Result<Void>();

		if (msg == null) {
			rs.error(RockEnum.PAEAM_ERR, "参数不能为空");
			return rs;
		}

		if (UtilString.isBlank(msg.getPubType())) {
			rs.error(RockEnum.PAEAM_ERR, "发布类型不能为空");
			return rs;
		}

		if (UtilString.isBlank(msg.getSendAccount())) {
			rs.error(RockEnum.PAEAM_ERR, "发送账号不能为空");
			return rs;
		}

		if (UtilString.isBlank(msg.getEvent())) {
			rs.error(RockEnum.PAEAM_ERR, "事件不能为空");
			return rs;
		}

		/**
		 * 加入房间，房间ID不能为空
		 */
		if (msg.getEvent().equals(EventEnum.JOINROOM.getCode())) {
			JSONObject body = UtilJson.getJsonObject(msg.getBody());
			String roomId = null;
			if (body != null) {
				roomId = body.getString("roomId");
				if (UtilString.isBlank(roomId)) {
					roomId = body.getString("bonfireId");
				}
			}
			if (UtilString.isBlank(roomId)) {
				rs.error(RockEnum.PAEAM_ERR, "房间ID不能为空");
			}
			return rs;
		}

		/**
		 * 退出房间消息
		 */

		if (msg.getEvent().equals(EventEnum.LEAVEROOM.getCode())) {
			JSONObject body = UtilJson.getJsonObject(msg.getBody());
			String roomId = null;
			if (body != null) {
				roomId = body.getString("roomId");
				if (UtilString.isBlank(roomId)) {
					roomId = body.getString("bonfireId");
				}
			}
			if (UtilString.isBlank(roomId)) {
				rs.error(RockEnum.PAEAM_ERR, "房间ID不能为空");
			}
			return rs;
		}

		/**
		 * 房间消息 接收账号不能为空
		 */
		if (msg.getPubType().equals(PubTypeEnum.ROOM.getCode())) {
			if (UtilString.isBlank(msg.getReceiveAccount())) {
				rs.error(RockEnum.PAEAM_ERR, "接收账号不能为空");
				return rs;
			}
		}
		/**
		 * 点对点消息接收账号不能为空
		 */
		if (msg.getPubType().equals(PubTypeEnum.ONE.getCode())) {
			if (UtilString.isBlank(msg.getReceiveAccount())) {
				rs.error(RockEnum.PAEAM_ERR, "接收账号不能为空");
				return rs;
			}
		}
		return rs;

	}

	@Override
	public Result<Void> writeAndFlush(ChannelHandlerContext ctx, NettyMsg msg) {
		AttributeKey<String> accountKey = AttributeKey.valueOf("account");

		String account = ctx.channel().attr(accountKey).get();
		if (UtilString.isNotBlank(account) && !EventEnum.HEART.getCode().equals(msg.getEvent())
				&& !EventEnum.HEART_NOT.getCode().equals(msg.getEvent())) {
			msg.setMsgId(null);
			NettySendMsg msg2 = UtilBean.copyProperties(NettySendMsg.class, msg);
			msg2.setMsgId(null);
			msg2.setMsgState(1);
			msg2.setSendCount(1);
			msg2.setAccount(account);
			nettySendMsgMapper.insert(msg2);
			if (msg2.getMsgId() != null) {
				msg.setMsgId(msg2.getMsgId().toString());
			}
		} else {
			log.warn("writeAndFlush绑定的信息不存在asLongText:{}", ctx.channel().id().asLongText());
		}
		ctx.channel().writeAndFlush(new TextWebSocketFrame(UtilJson.getStr(msg)));
		return new Result<Void>();
	}

	@Override
	public Result<Void> writeAndFlush(String account, NettyMsg msg) {

		ChannelHandlerContext ctx = MyNettyContext.getCtxByAccount(account);

		if (UtilString.isNotBlank(account) && !EventEnum.HEART.getCode().equals(msg.getEvent())
				&& !EventEnum.HEART_NOT.getCode().equals(msg.getEvent())) {
			NettySendMsg msg2 = UtilBean.copyProperties(NettySendMsg.class, msg);
			msg2.setMsgId(null);
			if (ctx != null) {
				msg2.setMsgState(1);
				msg2.setSendCount(1);
			}
			msg2.setAccount(account);
			nettySendMsgMapper.insert(msg2);
			if (msg2.getMsgId() != null) {
				msg.setMsgId(msg2.getMsgId().toString());
			}
		}
		if (ctx != null) {
			ctx.channel().writeAndFlush(new TextWebSocketFrame(UtilJson.getStr(msg)));
		}
		return new Result<Void>();

	}
}

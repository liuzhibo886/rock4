package com.lzb.rock.netty.server;

import com.lzb.rock.base.model.Result;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.entity.NettyAccount;

import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理类
 * 
 * @author lzb
 * @date 2020年9月18日上午11:11:29
 */
public interface INettyService {

	/*
	 * 账号绑定
	 */
	Result<Void> bind(String account, String platform, String deviceId, String version, ChannelHandlerContext ctx);

	/*
	 * 断开
	 */
	Result<Void> disconnect(String account);

	Result<Void> disconnect(ChannelHandlerContext ctx);

	/**
	 * 离线
	 * 
	 * @param account
	 * @return
	 */
	Result<Void> offLine(String account);

	/**
	 * 离线
	 * 
	 * @param ctx
	 * @return
	 */
	Result<Void> offLine(ChannelHandlerContext ctx);

	/**
	 * 上线
	 * 
	 * @param ctx
	 */
	Result<Void> onLine(ChannelHandlerContext ctx);

	/**
	 * 加入房间
	 * 
	 * @param account
	 * @param roomId
	 * @return
	 */
	Result<Void> joinRoom(String roomId, String account);

	/**
	 * 关闭房间
	 * 
	 * @param roomId
	 * @return
	 */
	Result<Void> closeRoom(String roomId);

	/**
	 * 退出房间
	 * 
	 * @param roomId
	 * @param account
	 * @return
	 */
	Result<Void> leaveRoom(String roomId, String account);

	/**
	 * 推送消息
	 * 
	 * @param msg
	 * @return
	 */
	Result<Void> push(NettyMsg msg);

	/**
	 * 检查消息的合法性
	 * 
	 * @param msg
	 * @return
	 */
	Result<Void> checkPush(NettyMsg msg);

	/**
	 * 发送消息
	 * 
	 * @param ctx
	 * @param msg
	 * @return
	 */
	Result<Void> writeAndFlush(String account, NettyMsg msg);

	Result<Void> writeAndFlush(ChannelHandlerContext ctx, NettyMsg msg);

}

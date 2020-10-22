package com.lzb.rock.netty.util;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.lzb.rock.base.util.UtilString;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户ChannelHandlerContext对象
 *
 * @author lzb
 *
 * @date 2019年9月30日 下午11:13:36
 */
@Slf4j
public class MyNettyContext {

	/**
	 * 房间ID为key
	 */
	private final static ConcurrentMap<String, Set<ChannelHandlerContext>> roomGroup = PlatformDependent
			.newConcurrentHashMap();

	/**
	 * 在线用户，账号为key
	 */
	private final static ConcurrentMap<String, ChannelHandlerContext> accountGroup = PlatformDependent
			.newConcurrentHashMap();

	public static ConcurrentMap<String, ChannelHandlerContext> getAccountGroup() {

		return accountGroup;
	}

	public static ConcurrentMap<String, Set<ChannelHandlerContext>> getRoomGroup() {

		return roomGroup;
	}

	/*
	 * 根据账号获取绑定的ctx
	 */
	public static ChannelHandlerContext getCtxByAccount(String account) {

		return accountGroup.get(account);
	}

	/**
	 * 获取房间所有 MyNettyContext
	 * 
	 * @param roomId
	 * @return
	 */
	public static Set<ChannelHandlerContext> getCtxsByRoomId(String roomId) {

		return roomGroup.get(roomId);
	}

	/**
	 * 账号绑定
	 * 
	 * @param account
	 * @param ctx
	 */
	public static void bind(String account, ChannelHandlerContext ctx) {
		accountGroup.put(account, ctx);
	}

	/**
	 * 断开连接
	 * 
	 * @param account
	 */
	public static void disconnect(String account) {
		ChannelHandlerContext ctx = accountGroup.get(account);
		if (ctx != null) {
			ctx.channel().disconnect();
			accountGroup.remove(account);
		}
	}

	public static void disconnect(ChannelHandlerContext ctx) {
		AttributeKey<String> accountKey = AttributeKey.valueOf("account");
		String account = ctx.channel().attr(accountKey).get();
		if (UtilString.isNotBlank(account)) {
			accountGroup.remove(account);
		}
		ctx.channel().disconnect();
	}

	/**
	 * 删除绑定关系
	 * 
	 * @param account
	 */
	public static void removeBind(String account) {
		accountGroup.remove(account);
	}

	/**
	 * 退出房间
	 * 
	 * @param account
	 */
	public static void leaveRoom(String account, String roomId) {
		Set<ChannelHandlerContext> rooms = roomGroup.get(roomId);
		if (rooms == null) {
			return;
		}
		AttributeKey<String> accountKey = AttributeKey.valueOf("account");
		for (ChannelHandlerContext ctx : rooms) {
			String accountOld = ctx.channel().attr(accountKey).get();
			if (UtilString.isNotBlank(account) && account.equals(accountOld)) {
				rooms.remove(ctx);
			}
		}
	}

	public static void leaveRoom(ChannelHandlerContext ctx, String roomId) {
		Set<ChannelHandlerContext> rooms = roomGroup.get(roomId);
		if (rooms == null) {
			return;
		}
		rooms.remove(ctx);
	}

	/**
	 * 加入房间
	 * 
	 * @param roomId
	 * @param account
	 */
	public static void joinRoom(String roomId, String account) {
		ChannelHandlerContext ctx = accountGroup.get(account);
		if (ctx != null) {
			Set<ChannelHandlerContext> rooms = roomGroup.get(roomId);
			if (rooms == null) {
				rooms = new CopyOnWriteArraySet<ChannelHandlerContext>();
				roomGroup.put(roomId, rooms);
			}
			rooms.add(ctx);
		} else {
			log.warn("joinRoom绑定的信息不存在account:{}", account);
		}
		log.debug("roomGroup:{}",roomGroup.get(roomId).size());
	}

	public static void closeRoom(String roomId) {
		roomGroup.remove(roomId);
	}
}

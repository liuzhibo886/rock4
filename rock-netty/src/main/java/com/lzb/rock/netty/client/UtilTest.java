package com.lzb.rock.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.model.Header;
import com.lzb.rock.base.util.UtilAES;
import com.lzb.rock.base.util.UtilHttp;
import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.enums.EventEnum;
import com.lzb.rock.netty.enums.PubTypeEnum;

public class UtilTest {
	private static String encodeRules = "c5xwYYiSgTkDfDemuULJ9zWto8pqiXASbxKxR7pmdU6LXTq7v2Et9NF8ZRI3jvgK395zZ0c9vWPxNCG5FwBAqy2zROX05Kizl39";

	public static String getAesAppToken(String token, String account, String platform, String appVersion,
			String deviceId) {

		Header header = new Header();
		header.setToken(token);
		header.setUserId(account);
		header.setPlatform(platform);
		header.setAppVersion(appVersion);
		header.setDeviceId(deviceId);
		String aesAppToken = UtilAES.Base64AESEncode(encodeRules, UtilJson.getStr(header));
		return aesAppToken;
	}

	/**
	 * 加入房间
	 * 
	 * @param url
	 * @return
	 */
	public static String joinRoom(String url, String sendAccount) {

		JSONObject obj = new JSONObject();
		String roomId = "room_" + UtilString.getRandomNumber(1);
		obj.put("roomId", roomId);
		NettyMsg msg = new NettyMsg();
		msg.setEvent(EventEnum.JOINROOM.getCode());
		msg.setPubType(PubTypeEnum.NO.getCode());
		msg.setBody(obj.toJSONString());
		msg.setSendAccount(sendAccount);
		UtilHttp.postBody(url, UtilJson.getStr(msg));
		return roomId;
	}

	/**
	 * 发送房间消息
	 * 
	 * @param body
	 * @param roomId
	 * @param sendAccount
	 * @param url
	 */
	public static void sendRoomMsg(String body, String roomId, String sendAccount, String url) {
		NettyMsg msg = new NettyMsg();
		msg.setEvent("msg");
		msg.setPubType(PubTypeEnum.ROOM.getCode());
		msg.setBody(body);
		msg.setSendAccount(sendAccount);
		msg.setReceiveAccount(roomId);
		UtilHttp.postBody(url, UtilJson.getStr(msg));

	}
}

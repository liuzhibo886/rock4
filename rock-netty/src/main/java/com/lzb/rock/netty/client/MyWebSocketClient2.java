package com.lzb.rock.netty.client;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilString;
import com.lzb.rock.netty.dto.NettyMsg;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MyWebSocketClient2 extends WebSocketClient {

	String name;

	public MyWebSocketClient2(URI serverUri, String name) {
		super(serverUri);
		this.name = name;
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		log.debug("打开连接:{}", name);
	}

	@Override
	public void onMessage(String message) {
		NettyMsg msg = UtilJson.getJavaBean(message, NettyMsg.class);
		if (msg != null) {
			if ("HEART".equals(msg.getEvent()) || "HEART_NOT".equals(msg.getEvent())) {
				this.send(message);
			} else {
				log.debug("用户:{}，收到消息:{}", name, message);
			}
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		log.debug("关闭连接：{}", name);

	}

	@Override
	public void onError(Exception ex) {
		log.debug("异常：{};ex:{}", name, ex);
	}

	public static void main(String[] args) throws Exception {
		String webSocketUrl = "http://tx.liuzhibo.top:19066/websocket?account=";
		String webUrl = "http://tx.liuzhibo.top:19065";

		MyWebSocketClient2 socketClient = new MyWebSocketClient2(new URI(webSocketUrl + "18589095668"), "主播");
		socketClient.connect();

		String roomId = UtilTest.joinRoom(webUrl + "/tell/netty/push", "18589095668");

		UtilTest.sendRoomMsg(UtilString.getRandomString(64), roomId, "18589095668", webUrl + "/tell/netty/push");
	}

}
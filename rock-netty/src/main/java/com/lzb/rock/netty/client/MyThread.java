package com.lzb.rock.netty.client;

import java.net.URI;

import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilString;

public class MyThread implements Runnable {
	String webSocketUrl = "http://127.0.0.1:19066/websocket?token=";
	String webUrl = "http://127.0.0.1:19065";

	String aesAppToken;

	String name;

	String roomId;

	public MyThread(String aesAppToken, String name) {
		this.aesAppToken = aesAppToken;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			MyWebSocketClient socketClient = new MyWebSocketClient(new URI(webSocketUrl + aesAppToken), name);
			socketClient.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (true) {

			// 加入房间
			if (UtilString.isBlank(roomId)) {
				roomId = UtilTest.joinRoom(webUrl + "/tell/netty/push", name);
			}

			// 退出房间

			// 断开连接

			// 重新连接

			// 发送消息
			UtilTest.sendRoomMsg(UtilString.getRandomString(64), roomId, name, webUrl + "/tell/netty/push");

//			try {
//				Thread.sleep(1000 * 1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}

	}

}

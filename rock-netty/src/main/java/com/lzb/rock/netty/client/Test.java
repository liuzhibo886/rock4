package com.lzb.rock.netty.client;

public class Test {

	public static void main(String[] args) throws Exception {
		// 5bc054f9ba7f837988e0ad21
		// 5bc054f9ba7f837988e0ad89

		for (int i = 0; i < 300; i++) {
			String aesAppToken = UtilTest.getAesAppToken("token_" + i, "account_" + i, "platform_" + i,
					"appVersion_" + i, "deviceId_" + i);
			MyThread target = new MyThread(aesAppToken, "account_" + i);
			new Thread(target).start();
		}
	}

}

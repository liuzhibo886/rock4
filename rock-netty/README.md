## rock-netty 1.0.0
基于 rook 4.0.1 技术架构实现的 基于netty的 WebSocket IM,实现了点对点,群消息
###  建立连接
-    请求路径 http://127.0.0.1:19066/websocket?token=
- token 生成见 com.lzb.rock.netty.client.getAesAppToken
- userId 必须全局唯一,所有消息推送均以userId 标识

## 消息对象说明 NettyMsg
- msgId 消息ID
- mqId 消息队列ID

- pubType 发布类型,用于标识消息广播方式,参数见枚举 PubTypeEnum

- event 事件,EventEnum 之外,其余事件用户自定义

- body 消息体.字符串,netty负责转发内容

- sendAccount 发送者账号

- receiveAccount 接收者账号

# 内部消息
-- 所有消息通过HTTP 请求接入, com.lzb.rock.netty.controller.NettyController.push
-- 也可以通过消息队列 rocketMq ,消息监听类:com.lzb.rock.netty.mqListener.MyMessageListenerConcurrently.


###  点对点消息

new NettyMsg(PubTypeEnum.ONE.getCode,"event",body,sendAccount,receiveAccount);

###  房间广播 消息

new NettyMsg(PubTypeEnum.ROOM.getCode,"event",body,sendAccount,receiveAccount);

## 加入房间

new NettyMsg(PubTypeEnum.NO.getCode,EventEnum.JOINROOM.getCode(),"{\"roomId'":\"roomId\"}",sendAccount,null);

## 退出房间

new NettyMsg(PubTypeEnum.NO.getCode,EventEnum.LEAVEROOM.getCode(),"{\"roomId'":\"roomId\"}",sendAccount,null);

## 关闭房间
new NettyMsg(PubTypeEnum.NO.getCode,EventEnum.CLOSEROOM.getCode(),"{\"roomId'":\"roomId\"}",sendAccount,null);

## 断开连接
new NettyMsg(PubTypeEnum.NO.getCode,EventEnum.DISCONNECT.getCode(),"",sendAccount,null);


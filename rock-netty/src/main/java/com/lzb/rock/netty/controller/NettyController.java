package com.lzb.rock.netty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lzb.rock.base.model.Result;
import com.lzb.rock.netty.dto.NettyMsg;
import com.lzb.rock.netty.server.INettyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/tell/netty")
@RestController
@Api(tags = { "netty控制器" })
public class NettyController {

	@Autowired
	INettyService nettyService;

	@PostMapping("/push")
	@ApiOperation(value = "发送消息")
	public Result<Void> push(@RequestBody NettyMsg msg) {

		return nettyService.push(msg);
	}
}

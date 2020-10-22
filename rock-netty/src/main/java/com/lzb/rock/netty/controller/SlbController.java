package com.lzb.rock.netty.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lzb.rock.base.aop.annotation.Log;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = { "SLB测试接口" })
@ApiIgnore
public class SlbController {

	@RequestMapping(value = "")
	@Log(before = false, end = false)
	public String test() {
		return "index";
	}

}

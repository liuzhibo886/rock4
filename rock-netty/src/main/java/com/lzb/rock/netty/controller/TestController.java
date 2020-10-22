package com.lzb.rock.netty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lzb.rock.base.model.Result;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("/tell/test")
@RestController
@Api(tags = { "测试控制器" })
@Slf4j
@ApiIgnore
public class TestController {

	@Autowired
	SpringClientFactory clientFactory;
	

	@PostMapping("/test")
	@ApiOperation(value = "测试")
	public Result<Void> test() {

		String serviceId = "tell-netty";

		RestTemplate restTemplate = new RestTemplate();

		ILoadBalancer loadBalancer = clientFactory.getLoadBalancer(serviceId);
		List<Server> servers = loadBalancer.getAllServers();
		for (Server server : servers) {
			log.info("server:{}", server.getHostPort());
//			String result = restTemplate.getForObject(server.getHostPort(), String.class);
//			restTemplate.postForEntity(url, request, responseType)

		}
		List<Server> servers2 = loadBalancer.getReachableServers();
		for (Server server : servers2) {
			log.info("server2:{}", server.getHostPort());
		}
		return new Result<Void>();
	}
}

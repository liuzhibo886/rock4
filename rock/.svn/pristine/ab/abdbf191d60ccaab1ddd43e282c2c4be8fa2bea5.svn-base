package com.lzb.rock.base.factory;



import org.springframework.context.annotation.Configuration;

import com.lzb.rock.base.enums.RockEnum;
import com.lzb.rock.base.exception.RockClientException;
import com.lzb.rock.base.exception.RockExceptionResponse;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author admin
 * @Date 2019年10月22日 下午5:15:33 feign 调用返回错误信息
 */
@Configuration
@Slf4j
public class MyErrorDecoder implements ErrorDecoder {
	private Decoder decoder;

	public MyErrorDecoder(Decoder decoder) {
		this.decoder = decoder;
	}

	@Override
	public Exception decode(String methodKey, Response response) {
		RockClientException ex = null;
		try {
			RockExceptionResponse ber = (RockExceptionResponse) decoder.decode(response, RockExceptionResponse.class);
			log.error("远程调用失败:methodKey:{};message:{};ex:{}",methodKey,ber.getMessage(),ber);
			ex = new RockClientException(RockEnum.REST_ERR, "服务繁忙,稍后再试");
		} catch (Exception e) {
			e.printStackTrace();
			ex = new RockClientException(RockEnum.SYSTTEM_ERR, "Service Unavailable");
		}

		return ex;
	}

}

package com.alipay.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @date 2017年1月19日上午9:23:06
 * @author jq.yin@i-vpoints.com
 */
@Controller
public class Test01 {
	
	@RequestMapping(value="/test",produces=MediaType.APPLICATION_JSON_VALUE)	// 响应头 content-type
	@ResponseBody
	public Map<String,Object> test(HttpServletRequest request,HttpServletResponse response){
		System.out.println(1234);
//		String form = "<form action='https://www.baidu.com' name='myform'></form>";
//		String a = "<script>document.myform.submit()</script>";
//		a = "<script>alert(123);</script>";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", "asd");
		return map;
	}
	
}

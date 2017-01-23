package com.alipay.one.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

/**
 * 配置文件读取类
 * 
 * @date 2017年1月19日下午4:51:36
 * @author jq.yin@i-vpoints.com
 */
@Service
public class PayConfig {

	// 配置文件名
	private static final String proName = "config.properties";
	private static Map<String, String> map = new HashMap<String, String>();

	public static String get(String key) {
		return map.get(key);
	}

	@PostConstruct
	private static void init() {
		try {
			// 文件在class的根路径
			InputStream is = PayConfig.class.getClassLoader()
					.getResourceAsStream(proName);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			Properties props = new Properties();
			props.load(br);
			for (Object s : props.keySet()) {
				String str = (String) s;
				map.put(str, props.getProperty(str));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
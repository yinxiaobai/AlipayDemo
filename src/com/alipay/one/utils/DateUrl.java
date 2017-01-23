package com.alipay.one.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date 2017年1月18日下午5:05:19
 * @author jq.yin@i-vpoints.com
 */
public class DateUrl {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 获得当前日期
	 * yyyyMMddHHmmss
	 * 
	 * @date 2017年1月18日下午5:06:58
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String getTime() {
		return sdf.format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(getTime());
	}
}

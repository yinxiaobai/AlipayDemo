package com.alipay.one;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * @date 2017年1月17日下午3:56:25
 * @author jq.yin@i-vpoints.com
 */
public class AliPayConfig {
	
	public static AliPayConfig aliPayConfig = new AliPayConfig();
	
	/**
	 * 开发模式
	 */
	private boolean isPro = false;		// 测试环境

	/**
	 * web环境地址(hostAddress:port)
	 */
	private String hostAddress;

	/**
	 * 支付宝客户端对象
	 */
	private AlipayClient alipayClient;

	/**
	 * 错误默认跳转action
	 */
	private String defaultAction;

	/**
	 * 构造函数
	 * 
	 * @param isPro
	 *            当前环境状态：是否为生产环境
	 * @param hostAddressDev
	 *            伯乔测试地址
	 * @param hostAddressPro
	 *            伯乔生产地址
	 */
	public AliPayConfig() {
		if(aliPayConfig != null){
			throw new RuntimeException("Error! Error! Error!");
		}
		this.alipayClient = new DefaultAlipayClient(getAliGateway(), getAppId(),
				getPrivateKey(), "json", "utf-8", getPublicKey());
	}

	/**
	 * 根据环境状态，获取应用appId
	 * 
	 * @Title: getAppId
	 * @Description: 根据环境状态，获取应用appId
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:48:45
	 */
	public String getAppId() {
		// 支付宝测试沙箱环境appId
		String appIdDev = "2016080100141836";
		// 支付宝生产环境appId
		String appIdPro = "2016111002681958";
		return get(appIdDev, appIdPro);
	}

	/**
	 * 根据环境状态，获取支付宝网关地址
	 * 
	 * @Title: getAliGateway
	 * @Description: 根据环境状态，获取支付宝网关地址
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:49:17
	 */
	public String getAliGateway() {
		// 支付宝测试环境网关地址
		String aliGatewayDev = "https://openapi.alipaydev.com/gateway.do";
		// 支付宝生产环境网关地址
		String aliGatewayPro = "https://openapi.alipay.com/gateway.do";
		return get(aliGatewayDev, aliGatewayPro);
	}

	/**
	 * 根据环境状态，获取伯乔私钥信息
	 * 
	 * @Title: getPrivateKey
	 * @Description: 根据环境状态，获取伯乔私钥信息
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:49:47
	 */
	public String getPrivateKey() {
		// 伯乔测试私钥
		String privateKeyDev = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANlYI14G726zp4ZiE9RBQEnPlP+4bXQU3fJOK7O7+jZO70v5JBTDW4/cBC2pe9lAYRIL9oFP/8DYGWe+I8dHLKouOh4fPHq1Ctj7vELBUBUTAfeVnn5hlxPRLYYbnR48QrW6tOIFNHRVvI+QrYF+wMzhmX0E+GUW1tMvglgSn3D3AgMBAAECgYAHteXDTzGtVrhJvy7+57W1KKUbkVpotNjO4NESFkghJxm8M0GBbxDPgLmxOQMIyQzoi/4ZxlHHcKMuGAPC7RZ7flWtM404yYb+gPAxJZYxwCr4MZM4hVacM9rZynfg92bGeTPGGJ/Jtm+1jimjktIRDFX6PZqwRrY4glKsp+liOQJBAPrV7EooK+OHMuSdFs2CqhCox15mSOqEXuJe8Eup8YMEx6cu2LPBTihbP/IBXb/Cr6S34vqoiMEoQpiz13EpsNMCQQDd0bFPzXiv2G2c02iJLkaGEVvCIS+RX9UkpMiJaEf/2Wpj3/YdmhcRXcnacH2LD77xA6vsewk26DyglgUGiMjNAkEA8kcQXSxh//nFkYWd0dCkfUTYlPiM81/52gLDbQHv0ZIbWgLosu9Ck75XjGP/bkoYpywqY9vHg3JkqOQEczxGmwJAfIEDhihtUj4noDvlVAJeYUgC+d7IpU9mAnBfgwi+SPQOzEpcCVeBFSVQdERpoxFOelc2O8D/dgs6ZGRwI6/JDQJAVAGJWvJ/BYDrh218oiRCFVJwr/LsZJdeB9c+xpm5Ukl8hfPzFSEgKlDN7fAyGf8PWIeWEOx1eXG+Dhiwb6ElBw==";
		// 伯乔生产私钥
		String privateKeyPro = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALq0dHigaNb1/dlU6qzUSLmDnxfNvysG01aWAmpv0o0ki5YhoAeumHDd2uPZnKAXIkIEqBncue6NyiYkvri+JykR6D1bN3BbsL3o2nIvdOE1qI3yaj5NSXQjlBB4UTNlDOGwEZvXNLaXeQ741WqBEBHPtOtXwhYjnUpcCE+c4P41AgMBAAECgYB3WvU6SBG9mfR2Azsi0XLMZcBGz1jugKaKK8vPjzzizgIOL+DGCG+bd7h/AJaIIUHdQmzUW67hOfOI/uSN5cHR/iMzzveTuNB+crV8CR/XE1H24hLcd4VUfxVCZBVFiyaf0Y6VSPjmwXKbvoxXOKTmHrHdHVt5GsdhJ/taysrw4QJBAPVtaPtCAtqrpCPCJmZRPil/sVhpLJRvp8twfe8xX1YA1heDKVPG1L5LO45UDAVuV0rw1rgbcLdI5jf+TktvEO8CQQDCv3MJHzTMIJPPoS0L/MYTx2yvD+PB7pkKA2X+vR+tmQ3w8lJ/bpF4bmrZLQK/gUciBcGHAqc+FVtlBp2CrxsbAkEA2lVQ9WUOIPZc+Sq4+oD/6e8dF+fIrux9u3ABuI6Vn27zBsu6MjtJ+1f2TXHbl+FHavXCLtrKdWM2200y3cyapQJBAJd7VZ6V0HqrKG5Ln3+Aig5eg9KUGIQD9LSOOirwrxBYSjjONI980NiNgr0VPtr5UvxK3+MFlFM9R48Im0JItFsCQHmzr3L+hQOTZm8ai3bQWgt6BEfv0Zppufzn1pajPGgZ9D+ZCNzkYsMFdFGEpBrI6spCTHuIlVg0Gm98lDgLiNg=";
		return get(privateKeyDev, privateKeyPro);
	}

	/**
	 * 根据环境状态，获取支付宝公钥信息
	 * 
	 * @Title: getPublicKey
	 * @Description: 根据环境信息，获取支付宝公钥信息
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:50:14
	 */
	public String getPublicKey() {
		// 支付宝测试公钥
		String publicKeyDev = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
		// 支付宝生产公钥
		String publicKeyPro = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
		return get(publicKeyDev, publicKeyPro);
	}

	/**
	 * 根据环境状态，获取上传到支付宝的公钥信息
	 * 
	 * @Title: getAppPublicKey
	 * @Description: 根据环境信息，获取上传到支付宝的公钥信息
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:50:41
	 */
	public String getAppPublicKey() {
		// 伯乔测试公钥（上传支付宝）
		String appPublicKeyDev = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZWCNeBu9us6eGYhPUQUBJz5T/uG10FN3yTiuzu/o2Tu9L+SQUw1uP3AQtqXvZQGESC/aBT//A2BlnviPHRyyqLjoeHzx6tQrY+7xCwVAVEwH3lZ5+YZcT0S2GG50ePEK1urTiBTR0VbyPkK2BfsDM4Zl9BPhlFtbTL4JYEp9w9wIDAQAB";
		// 伯乔生产公钥（上传支付宝）
		String appPublicKeyPro = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6tHR4oGjW9f3ZVOqs1Ei5g58Xzb8rBtNWlgJqb9KNJIuWIaAHrphw3drj2ZygFyJCBKgZ3LnujcomJL64vicpEeg9WzdwW7C96NpyL3ThNaiN8mo+TUl0I5QQeFEzZQzhsBGb1zS2l3kO+NVqgRARz7TrV8IWI51KXAhPnOD+NQIDAQAB";
		return get(appPublicKeyDev, appPublicKeyPro);
	}

	/**
	 * 根据环境状态，获取aes加密密钥信息
	 * 
	 * @Title: getAesKey
	 * @Description: 根据环境状态，获取aes加密密钥信息
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:51:16
	 */
	public String getAesKey() {
		// 测试aes加密key
		String aesKeyDev = "Gb6UsRPOCO8HCltCNGxQXA==";
		// 生产aes加密key
		String aesKeyPro = "SyFPzkUgwG2bK9/l9CVIkA==";
		return get(aesKeyDev, aesKeyPro);
	}

	/**
	 * 根据环境状态，获取sellerId加密密钥信息
	 * 
	 * @Title: getSellerId
	 * @Description: 根据环境状态，获取sellerId加密密钥信息
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月17日 下午6:28:32
	 */
	public String getSellerId() {
		String sellerIdDev = "2088102169463358";
		String sellerIdPro = "2088411557088890";
		return get(sellerIdDev, sellerIdPro);
	}

	/**
	 * 根据环境状态，获取伯乔对接地址信息
	 * 
	 * @Title: getHostAddress
	 * @Description: 根据环境状态，获取伯乔对接地址信息
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:51:48
	 */
	public String getHostAddress() {
		return hostAddress;
	}

	/**
	 * 获取pid的配置信息
	 * 
	 * @Title: getPid
	 * @Description: 获取pid的配置信息
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月16日 上午10:29:26
	 */
	public String getPid() {
		String pidDev = "2088102169463358";
		String pidPro = "2088411557088890";
		return get(pidDev, pidPro);
	}

	/**
	 * 获取支付宝客户端对象信息
	 * 
	 * @Title: getAliClient
	 * @Description: 获取支付宝客户端对象信息
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:52:20
	 */
	public AlipayClient getAliClient() {
		return alipayClient;
	}

	/**
	 * 获取默认处理Action
	 * 该Action用于自定义标签，不需要包含hostAddress,contextPath
	 * 
	 * @Title: getDefaultAction
	 * @Description: 获取默认处理Action
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月23日 上午10:37:23
	 */
	public String getDefaultAction() {
		return defaultAction;
	}

	/**
	 * 判断环境状态，返回指定值
	 * 
	 * @Title: get
	 * @Description: 判断环境状态，返回指定值
	 * @param dev
	 *            测试环境返回值
	 * @param pro
	 *            生产环境返回值
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月15日 下午12:52:33
	 */
	private String get(String dev, String pro) {
		return isPro() ? pro : dev;
	}

	/**
	 * 获取当前系统的开发环境
	 * 
	 * @Title: isPro
	 * @Description: 获取当前系统的开发环境
	 * @return boolean 开发环境信息
	 *         true:生产环境
	 *         false:测试环境
	 * @author g.yang@i-vpoints.com
	 * @date 2016年11月18日 上午11:58:16
	 */
	public boolean isPro() {
		return isPro;
	}

}

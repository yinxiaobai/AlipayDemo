package com.alipay.one.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.alipay.one.AliPayConfig;
import com.alipay.one.services.PayServices;
import com.alipay.one.utils.DateUrl;
import com.alipay.one.utils.Tool;

/**
 * @date 2017年1月17日下午4:54:54
 * @author jq.yin@i-vpoints.com
 */
@RestController
@RequestMapping("/core")
public class CoreController {

	private static final Logger log = LoggerFactory
			.getLogger(CoreController.class);

	/**
	 * 支付宝下单
	 * 
	 * @date 2017年1月20日上午11:08:58
	 * @param request
	 * @param response
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping(value = "/alipay", produces = "text/html;charset=utf-8")
	public String applyPay() {
		log.info("【pay start】");
		String form = null;
		try {
			AlipayClient alipayClient = AliPayConfig.aliPayConfig
					.getAliClient();
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("out_trade_no", DateUrl.getTime());
			jsonObject.put("total_amount", "0.01");
			jsonObject.put("subject", "交易测试");
			jsonObject.put("seller_id",
					AliPayConfig.aliPayConfig.getSellerId());
			jsonObject.put("product_code", "QUICK_WAP_PAY");
			// 填充业务参数
			alipayRequest.setBizContent(jsonObject.toJSONString());
			alipayRequest.setReturnUrl(
					"http://wxgzh3.i-vpoints.com/ALiPayDemo/core/returnUrl");	// 回调地址
			alipayRequest.setNotifyUrl(
					"http://wxgzh3.i-vpoints.com/ALiPayDemo/core/notifyUrl"); // 通知地址
			// 调用SDK生成表单
			form = alipayClient.pageExecute(alipayRequest).getBody();
			log.debug("form:\n" + form);
			AlipayTradeWapPayResponse appPayResponse = new AlipayTradeWapPayResponse();
			System.out
					.println(appPayResponse.isSuccess() ? "调用成功" : "【调用失败！！！】");
		} catch (AlipayApiException e) {
			log.error(e.getMessage(), e);
		}
		// 直接将完整的表单html输出到页面
		return form;
	}

	/**
	 * 订单查询接口
	 * 
	 * @date 2017年1月22日下午1:34:30
	 * @param tradeNo
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping(value = "/tradeInfo/{tradeNo}")
	public AlipayTradeQueryResponse query(
			@PathVariable(required = false) String tradeNo) {
		AlipayClient alipayClient = AliPayConfig.aliPayConfig.getAliClient();
		AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
		JSONObject bizJSON = new JSONObject();

		bizJSON.put("out_trade_no", tradeNo);	// 订单号 二选一，优先选此
		// bizJSON.put("trade_no", ""); // 支付宝订单号
		queryRequest.setBizContent(bizJSON.toJSONString());
		AlipayTradeQueryResponse response = null;
		try {
			response = alipayClient.execute(queryRequest);
			if (response.isSuccess()) {
				log.info("调用成功");
				log.info("交易状态:" + response.getTradeStatus());
			} else {
				log.info("调用失败");
			}
		} catch (AlipayApiException e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

	/**
	 * 退款申请接口
	 * 
	 * @date 2017年1月22日下午2:07:10
	 * @param tradeNo
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping("/refund/{tradeNo}")
	public AlipayTradeRefundResponse tradeRefund(
			@PathVariable(required = false) String tradeNo) {
		AlipayClient alipayClient = AliPayConfig.aliPayConfig.getAliClient();
		AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
		JSONObject bizJSON = new JSONObject();
		bizJSON.put("out_trade_no", tradeNo);
		bizJSON.put("refund_amount", 0.01);

		refundRequest.setBizContent(bizJSON.toJSONString());
		AlipayTradeRefundResponse refundResponse = null;
		try {
			refundResponse = alipayClient.execute(refundRequest);
			if (refundResponse.isSuccess()) {
				log.info("调用成功");
				log.info(refundResponse.getOpenId());
				log.info("本次退款情况:" + refundResponse.getFundChange());
			} else {
				log.info("调用失败");
			}
		} catch (AlipayApiException e) {
			log.error(e.getMessage(), e);
		}
		return refundResponse;
	}

	/**
	 * 对账单下载接口
	 * 
	 * @date 2017年1月22日下午3:11:27
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping(value = "/downLoad", produces = "text/html;charset=utf-8")
	public String downLoadData() {
		// public AlipayDataDataserviceBillDownloadurlQueryResponse
		// downLoadData() {
		AlipayClient alipayClient = AliPayConfig.aliPayConfig.getAliClient();
		AlipayDataDataserviceBillDownloadurlQueryRequest downloadurlQueryRequest = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		JSONObject bizJSON = new JSONObject();
		bizJSON.put("bill_type", "trade");	// trade、signcustomer
		bizJSON.put("bill_date", "2017-01-10");	// yyyy-MM-dd
		downloadurlQueryRequest.setBizContent(bizJSON.toJSONString());
		AlipayDataDataserviceBillDownloadurlQueryResponse downloadurlQueryResponse = null;
		String url = "";
		try {
			downloadurlQueryResponse = alipayClient
					.execute(downloadurlQueryRequest);
			url = downloadurlQueryResponse.getBillDownloadUrl();
			if (downloadurlQueryResponse.isSuccess()) {
				log.info("调用成功");
				log.info("账单下载地址:"
						+ downloadurlQueryResponse.getBillDownloadUrl());
			}
		} catch (AlipayApiException e) {
			log.error(e.getMessage(), e);
		}
		// return downloadurlQueryResponse;
		// url = "<script>location.href=\""+url+"\"</script>";
		return url;
	}

	/**
	 * 支付宝支付回调地址
	 * 
	 * @date 2017年1月20日上午11:08:07
	 * @param request
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
	public Map<String, String> returnInfo(HttpServletRequest request) {
		log.info("【回调方式:】" + request.getMethod());
		Map<String, String> map = Tool.requestMap(request);
		log.info(map.toString());
		log.info("【return success】");
		return map;
	}

	/**
	 * 支付宝支付通知地址
	 * 
	 * @date 2017年1月20日上午11:08:19
	 * @param request
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
	public String notifyInfo(HttpServletRequest request) {
		log.info("【通知方式:】" + request.getMethod());
		Map<String, String> map = Tool.requestMap(request);
		log.info(map.toString());
		try {
			PayServices.citiService(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("【notify success】");
		return "success";
	}

}

package com.alipay.one.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @date 2017年1月20日下午3:15:56
 * @author jq.yin@i-vpoints.com
 */
public class PayServices {

	private static final Logger log = LoggerFactory
			.getLogger(PayServices.class);

	/**
	 * @date 2017年1月20日下午3:26:54
	 * @param map
	 * @author jq.yin@i-vpoints.com
	 */
	public static void citiService(Map<String,String> map) {
		String outTradeNo = map.get("out_trade_no");
		String buyerLogonId = map.get("buyer_logon_id");	// 买家支付宝账号
		String sellerEmail = map.get("seller_email");		// 卖家支付宝账号
		String tradeStatus = map.get("trade_status");		// 交易状态
		String totalAmount = map.get("total_amount");		// 订单金额
		String invoiceAmount = map.get("invoice_amount");	// 开票金额
		String pointAmount = map.get("point_amount");		// 使用集分宝支付的金额
		String subject = map.get("subject");
		String buyerPayAmount = map.get("buyer_pay_amount");			// 付款金额
		String receiptAmount = map.get("receipt_amount");	// 实收金额
		String fundBillList = map.get("fund_bill_list");	// 支付金额信息
		JSONObject json = (JSONObject) JSONObject.parse(fundBillList.substring(1,fundBillList.length()-1));
		String fundChannel = json.getString("fundChannel");	// 支付渠道
		String amount = json.getString("amount");			// 支付金额
		
		log.info(map.toString());
		log.info("【{}:买家 {} 通过 {} 方式支付了 {} 元,从卖家 {} 购买:{},{}】",outTradeNo,buyerLogonId,fundChannel,amount,sellerEmail,subject,tradeStatus);
		log.info("【订单金额{},开票金额{},集分宝金额{},付款金额{},实收金额{}】",totalAmount,invoiceAmount,pointAmount,buyerPayAmount,receiptAmount);
	};
}

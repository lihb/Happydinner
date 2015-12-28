package com.handgold.pjdc.base;


/**
 * @author Administrator
 *
 */
public class Constant {

	/**
	 * appSecret
	 */
	public static final String TYY_APP_SECRET = "rx8C3Zgh";

	/**
	 * 客户端类型
     */
	public final static String CLIENT_TYPE = "TELEANDROID";
	/**网络类型--所有网络*/
	public final static int NETWORK_TYPE_ALL = 1;
	/**网络类型--wifi*/
	public final static int NETWORK_TYPE_ONLY_WIFI = 0;
	
	// 检查首次使用引导界面所需的版本号
	public final static int FIRST_USE_GUIDE_VERSION_CODE = 59;
	/** 控制某些提示的显示的id号 */
	public static final int CONTROL_PROMPT_ALBUMBACK_ID = 60;

    //——————————————————————————————————————————————————微信支付——————————————————————————————

	// 公众号ID
	public static final String APP_ID = "";

	// 商户号
	public static final  String MCH_ID = "";

	// 用来生成签名的key
	public static final String WECHAT_KEY = "";

	// 回调地址
	public static final  String NOTIFY_URL = "";

	// 统一下单 api
	public static String UNIFY_PAY_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	//——————————————————————————————————————————————————微信支付——————————————————————————————



}

package com.yonghui.portal.util;

import java.text.SimpleDateFormat;



/**
 * 用户记录各种常量
 * 
 * @author kwq
 */
public class Const  {
	private static final long serialVersionUID = -5499557044989645565L;

	public static final String UTF8 = "utf-8";
	
	/**
	 * 用户置于session中的key
	 */
	public static final String SESSION_USER_KEY = "UserBeanKey";
	
	/**
	 * 用户置于session中的key,当前站点
	 */
	public static final String CURRENT_SITE_CODE = "currentSiteCode";

	/**
	 * 用户置于session中的key，用户拥有站点列表
	 */
	public static final String CURRENT_SITE_CODE_LIST="cuerrntSiteCodeList";
	
	/**
	 * 用户置于session中的key，用户拥有站点列表长度
	 */
	public static final String SITE_LIST_SIZE="siteListSize";
	/**
	 * 
	 * Redis 缓存名称
	 */
	// public static final String JEDIS_CACHE_KEY_PRIVILEGE = "yw_p_"; // 访问权限

	// public static final String JEDIS_CACHE_NAME = "YW_"; // 通用缓存名

	// public static final String JEDIS_CACHE_I18N_NAME = "YW_i18n_"; // 国际化

	public static final String JEDIS_CACHE_VERSION = "YW_ver_"; // 数据版本号

	public static final String JEDIS_DATA_CACHE = "YW_$data_"; // 数据缓存

	/**
	 * 图片验证码保存在session中的名称
	 */
	public static final String VALID_CODE = "random_code";

	/**
	 * 权限访问
	 */
	public static final String CACHE_KEY_MENU_PRIVILEGE = "mp_";

	/**
	 * 角色对应的功能组
	 */
	public static final String CACHE_KEY_ROLE_FUNC_MAPPING = "RFM_";

	/**
	 * 登录使用的随机加密因子变量名
	 */
	public static final String LOGIN_MCRYPT_KEY = "_$token";

	/**
	 * 产品分类的缓存
	 */
	public static final String CACHE_KEY_VALID_CATEGORY_LIST = "_CVL";
	public static final String CACHE_KEY_VALID_BRAND_LIST = "_CBL";

	/**
	 * 缓存信息的key
	 */
	public static final String CACHE_KEY_AREA = "_$area_";
	public static final String CACHE_KEY_DELIVERY_AREA = "_$d_area_";
	public static final String CACHE_KEY_BRAND = "_$brand_";
	public static final String CACHE_KEY_CATEGORY = "_$category_";
	public static final String CACHE_KEY_PRODUCT = "_$product_";
	public static final String CACHE_KEY_PRODUCT_EXT_PROP_DEF = "_$pe_prop_";
	public static final String CACHE_KEY_DICT = "_$dict_";
	public static final String CACHE_KEY_TB_OAUTH = "_$oauth_";
	public static final String CACHE_KEY_TB_OAUTHS = "_$oauths_";
	public static final String CACHE_KEY_TMC_PROCESSOR = "_$tmc_";
	public static final String CACHE_KEY_ONSALE_ITEM = "_$os_oi_";
	public static final String CACHE_KEY_USER = "_$user_";
	public static final String CACHE_KEY_SMS_TEMPLATE = "_$st_";
	public static final String CACHE_KEY_ORG_CODE = "_$oc_";
	public static final String CACHE_KEY_ITEM = "os_it_";
	public static final String CACHE_KEY_SID_GEN = "_$sg_";
	public static final String JEDIS_CACHE_VALIDATION_CODE = "_$vc$_";
	public static final String CACHE_KEY_VALID_CODE_APP = "_$vcodeapp_";
	public static final String CACHE_KEY_ENTERPRISE = "_$ent_";
	/**
	 * 缓存用户登录次数
	 */
	public static final String USER_LOGIN_COUNT = "_$user_";

	/**
	 * 用户是否必须使用验证码
	 */
	public static final String USER_REQUIRE_VALID_CODE = "_$need_code_";

	public static final String CACHE_KEY_JOB_CATEGORY = "_$jc";

	public static SimpleDateFormat DF_YMD = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat DF_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}

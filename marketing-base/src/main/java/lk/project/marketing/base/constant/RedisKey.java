package lk.project.marketing.base.constant;

/**
 * Created by dell on 2017/9/20.
 */
public class RedisKey {
    /**
     * Token Definition Keys
     * */
    /** 1.Access Token */
    public final static String ENN_API_ACCESS_TOKEN_ = "ENN_API_ACCESS_TOKEN_";
    /** 3.PC Access Token */
    public final static String ENN_API_PC_ACCESS_TOKEN_ = "ENN_API_PC_ACCESS_TOKEN_";
    /** 4.MOBILE Access Token */
    public final static String ENN_API_MOBILE_ACCESS_TOKEN_ = "ENN_API_MOBILE_ACCESS_TOKEN_";

    /** 2.Enterprise 3rd-party SaaS application token redis keys */
    public final static String ENN_SAAS_APP_TOKEN_CFG_LIST_ = "ENN_SAAS_APP_TOKEN_CFG_LIST";

    /** 1.Access Token */
    public final static String PAAS_API_ACCESS_TOKEN = "PAAS_API_ACCESS_TOKEN_";

    /** 创建的session ID */
    public final static String SHARE_SESSION_ID_ = "ENN_SHARE_SESSION_ID_";

    /** Redis Shiro cache */
    public final static String REDIS_SHIRO_CACHE_NAME = "ENN_SHIRO_CACHE_NAME";
    public final static String REDIS_SHIRO_CACHE_ = "ENN_SHIRO_CACHE_";
    public final static String REDIS_CACHE_USER_PERMISSION = "CACHE_USER_PERMISSION_";
    public final static String REDIS_CACHE_USER_ROLE = "CACHE_USER_ROLE_";
    public final static String REDIS_CACHE_SHIRO_USER_OBJECT_ = "CACHE_SHIRO_USER_OBJECT_";

    /** 付款交易记录缓存块和键(商户ID+订单编号唯一) */
    public final static String REGION_LK_PAYMENT_INFO = "REGION_LK_PAYMENT_INFO";
    public final static String HASH_KEY_LK_PAYMENT_INFO = "LK:PAYMENT:INFO:%s:%s";

    /** 后台系统商户参数配置信息的缓存块和键(商户编码/ID映射,商户ID+支付渠道ID唯一) */
    public final static String REGION_LK_MERCHANT_BACKEND_PARAM_CFG = "REGION_LK_MERCHANT_BACKEND_PARAM_CFG";
    public final static String HASH_KEY_LK_MERCHANT_BACKEND_PARAM_CFG = "LK:PAYMENT:MERCHANT:BACKEND:%s:%s";
    public final static String HASH_KEY_LK_MERCHANT_BACKEND_CFG_PREFIX = "LK:PAYMENT:MERCHANT:BACKEND:";

    /** 微信获取keyPrivate信息KEY */
    public final static String HASH_KEY_WEIXIN_MERCHANT = "WEIXIN:MERCHANT:%s";

    public final static String REGION_LK_MERCHANT_BACKEND_PARAM_CFG_CERT = "REGION_LK_MERCHANT_BACKEND_PARAM_CFG_CERT";


    /** 运行时获取商户参数配置的缓存块和键(编码/ID映射,商户+支付渠道ID唯一) */
    public final static String REGION_LK_MERCHANT_PLATFORM_CFG_CONTEXT = "REGION_LK_MERCHANT_PLATFORM_CFG_CONTEXT";
    public final static String HASH_KEY_LK_MERCHANT_PLATFORM_CFG_CONTEXT = "LK:PAYMENT:MERCHANT:CONTEXT:%s:%s";

    /** 应用缓存块和键 */
    public final static String REGION_LK_PAYMENT_APP_CODE = "REGION_LK_PAYMENT_APP_CODE";
    public final static String HASH_KEY_LK_PAYMENT_APP_CODE = "LK:PAYMENT:APP_CODE:%s";

    /** 将第三方报文改造为移动端SDK所需报文放入缓存以便客户端后续操作 */
    public final static String LK_PAYMENT_MOBILE_REQUEST_DATA = "LK:PAYMENT:MOBILE:REQUEST:%s:%s:%s :%s:%s:%s";

    /** URL唯一请求Token */
    public final static String LK_PAYMENT_CANCEL_URL_TOKEN="LK:PAYMENT_CANCEL_URL_TOKEN:";
    public final static String LK_PAYMENT_RETURN_URL_TOKEN="LK:PAYMENT_RETURN_URL_TOKEN:";

    /**请求URL*/
    public final static String LK_PAYMENT_REQUEST_URL_IP="LK:PAYMENT_REQUEST_URL:%s:%s";

    /** 某商户微信SRA公钥KEY*/
    public final static String LK_PAYMENT_WEIXIN_SRA_PUB_KEY="LK:PAYMENT:WEIXIN:SRA:PUB:%s";
}

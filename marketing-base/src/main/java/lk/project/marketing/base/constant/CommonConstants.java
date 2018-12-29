package lk.project.marketing.base.constant;

/**
 * Created by alexlu on 9/20/2017.
 */
public class CommonConstants {
    /**
     * Data Cache Params Settings
     */
    public final static int CACHE_EXPIRATION = 50 * 60 * 60;
    public final static int CACHE_EXPIRATION_WEEKLY = (7 * 24 + 2) * 60 * 60;
    public final static int CACHE_EXPIRATION_WEEKLY_NEW = (14 * 24) * 60 * 60;
    public final static int CACHE_EXPIRATION_ONE_DAY = 24 * 60 * 60;//一天
    public final static int CACHE_EXPIRATION_ONE_MINUTE = 60;//60秒
    public final static int CACHE_EXPIRATION_FIVE_MINUTES = 300;//五分钟  300秒
    public final static int CACHE_EXPIRATION_TEN_MINUTES = 600;//十分钟
    public final static int CACHE_EXPIRATION_ONE_HOUR = 3600;//一个小时
    public final static int CACHE_VERIFY_CODE_EXPIRATION = 50 * 60;

    /**
     * HTTP请求方式
     */
    public final static String HTTP_METHOD_GET = "GET";
    public final static String HTTP_METHOD_POST = "POST";

    /**
     * 分页：每页显示条数
     */
    public final static Integer DEFAULT_QUERY_IS_PAGER = 1;
    public final static int DEFAULT_CAHCE_PAGE_DATA_OFFSET = 500;
    public final static int SPEED_CAHCE_PAGE_DATA_OFFSET = 1000;
    public final static int DEFAULT_LIST_PAGE_INDEX = 1;
    public final static int DEFAULT_LIST_PAGE_SIZE = 20;

    public final static int DEFAULT_CREATOR_ID = 275;
    public final static boolean DEFAULT_VALID = false;
    public final static boolean DEFAULT_INVALID = true;

    /**
     * Default time/date parseStr
     */
    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public final static String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public final static String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String CONSULTANT_FREE_TIME_DATE_FORMAT = "yyyyMMdd";

    /**
     * 符号定义
     */
    public final static String TAG_COMMA = ",";
    public final static String TAG_DELIMITER = "-";
    public final static String TAG_DOT = ".";
    public final static String TAG_EQUALS = "=";
    public final static String TAG_POUND = "#";
    public final static String TAG_UNDERLINE = "_";
    public final static String TAG_COLON = ":";
    public final static String TAG_LEFT_SLASH = "/";
    public final static String TAG_AT = "@";
    public final static String TAG_EXCLAMATION = "!";

    /**
     * 条件操作符
     */
    public final static String CRITERIA_OPERATOR_AND = "and";
    public final static String CRITERIA_OPERATOR_OR = "or";
    public final static String CRITERIA_OPERATOR_EQUALS = "=";
    public final static String CRITERIA_OPERATOR_NOT_EQUALS = "!=";
    public final static String CRITERIA_OPERATOR_GT = ">";
    public final static String CRITERIA_OPERATOR_GT_EQUALS = ">=";
    public final static String CRITERIA_OPERATOR_LT = "<";
    public final static String CRITERIA_OPERATOR_LT_EQUALS = "<=";
    public final static String CRITERIA_OPERATOR_BOTH_LIKE = ":";
    public final static String CRITERIA_OPERATOR_LEFT_LIKE = "l:";
    public final static String CRITERIA_OPERATOR_RIGHT_LIKE = ":l";
    public final static String CRITERIA_OPERATOR_BOTH_NULL = "null";
    public final static String CRITERIA_OPERATOR_NOT_NULL = "!null";
    public final static String CRITERIA_OPERATOR_IN = "in";

    /**
     * 数字常量
     */
    public final static int NUMBER_1 = 1;
    public final static int NUMBER_2 = 2;

    /**
     * 编码类型
     */
    public final static String ENCODING_UTF_8 = "UTF-8";

    /**
     * 促销规则类型(0:发放规则,1:使用规则)
     */
    public final static int PROMOTION_RULE_TYPE_RECEIVE_COUPON = 0;
    public final static int PROMOTION_RULE_TYPE_CONSUME_COUPON = 1;

    /**
     * 优惠券扣减对象类型(1-订单,2-商品)
     */
    public final static int COUPON_REDUCE_TARGET_TYPE_ORDER = 1;
    public final static int COUPON_REDUCE_TARGET_TYPE_ORDER_ITEM = 2;

    /**
     * 当前可用优惠券项最新状态(0-未选中,1-已选中,2-不可使用)
     */
    public final static int CURRENT_USER_USE_COUPON_STATUS_NOT_SELECTED = 0;
    public final static int CURRENT_USER_USE_COUPON_STATUS_SELECTED = 1;
    public final static int CURRENT_USER_USE_COUPON_STATUS_FORBIDDEN = 2;

    public final static String IMAGE_TYPE_JPEG = "JPEG";

    public final static int AMOUNT_SCALE_NUM = 2;
}

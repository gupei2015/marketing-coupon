/**  
  * @Title: BusinessErrorCodeEnum.java
  * @Package cn.enncloud.common.enums
  * @Description: BusinessErrorCodeEnum
  * @author alexlu
  * @date 2017年6月14日
  * @version V1.0  
  */
    
package lk.project.marketing.client.exception;

/**
  * @ClassName: BusinessErrorCodeEnum
  * @Description: BusinessErrorCodeEnum
  * @author alexlu
  * @date 2017年6月14日
  *
  */

public enum BusinessErrorCodeEnum {
    SUCCESS(200,"操作成功"),
    PARAMETER_ERROR(100001,"参数有误"),
    UNDEFINED_ERROR(100002,"未知错误"),
    VERIFICATIONCODE_ERROR(100003,"验证码错误"),
    THIRDPARTY_SYSTEM_ERROR(100004,"第三方业务系统异常"),
    REQUEST_TOO_MANY(100005,"请求过于频繁,请检查!"),

    //系统操作相关异常
    SAVE_TO_REDIS_EXCEPTION(11001, "插入Redis缓存服务器缓存异常!"),

    //优惠券相关异常
    NOT_FOUND_COUPON_INFO(14001, "无法找到优惠券记录!"),
    USER_COUPON_INVALID(14002, "此张优惠券已失效!"),
    EMPTY_AVAILABLE_COUPON(14003, "可用优惠券不能为空!"),

    //活动相关异常
    NOT_FOUND_PROMOTION_ACTIVITY(15001, "无法找到优惠券对应的促销活动!"),

    //促销规则相关异常
    PROMOTION_ACTIVITY_RULE_MATCH_USER_FAILED(16001, "用户适用对象条件不匹配!"),
    PROMOTION_ACTIVITY_RULE_MATCH_ORDER_ITEM_FAILED(16002, "商品适用范围条件不匹配!"),
    PROMOTION_ACTIVITY_RULE_CHECK_SUCCEED(16003, "优惠券活动规则可用性校验匹配成功!"),
    VERIFY_COUPON_AVAILABLE_RULES_CHECK_FAILED(16004, "优惠券活动规则或结算规则可用性校验不匹配!"),
    VERIFY_COUPON_MATCH_PROMOTION_RULES_FAILED(16005, "校验待用的各优惠券-发现适用范围条件不匹配!"),

    //发券相关异常
    NOT_FOUND_COUPON_RECEIVE_DETAIL_LIST(21001, "无法找到发券详情列表记录!"),
    COUPON_RECEIVE_STATUS_EXCEPTION(21002, "领券记录状态异常!"),
    COUPON_RECEIVE_FIELD_EXCEPTION(21003, "优惠券领券信息字段异常!"),
    COUPON_RECEIVE_DETAIL_QR_CODE_GENERATE_FAILED(21004, "优惠券领券生成劵二维码失败!"),

    //用劵相关异常
    COUPON_CONSUME_RECORD_STORE_EXCEPTION(17001, "优惠券用劵主记录保存失败!"),
    NOT_FOUND_USER_COUPON_SUMMARY_RECORD(17002, "无法找到优惠券汇总记录!"),
    PROCESS_USER_CONSUMING_COUPON_FAILED(17003, "处理当前使用的优惠券操作失败!"),
    COUPON_SUMMARY_QUANTITY_NOT_AVAILABLE(17004, "优惠券可用数量不一致!"),
    NOT_FOUND_USER_COUPON_CONSUME_RECORD(17005, "无法找到优惠券用劵记录!"),
    NOT_FOUND_COUPON_CONSUME_DETAIL_LIST(17006, "无法找到用券详情列表记录!"),
    COUPON_CONSUME_FIELD_EXCEPTION(17007, "优惠券用券信息字段异常!"),

    //结算相关异常
    NOT_FOUND_COUPON_ACCOUNT_RULE(18001, "无法找到优惠券结算规则!"),
    COUPON_ACCOUNT_RULE_MATCH_THRESHOLD_AMOUNT_FAILED(18002, "校验优惠券结算规则-[满金额]条件不匹配!用户金额:%s,满减阈值条件金额:%s"),
    COUPON_ACCOUNT_RULE_MATCH_THRESHOLD_QUANTITY_FAILED(18003, "校验优惠券结算规则-[满数量]条件不匹配!用户数量:%s,满减阈值条件数量:%s"),
    SETTLE_CALC_SUM_REWARD_AMOUNT_FAILED(18004, "计算累加的扣减额度失败!"),
    CHECK_COUPON_ACCOUNT_RULES_MATCH_FAILED(18005, "校验匹配优惠券满减结算规则(金额和数量)失败!"),
    VERIFY_ACCOUNT_RULES_CHECK_SUCCEED(18006, "优惠券活动规则可用性校验匹配成功!"),
    EMPTY_ACCOUNT_RULES_CHECK_RETURN_REDUCE_AMOUNT_MAP(18007, "订单或商品的各类优惠券扣减数据为空!"),
    EMPTY_USER_SELECTED_COUPONS_LIST(18008, "动态计算用户选中的优惠券列表源数据不能为空!"),
    USER_SELECTED_COUPONS_ACCOUNT_RULE_MATCH_FAILED(18009, "动态计算用户选中的优惠券-校验每种优惠券是否匹配结算递减的满减条件失败!"),
    EMPTY_USER_SELECTED_COUPONS_PROMOTION_PRICE_EXCEPTION(18010, "计算订单或商品的每种优惠券本次总共应扣减的金额-优惠价不能配置为空!"),
    USER_SELECTED_COUPONS_REDUCE_CALC_ERROR(18011, "计算订单或商品的每种优惠券本次总共应扣减的金额-计算出错!"),
    EMPTY_CALC_TOTAL_REDUCE_BY_EACH_COUPON_REQ_INFO(18012, "计算订单或商品的每种优惠券-本次应扣结算规则信息不能为空!"),
    NO_REDUCE_AMOUNT(18013,"金额扣减类型结算结果的扣减金额不能为零，使用优惠券错误"),
    INVALID_POINT_RATIO(18014,"积分兑换率设置错误，结算失败"),

    //优惠券汇总相关异常
    EMPTY_COUPON_SUMMARY_INFO(19001, "优惠券汇总信息不能为空!"),
    COUPON_SUMMARY_FIELD_EXCEPTION(19002, "优惠券汇总信息字段异常!"),
    NOT_FOUND_COUPON_SUMMARY_INFO(19003, "找不到用户优惠券汇总记录!"),

    //领券相关异常
    EMPTY_PROMOTION_ACTIVITY(30001,"没有指定发券／领券对应的促销活动"),
    NO_ACTIVE_PROMOTION(30002, "优惠券没有有效的促销活动，无法使用"),
    NOT_MATCHED_ACTIVITY_RULE(30003,"优惠券促销活动适用条件不满足"),
    NOT_MATCHED_RECEIVE_PROMOTION_RULE(30004,"不满足领券条件，促销活动:%s"),
    RECEIVE_COUPON_TIME(30005,"等待领券超时失败！请重新领券"),
    NO_RELATED_COUPON(30006,"促销活动未关联优惠券，无法使用"),
    NO_RECEIVE_QUANTITY(30007,"未指定领券数量，发券失败"),
    EXCEED_USER_RECEIVE_QUANTITY(30008,"领券数量超出用户可领取数量，发券失败"),
    EXCEED_ACTIVITY_RECEIVE_QUANTITY(30009,"领券数量超出促销活动剩余可领取数量，发券失败"),

    //其他异常
    EMPTY_USER_BASIC_INFO(20001, "缺少用户基础信息!"),
    EMPTY_ORDER_ITEM_INFO(20002, "缺少订单项商品信息!"),
    EMPTY_ORDER_INFO(20003, "缺少订单基础信息!"),
    EMPTY_COUPON_REDUCE_AMOUNT_MAP(20004, "缺少优惠券扣减集合源信息!"),

    ;

    private Integer code;
    private String message;

    BusinessErrorCodeEnum(Integer code, String message){
	this.code = code;
	this.message = message;
    }
    
    /**
    * @return code
    */
    public Integer getCode() {
        return code;
    }
    
    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    
    /**
    * @return message
    */
    public String getMessage() {
        return message;
    }

    
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取message
     * @param code
     * @return
     */
    public static String getValueByCode(Integer code) {
        for (BusinessErrorCodeEnum businessError : BusinessErrorCodeEnum.values()) {
            if (businessError.getCode().equals(code)) {
                return businessError.getMessage();
            }
        }
        return null;
    }

}

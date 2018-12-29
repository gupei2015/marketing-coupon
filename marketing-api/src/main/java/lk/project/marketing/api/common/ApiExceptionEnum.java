package lk.project.marketing.api.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口异常定义
 * Created by alexlu on 2017/10/13.
 */
public enum ApiExceptionEnum {
    //COUPON_GENERATE_EXCEPTION(-200001,"/coupon/create" ,"生成优惠券"),
    QUERY_ORDER_CONSUMED_COUPON_EXCEPTION(-300001,"/couponConsume/order/history/query","查询订单用券记录异常"),
    QUERY_MATCHED_COUPON_FOR_PURCHASE_ITEM_EXCEPTION(-300002,"/couponConsume/item/available/query","查询购买商品可用优惠券记录异常"),
    QUERY_MATCHED_COUPON_FOR_ORDER(-300003,"/couponConsume/order/available/query","查询订单可用优惠券记录异常"),
    SORT_AVAILABLE_MATCHED_COUPONS(-300004,"/couponConsume/available/sort","排序可用优惠券异常"),
    CONSUME_COUPON(-300005,"/couponConsume/consume","用券异常"),
    PRODUCE_COUPON(-400001,"/couponReceive/produceCoupon","领券异常"),
    ORDER_SETTLEMENT(-500001,"/couponSettle/order/settlement","订单结算异常"),
    SELECT_COUPON(-500002,"/couponSettle/dynamicSelect","动态查询优惠券异常")
    ;

    private Integer code;
    private String apiMethodName;
    private String message;

    ApiExceptionEnum(Integer code, String apiMethodName, String message){
        this.code = code;
        this.apiMethodName = apiMethodName;
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
     * @return API method name
     */
    public String getApiMethodName() {
        return apiMethodName;
    }

    /**
     * @param apiMethodName the API method name to set
     */
    public void setApiMethodName(String apiMethodName) {
        this.apiMethodName = apiMethodName;
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
     * 根据方法名读取异常信息
     * @param apiActionUrl
     * @return
     */
    public static Map<Integer,String> getExpCodeMsgByApiUrl(String apiActionUrl) {
        Map<Integer,String> apiCodeMsg = new HashMap();
        for (ApiExceptionEnum apiException : ApiExceptionEnum.values()) {
            if (apiActionUrl.indexOf(apiException.getApiMethodName()) > 0) {
                apiCodeMsg.put(apiException.getCode(),apiException.getMessage());
                return apiCodeMsg;
            }
        }

        apiCodeMsg.put(40007,"系统异常!");
        return apiCodeMsg;
    }

    /**
     * 根据Code获取枚举对象
     * @param code
     * @return
     */
    public static ApiExceptionEnum getValueByCode(Integer code){
        for(ApiExceptionEnum apiExceptionEnum : ApiExceptionEnum.values()){
            if(apiExceptionEnum.getCode().equals(code)){
                return apiExceptionEnum;
            }
        }

        return null;
    }
}

package lk.project.marketing.base.enums;

/**
 * 优惠券消费状态枚举
 */
public enum CouponConsumeStatusEnum {
    USED_WITH_PAYED(0, "已支付使用劵完毕"),
    RETURN_BACK(1, "退回优惠券");

    /**
     * 优惠券消费状态编码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String statusName;


    /**
     * 使用优惠券消费状态码和优惠券消费状态名称构造枚举
     *
     * @param code    优惠券消费状态编码
     * @param statusName 优惠券消费状态名称
     */
    CouponConsumeStatusEnum(Integer code, String statusName) {
        this.code = code;
        this.statusName = statusName != null ? statusName : "";
    }

    /**
     * 获取优惠券消费状态号
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取优惠券消费状态名称
     *
     * @return String
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 根据Code获取枚举对象
     * @param code
     * @return
     */
    public static CouponConsumeStatusEnum getValueByCode(Integer code){
        for(CouponConsumeStatusEnum couponConsumeStatusEnum : CouponConsumeStatusEnum.values()){
            if(couponConsumeStatusEnum.getCode().equals(code)){
                return couponConsumeStatusEnum;
            }
        }

        return null;
    }
}

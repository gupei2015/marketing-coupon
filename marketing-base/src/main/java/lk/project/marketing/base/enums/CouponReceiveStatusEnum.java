package lk.project.marketing.base.enums;

/**
 * 领取优惠券状态枚举
 */
public enum CouponReceiveStatusEnum {
    NOT_USED(0, "未使用"),
    USED(1, "已使用"),
    PARTIAL_USED(2, "部分使用"),
    INVALID(3, "已失效");

    /**
     * 领取优惠券状态编码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String statusName;


    /**
     * 领取优惠券状态码和优惠券状态名称构造枚举
     *
     * @param code    优惠券状态编码
     * @param statusName 优惠券状态名称
     */
    CouponReceiveStatusEnum(Integer code, String statusName) {
        this.code = code;
        this.statusName = statusName != null ? statusName : "";
    }

    /**
     * 获取优惠券类型号
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取优惠券类型名称
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
    public static CouponReceiveStatusEnum getValueByCode(Integer code){
        for(CouponReceiveStatusEnum couponReceiveStatusEnum : CouponReceiveStatusEnum.values()){
            if(couponReceiveStatusEnum.getCode().equals(code)){
                return couponReceiveStatusEnum;
            }
        }

        return null;
    }
}

package lk.project.marketing.base.enums;

/**
 * 优惠券状态枚举
 */
public enum CouponStatusEnum {
    USED(0, "已启用"),
    NOT_USED(1, "未启用");

    /**
     * 优惠券状态编码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String statusName;


    /**
     * 使用优惠券状态码和优惠券状态名称构造枚举
     *
     * @param code    优惠券状态编码
     * @param statusName 优惠券状态名称
     */
    CouponStatusEnum(Integer code, String statusName) {
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
    public static CouponStatusEnum getValueByCode(Integer code){
        for(CouponStatusEnum couponStatusEnum : CouponStatusEnum.values()){
            if(couponStatusEnum.getCode().equals(code)){
                return couponStatusEnum;
            }
        }

        return null;
    }
}

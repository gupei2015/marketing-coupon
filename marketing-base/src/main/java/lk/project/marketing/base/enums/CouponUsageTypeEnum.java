package lk.project.marketing.base.enums;

/**
 * 优惠券使用类型枚举
 */
public enum CouponUsageTypeEnum {
    ORDER_COUPON(1, "订单使用优惠券"),
    ORDER_ITEM_COUPON(2, "订单明细/商品/服务使用优惠券");

    /**
     * 优惠券类型编码
     */
    private Integer code;

    /**
     * 优惠券类型名称
     */
    private String typeName;


    /**
     * 使用优惠券类型号和优惠券类型名称构造枚举
     *
     * @param code    优惠券类型号
     * @param typeName 优惠券类型名称
     */
    CouponUsageTypeEnum(Integer code, String typeName) {
        this.code = code;
        this.typeName = typeName != null ? typeName : "";
    }

    /**
     * 获取优惠券使用类型号
     *
     * @return int
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取优惠券使用类型名称
     *
     * @return String
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 根据Code获取枚举对象
     * @param code
     * @return
     */
    public static CouponUsageTypeEnum getValueByCode(Integer code){
        for(CouponUsageTypeEnum couponUsageTypeEnum : CouponUsageTypeEnum.values()){
            if(couponUsageTypeEnum.getCode().equals(code)){
                return couponUsageTypeEnum;
            }
        }

        return null;
    }
}

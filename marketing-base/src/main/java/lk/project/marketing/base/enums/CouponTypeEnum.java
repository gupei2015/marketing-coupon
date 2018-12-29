package lk.project.marketing.base.enums;

/**
 * 优惠券(模板)类型枚举
 */
public enum CouponTypeEnum {
    POINTS(0, "积分"),
    /**
     * 包括现金抵扣和折扣,参考满减规则
     */
    DISCOUNT(1, "满减折扣"),
    GIFT(2, "赠送赠品");

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
    CouponTypeEnum(Integer code, String typeName) {
        this.code = code;
        this.typeName = typeName != null ? typeName : "";
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
    public String getTypeName() {
        return typeName;
    }

    /**
     * 根据Code获取枚举对象
     * @param code
     * @return
     */
    public static CouponTypeEnum getValueByCode(Integer code){
        for(CouponTypeEnum couponTypeEnum : CouponTypeEnum.values()){
            if(couponTypeEnum.getCode().equals(code)){
                return couponTypeEnum;
            }
        }

        return null;
    }
}

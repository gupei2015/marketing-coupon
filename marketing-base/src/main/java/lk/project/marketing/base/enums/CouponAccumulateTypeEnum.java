package lk.project.marketing.base.enums;

/**
 * 优惠券使用规则枚举
 */
public enum CouponAccumulateTypeEnum {

    EXCLUSIVE(0, "排他"),
    ACCUMULATE(1, "累计"),
    PRIORITIZING(2, "择优");

    /**
     * 优惠券使用规则号码
     */
    private Integer code;
    /**
     * 优惠券使用规则名称
     */
    private String typeName;

    /**
     * 使用优惠券规则编码和名称构造枚举
     *
     * @param code    优惠券使用规则编码
     * @param typeName 优惠券使用规则名称
     */
    CouponAccumulateTypeEnum(Integer code, String typeName) {
        this.code = code;
        this.typeName = typeName != null ? typeName : "";
    }

    /**
     * 获取优惠券使用规则号码
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取优惠券使用规则名称
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
    public static CouponAccumulateTypeEnum getValueByCode(Integer code){
        for(CouponAccumulateTypeEnum couponAccumulateTypeEnum : CouponAccumulateTypeEnum.values()){
            if(couponAccumulateTypeEnum.getCode().equals(code)){
                return couponAccumulateTypeEnum;
            }
        }

        return null;
    }
}

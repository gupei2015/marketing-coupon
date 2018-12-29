package lk.project.marketing.base.enums;

/**
 * 促销规则类型
 */
public enum PromotionRuleTypeEnum {
    ISSUE(0, "发放规则"),
    USE(1, "使用规则");


    /**
     * 促销规则类型状态编码
     */
    private Integer code;

    /**
     * 促销规则类型名称
     */
    private String typeName;


    /**
     * 使用促销规则类型状态码和促销规则类型名称构造枚举
     *
     * @param code    促销规则类型状态编码
     * @param typeName 促销规则类型名称
     */
    PromotionRuleTypeEnum(Integer code, String typeName) {
        this.code = code;
        this.typeName = typeName != null ? typeName : "";
    }

    /**
     * 获取促销规则类型号
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取促销规则类型名称
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
    public static PromotionRuleTypeEnum getValueByCode(Integer code){
        for(PromotionRuleTypeEnum promotionRuleTypeEnum : PromotionRuleTypeEnum.values()){
            if(promotionRuleTypeEnum.getCode().equals(code)){
                return promotionRuleTypeEnum;
            }
        }

        return null;
    }
}

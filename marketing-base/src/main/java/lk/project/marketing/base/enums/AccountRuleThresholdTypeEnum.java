package lk.project.marketing.base.enums;

/**
 * 结算规则满减条件类型
 */
public enum AccountRuleThresholdTypeEnum {
    UNCONDITIONAL(0, "无条件"),
    AMOUNT(1, "满金额"),
    QUANTITY(2, "满数量"),
    PREFERENTIAL(3, "优惠价");

    /**
     * 满减条件类型状态编码
     */
    private Integer code;

    /**
     * 满减条件类型名称
     */
    private String typeName;


    /**
     * 使用满减条件类型状态码和满减条件类型名称构造枚举
     *
     * @param code    满减条件类型状态编码
     * @param typeName 满减条件类型名称
     */
    AccountRuleThresholdTypeEnum(Integer code, String typeName) {
        this.code = code;
        this.typeName = typeName != null ? typeName : "";
    }

    /**
     * 获取满减条件类型号
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取满减条件类型名称
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
    public static AccountRuleThresholdTypeEnum getValueByCode(Integer code){
        for(AccountRuleThresholdTypeEnum accountRuleThresholdTypeEnum : AccountRuleThresholdTypeEnum.values()){
            if(accountRuleThresholdTypeEnum.getCode().equals(code)){
                return accountRuleThresholdTypeEnum;
            }
        }

        return null;
    }
}

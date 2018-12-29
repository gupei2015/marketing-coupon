package lk.project.marketing.base.enums;

/**
 * 结算规则扣减类型
 */
public enum AccountRuleRewardTypeEnum {
    POINTS(0, "积分", 3),
    MONEY(1, "抵扣金额", 1),
    DISCOUNT(2, "折扣", 2),
    GIFT(3, "其他赠品", 4);

    /**
     * 扣减类型状态编码
     */
    private Integer code;

    /**
     * 扣减类型名称
     */
    private String typeName;

    /**
     * 扣减类型排序值(用于设置顺序扣减)
     */
    private Integer reduceSeqNo;


    /**
     * 使用扣减类型状态码和扣减类型名称构造枚举
     *
     * @param code    扣减类型状态编码
     * @param typeName 扣减类型名称
     */
    AccountRuleRewardTypeEnum(Integer code, String typeName, Integer reduceSeqNo) {
        this.code = code;
        this.typeName = typeName != null ? typeName : "";
        this.reduceSeqNo = reduceSeqNo;
    }

    /**
     * 获取扣减类型号
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取扣减类型名称
     *
     * @return String
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 获取扣减类型排序值(用于设置顺序扣减)
     * @return
     */
    public Integer getReduceSeqNo() {
        return reduceSeqNo;
    }

    /**
     * 根据Code获取枚举对象
     * @param code
     * @return
     */
    public static AccountRuleRewardTypeEnum getValueByCode(Integer code){
        for(AccountRuleRewardTypeEnum accountRuleRewardTypeEnum : AccountRuleRewardTypeEnum.values()){
            if(accountRuleRewardTypeEnum.getCode().equals(code)){
                return accountRuleRewardTypeEnum;
            }
        }

        return null;
    }
}

package lk.project.marketing.base.enums;

/**
 * 促销活动状态枚举
 */
public enum PromotionActivityStatusEnum {
    NOT_STARTED(0, "未开始"),
    STARTING(1, "进行中"),
    END(2, "已结束");

    /**
     * 促销活动状态编码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String statusName;


    /**
     * 使用促销活动状态码和促销活动状态名称构造枚举
     *
     * @param code    促销活动状态编码
     * @param statusName 促销活动状态名称
     */
    PromotionActivityStatusEnum(Integer code, String statusName) {
        this.code = code;
        this.statusName = statusName != null ? statusName : "";
    }

    /**
     * 获取促销活动类型号
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取促销活动类型名称
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
    public static PromotionActivityStatusEnum getValueByCode(Integer code){
        for(PromotionActivityStatusEnum promotionActivityStatusEnum : PromotionActivityStatusEnum.values()){
            if(promotionActivityStatusEnum.getCode().equals(code)){
                return promotionActivityStatusEnum;
            }
        }

        return null;
    }
}

package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryCouponReqDto extends PagerBaseRequestDto implements Serializable {

    /**
     * 优惠券模板ID
     */
    private Long id;

    /**
     * 公司,租户ID
     */
    private Integer companyId;

    /**
     * 所属类型, 0:积分;1:满减(包括现金抵扣和折扣,参考满减规则),2:赠送赠品
     */
    private Integer couponType;

    /**
     * 使用该优惠券产生的扣减是否计入满减条件的支付总量
     */
    protected Boolean accumulated;

    /**
     * 优惠券使用规则之间关系,0:排他;1:并存(累计);2:择优
     */
    private Integer accumulateType;

    /**
     * 状态，0已启用，1未启用
     */
    private Integer status;
}

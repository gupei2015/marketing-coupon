package lk.project.marketing.base.bo;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by gupei on 2018/9/18.
 * 优惠券模板保存接口对象
 */
@Data
public class CouponReqBo implements Serializable {

    /**
     * 优惠券模板ID
     */
    private Long id;

    /**
     * 结算规则ID
     */
    private Long accountRuleId;

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
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券活动描述
     */
    private String couponDesc;

    /**
     * 优惠券图标的URL地址
     */
    private String icon;

    /**
     * 优惠券使用规则之间关系,0:排他;1:并存(累计);2:择优
     */
    private Integer accumulateType;

    /**
     * 权重值,数值小表示优先
     */
    private Integer weight;

    /**
     * 退款时是否支持退回
     */
    private Boolean supportReturn;

    /**
     * 状态，0已启用，1未启用
     */
    private Integer status;
}

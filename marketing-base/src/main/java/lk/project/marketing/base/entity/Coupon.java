package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * Created by gupei on 2018/09/13.
 * 优惠券模板
 */
@TableName("t_coupon")
@Data
public class Coupon extends BaseEntity<Coupon> {

    /**
     * 结算规则ID
     */
    @TableField("account_rule_id")
    private Long accountRuleId;

    /**
     * 公司,租户ID
     */
    @TableField("company_id")
    private Integer companyId;

    /**
     * 所属类型, 0:积分;1:满减(包括现金抵扣和折扣,参考满减规则),2:赠送赠品
     */
    @TableField("coupon_type")
    private Integer couponType;

    /**
     * 使用该优惠券产生的扣减是否计入满减条件的支付总量
     */
    @TableField("is_accumulated")
    protected Boolean accumulated;

    /**
     * 优惠券名称
     */
    @TableField("coupon_name")
    private String couponName;

    /**
     * 优惠券活动描述
     */
    @TableField("coupon_desc")
    private String couponDesc;

    /**
     * 优惠券图标的URL地址
     */
    @TableField("icon")
    private String icon;

    /**
     * 适用商户供应商ID
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 对象范围,适用商品或服务分类ID
     */
    @TableField("sku_category")
    private Integer skuCategory;

    /**
     * 顾客资格,适用用户分类
     */
    @TableField("user_category")
    private Integer userCategory;

    /**
     * 商品或服务SKU id
     */
    @TableField("sku_id")
    private Integer skuId;

    /**
     * 优惠券使用规则之间关系,0:排他;1:并存(累计);2:择优
     */
    @TableField("accumulate_type")
    private Integer accumulateType;

    /**
     * 权重值,数值小表示优先
     */
    @TableField("weight")
    private Integer weight;

    /**
     * 退款时是否支持退回
     */
    @TableField("is_supportReturn")
    private Boolean supportReturn;

    /**
     * 状态，0已启用，1未启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 优惠券结算规则
     */
    @TableField(exist = false)
    private AccountRule accountRule;

    /**
     * 优惠券关联的有效活动
     */
    @TableField(exist = false)
    private List<PromotionActivity> activityPromotionList;
}

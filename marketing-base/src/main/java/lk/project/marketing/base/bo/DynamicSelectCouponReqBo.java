package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 动态计算实际可用劵请求类
 * Created by alexlu on 2018/11/22.
 */
@Data
public class DynamicSelectCouponReqBo implements Serializable {
    /**
     * 动态计算用户选中的优惠券列表
     * 返回计算后的实际可用劵列表
     */
    List<DynamicCalcSelectedCouponBo> dynamicCalcSelectedCouponList;

    /**
     * 当前最新的总金额
     */
    private BigDecimal totalAmount;

    /**
     * 参与满减条件计算的虚拟总金额
     */
    private BigDecimal virtualCalcTotalAmount;

    /**
     * 用户信息
     */
    private MemberBo memberBo;

    /**
     * 订单信息
     */
    private OrderBo orderBo;

    /**
     * 订单项信息
     */
    private OrderItemBo orderItemBo;
}

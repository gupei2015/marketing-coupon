package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 动态计算实际可用劵返回类
 * Created by alexlu on 2018/11/22.
 */
@Data
public class DynamicSelectCouponResultRespBo extends CouponReduceInfoBo implements Serializable {
    /**
     * 动态返回每次操作计算后的实际可用劵
     *
     */
    List<DynamicCalcSelectedCouponBo> dynamicCalcSelectedCouponList;

    /**
     * 当前计算的(订单或单个商品)最终实付总金额
     */
    private BigDecimal finalTotalAmount;

    /**
     * 参与满减条件计算的虚拟总金额
     */
    private BigDecimal virtualCalcTotalAmount;
}

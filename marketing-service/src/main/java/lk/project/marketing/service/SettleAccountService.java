package lk.project.marketing.service;

import lk.project.marketing.base.bo.*;

/**
 * Created by gupei on 2018/9/18.
 * 订单结算接口
 */
public interface SettleAccountService {

    /**
     * 订单结算
     * @param memberReqBo 用户信息
     * @param order 订单和商品信息(包含使用的优惠券)
     * @return 订单结算结果对象
     */
    OrderSettleResultBo orderSettlement(MemberBo memberReqBo, OrderBo order) throws Exception;

    /**
     * 动态返回用户选中的优惠券数据及其金额
     * @param dynamicSelectCouponReqBo
     * @return
     */
    DynamicSelectCouponResultRespBo selectCoupon(DynamicSelectCouponReqBo dynamicSelectCouponReqBo) throws Exception;
}

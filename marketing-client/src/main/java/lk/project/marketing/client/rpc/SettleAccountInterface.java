package lk.project.marketing.client.rpc;

import lk.project.marketing.client.dto.DynamicSelectCouponReqDto;
import lk.project.marketing.client.dto.OrderSettlementReqDto;
import lk.project.marketing.client.vo.ResponseVO;

/**
 * Created by alexlu on 2018/11/23.
 */
public interface SettleAccountInterface {
    /**
     * 订单结算
     * @param orderSettlementReqDto
     * @return 订单结算结果对象
     */
    ResponseVO orderSettlement(OrderSettlementReqDto orderSettlementReqDto) throws Exception;

    /**
     * 动态返回用户选中的优惠券数据及其金额
     * @param dynamicSelectCouponReqDto
     * @return
     */
    ResponseVO selectCoupon(DynamicSelectCouponReqDto dynamicSelectCouponReqDto) throws Exception;
}

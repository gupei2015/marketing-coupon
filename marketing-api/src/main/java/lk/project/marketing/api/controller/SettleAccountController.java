package lk.project.marketing.api.controller;

import lk.project.marketing.api.common.BaseController;
import lk.project.marketing.client.dto.DynamicSelectCouponReqDto;
import lk.project.marketing.client.dto.OrderSettlementReqDto;
import lk.project.marketing.client.rpc.SettleAccountInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alexlu on 2018/11/23.
 */

@RestController
@RequestMapping("/couponSettle")
public class SettleAccountController extends BaseController{
    @Autowired
    SettleAccountInterface settleAccountInterface;

    /**
     * 订单结算
     * @param orderSettlementReqDto
     * @return 订单结算结果对象
     */
    @PostMapping("/order/settlement")
    public ResponseVO orderSettlement(@RequestBody OrderSettlementReqDto orderSettlementReqDto) throws Exception{
        return settleAccountInterface.orderSettlement(orderSettlementReqDto);
    }

    /**
     * 动态返回用户选中的优惠券数据及其金额
     * @param dynamicSelectCouponReqDto
     * @return
     */
    @PostMapping("/dynamicSelect")
    public ResponseVO selectCoupon(@RequestBody DynamicSelectCouponReqDto dynamicSelectCouponReqDto) throws Exception{
        return settleAccountInterface.selectCoupon(dynamicSelectCouponReqDto);
    }
}

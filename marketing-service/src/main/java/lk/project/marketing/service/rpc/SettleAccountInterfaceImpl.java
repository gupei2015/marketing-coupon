package lk.project.marketing.service.rpc;

import lk.project.marketing.base.bo.*;
import lk.project.marketing.client.dto.DynamicSelectCouponReqDto;
import lk.project.marketing.client.dto.OrderSettlementReqDto;
import lk.project.marketing.client.rpc.SettleAccountInterface;
import lk.project.marketing.client.utils.JsonUtil;
import lk.project.marketing.client.vo.ResponseVO;
import lk.project.marketing.service.rpc.pojo.BaseResponse;
import lk.project.marketing.service.SettleAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alexlu on 2018/11/23.
 */

public class SettleAccountInterfaceImpl extends BaseResponse implements SettleAccountInterface {
    @Autowired
    SettleAccountService settleAccountService;

    /**
     * 订单结算
     *
     * @param orderSettlementReqDto
     * @return 订单结算结果对象
     */
    @Override
    public ResponseVO orderSettlement(OrderSettlementReqDto orderSettlementReqDto) throws Exception {
        MemberBo memberBo = new MemberBo();
        BeanUtils.copyProperties(orderSettlementReqDto.getMemberDto(), memberBo);

        OrderBo orderBo = new OrderBo();
        BeanUtils.copyProperties(orderSettlementReqDto.getOrderDto(), orderBo);

        if (!orderSettlementReqDto.getOrderDto().getUseCoupons().isEmpty()) {
            orderBo.setUseCoupons(orderSettlementReqDto.getOrderDto().getUseCoupons().stream()
                    .map(s -> JsonUtil.formatObject(s, UseCouponBo.class)).collect(Collectors.toList()));
        }

        if (!orderSettlementReqDto.getOrderDto().getOrderItemList().isEmpty()) {
            List<OrderItemBo> orderItemBoList = new ArrayList();
            orderSettlementReqDto.getOrderDto().getOrderItemList().forEach(c -> {
                OrderItemBo orderItemBo = new OrderItemBo();
                BeanUtils.copyProperties(c, orderItemBo);
                List<UseCouponBo> itemUseCouponBoList = c.getUseCoupons().stream()
                        .map(s -> JsonUtil.formatObject(s, UseCouponBo.class)).collect(Collectors.toList());
                orderItemBo.setUseCoupons(itemUseCouponBoList);
                orderItemBoList.add(orderItemBo);
            });
            orderBo.setOrderItemList(orderItemBoList);
        }

        return getFromData(settleAccountService.orderSettlement(memberBo, orderBo));
    }

    /**
     * 动态返回用户选中的优惠券数据及其金额
     *
     * @param dynamicSelectCouponReqDto
     * @return
     */
    @Override
    public ResponseVO selectCoupon(DynamicSelectCouponReqDto dynamicSelectCouponReqDto) throws Exception {
        List<DynamicCalcSelectedCouponBo> dynamicCalcSelectedCouponList = dynamicSelectCouponReqDto.getDynamicCalcSelectedCouponList().stream()
                .map(s -> JsonUtil.formatObject(s, DynamicCalcSelectedCouponBo.class)).collect(Collectors.toList());
        DynamicSelectCouponReqBo dynamicSelectCouponReqBo = new DynamicSelectCouponReqBo();
        BeanUtils.copyProperties(dynamicSelectCouponReqDto, dynamicSelectCouponReqBo);

        dynamicSelectCouponReqBo.setDynamicCalcSelectedCouponList(dynamicCalcSelectedCouponList);

        if (dynamicSelectCouponReqDto.getMemberDto() != null) {
            MemberBo memberBo = new MemberBo();
            BeanUtils.copyProperties(dynamicSelectCouponReqDto.getMemberDto(), memberBo);
            dynamicSelectCouponReqBo.setMemberBo(memberBo);
        }

        if (dynamicSelectCouponReqDto.getOrderDto() != null) {
            OrderBo orderBo = new OrderBo();
            BeanUtils.copyProperties(dynamicSelectCouponReqDto.getOrderDto(), orderBo);
            dynamicSelectCouponReqBo.setOrderBo(orderBo);
        }

        if (dynamicSelectCouponReqDto.getOrderItemDto() != null) {
            OrderItemBo orderItemBo = new OrderItemBo();
            BeanUtils.copyProperties(dynamicSelectCouponReqDto.getOrderItemDto(), orderItemBo);
            dynamicSelectCouponReqBo.setOrderItemBo(orderItemBo);
        }

        return getFromData(settleAccountService.selectCoupon(dynamicSelectCouponReqBo));
    }
}

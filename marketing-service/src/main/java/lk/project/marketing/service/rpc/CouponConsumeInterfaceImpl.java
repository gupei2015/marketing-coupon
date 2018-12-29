package lk.project.marketing.service.rpc;

import lk.project.marketing.base.bo.*;
import lk.project.marketing.client.dto.*;
import lk.project.marketing.client.rpc.CouponConsumeInterface;
import lk.project.marketing.client.utils.JsonUtil;
import lk.project.marketing.client.vo.ResponseVO;
import lk.project.marketing.service.rpc.pojo.BaseResponse;
import lk.project.marketing.service.CouponConsumeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CouponConsumeInterfaceImpl extends BaseResponse implements CouponConsumeInterface {

    @Autowired
    CouponConsumeService couponConsumeService;

    /**
     * 查询订单关联的优惠券使用记录
     * @param queryOrderConsumedCouponReqDto
     * @return
     */
    @Override
    public ResponseVO queryOrderConsumedCoupon(QueryOrderConsumedCouponReqDto queryOrderConsumedCouponReqDto){
        return getFromData(couponConsumeService.queryOrderConsumedCoupon(queryOrderConsumedCouponReqDto.getOrderId()));
    }

    /**
     * 查询会员购买单项商品/服务可使用的优惠券
     * @param queryItemMatchedCouponReqDto
     * @return
     */
    @Override
    public ResponseVO queryMatchedCouponForPurchaseItem(QueryItemMatchedCouponReqDto queryItemMatchedCouponReqDto){
        MemberBo memberBo = new MemberBo();
        BeanUtils.copyProperties(queryItemMatchedCouponReqDto.getMemberDto(),memberBo);

        OrderItemBo orderItemBo = new OrderItemBo();
        BeanUtils.copyProperties(queryItemMatchedCouponReqDto.getOrderItemDto(),orderItemBo);
        return getFromData(couponConsumeService.queryMatchedCouponForPurchaseItem(memberBo, orderItemBo));
    }

    /**
     * 查询会员/用户下单可使用的优惠券
     * @param queryOrderMatchedCouponDto
     * @return
     */
    @Override
    public ResponseVO queryMatchedCouponForOrder(QueryOrderMatchedCouponDto queryOrderMatchedCouponDto){
        MemberBo memberBo = new MemberBo();
        BeanUtils.copyProperties(queryOrderMatchedCouponDto.getMemberDto(), memberBo);

        OrderBo orderBo = new OrderBo();
        BeanUtils.copyProperties(queryOrderMatchedCouponDto.getOrderDto(), orderBo);
        return getFromData(couponConsumeService.queryMatchedCouponForOrder(memberBo,orderBo));
    }

    /**
     * 将可用优惠券明细列表按优先使用策略排序
     * @param sortAvailableMatchedCouponsReqDto
     * @return
     */
    @Override
    public ResponseVO sortAvailableMatchedCoupons(SortAvailableMatchedCouponsReqDto sortAvailableMatchedCouponsReqDto){
        List<AvailableCouponDetailsBo> availableCouponDetailsBoList = sortAvailableMatchedCouponsReqDto.getAvailableCouponDetailsDtoList().stream()
                .map(m -> JsonUtil.formatObject(m, AvailableCouponDetailsBo.class)).collect(Collectors.toList());

        return getFromData(couponConsumeService.sortAvailableMatchedCoupons(availableCouponDetailsBoList));
    }

    /**
     * 校验用券信息,每一种优惠券按使用策略顺序更新领券记录状态,并更新用户中心优惠券信息
     * @param consumeCouponReqDto
     * @return
     */
    @Override
    public ResponseVO consumeCoupon(ConsumeCouponReqDto consumeCouponReqDto) throws Exception{
        MemberBo memberBo = new MemberBo();
        BeanUtils.copyProperties(consumeCouponReqDto.getMemberDto(), memberBo);

        OrderBo orderBo = new OrderBo();
        BeanUtils.copyProperties(consumeCouponReqDto.getOrderDto(), orderBo);
        List<OrderItemBo> orderItemBoList = consumeCouponReqDto.getOrderDto().getOrderItemList().stream()
                .map(m -> JsonUtil.formatObject(m, OrderItemBo.class)).collect(Collectors.toList());
        List<UseCouponBo> useCouponBoList = consumeCouponReqDto.getOrderDto().getUseCoupons().stream()
                .map(m -> JsonUtil.formatObject(m, UseCouponBo.class)).collect(Collectors.toList());
        orderBo.setOrderItemList(orderItemBoList);
        orderBo.setUseCoupons(useCouponBoList);

        return getFromData(couponConsumeService.consumeCoupon(memberBo,orderBo));
    }
}

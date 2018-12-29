package lk.project.marketing.service;

import lk.project.marketing.base.bo.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by gupei on 2018/9/18.
 * 优惠券使用接口
 */
public interface CouponConsumeService {

    /**
     * 查询订单关联的优惠券使用记录
     * @param orderId 订单Id
     * @return 已使用的优惠券
     */
    CouponConsumeHistoryRespBo queryOrderConsumedCoupon(String orderId);

    /**
     * 查询会员购买单项商品/服务可使用的优惠券
     * @param memberReqBo   会员信息
     * @param orderItem 订单明细项
     * @return 查询订单明细项(商品)可用优惠券结果
     */
    OrderItemAvailableCouponsRespBo queryMatchedCouponForPurchaseItem(MemberBo memberReqBo, OrderItemBo orderItem);

    /**
     * 查询会员下单可使用的优惠券
     * @param memberReqBo   会员信息
     * @param orderReqBo 订单信息
     * @returns 查询订单可用优惠券结果
     */
    OrderAvailableCouponsRespBo queryMatchedCouponForOrder(MemberBo memberReqBo, OrderBo orderReqBo);

    /**
     * 查询订单或商品(各活动)可用优惠券结果集合
     *
     * @param memberReqBo 会员信息
     * @param orderReqBo 订单信息
     * @param orderItemReqBo 订单项信息
     * @return
     */
    List<SelectCouponReceiveInfoBo> getSelectCouponReceiveInfoList(MemberBo memberReqBo,
                                                                   OrderBo orderReqBo,
                                                                   OrderItemBo orderItemReqBo);

    /**
     * 获取订单或商品(各活动)优惠券总剩余数量
     *
     * @param memberReqBo 会员信息
     * @param orderReqBo 订单信息
     * @param orderItemReqBo 订单项信息
     * @param couponId 当前查询的优惠券ID
     * @return
     */
    Long getTotalCouponValidRemainQuantity(MemberBo memberReqBo, OrderBo orderReqBo,
                                           OrderItemBo orderItemReqBo,final Long couponId);

    /**
     * 将用户优惠券可选列表排序返回
     * @param orderReqBo 订单信息
     * @param orderItemReqBo 订单明细项信息
     * @param memberReqBo    用户信息
     * @param selectCouponReceiveInfoList 可选的用户优惠券领劵记录列表
     * @return 策略排序后的用户优惠券可选列表
     */
    List<SelectCouponUseBo> getSelectCouponUseList(OrderBo orderReqBo,
                                                   OrderItemBo orderItemReqBo,
                                                   MemberBo memberReqBo,
                                                   List<SelectCouponReceiveInfoBo> selectCouponReceiveInfoList);

    /**
     * 将可用的优惠券按优先使用策略排序
     * @param availableCouponList 可用的用户优惠券列表
     * @return 待使用优惠券明细信息列表
     */
    List<AvailableCouponDetailsBo> sortAvailableMatchedCoupons(List<AvailableCouponDetailsBo> availableCouponList);

    /**
     * 用券相关操作
     * 校验用券信息,每一种优惠券按使用策略顺序更新领券记录状态,并更新用户中心优惠券信息
     * @param memberReqBo 用户信息对象
     * @param orderReqBo 包含使用优惠券的订单信息对象
     * @return 实际使用的优惠券
     */
    List<CouponConsumeBo> consumeCoupon(MemberBo memberReqBo,OrderBo orderReqBo) throws Exception;

    /**
     * 校验当前用户下单和商品/服务优惠券是否可用
     *
     * @param memberReqBo 用户信息对象
     * @param orderReqBo 包含使用优惠券的订单信息对象
     * @param isSettleCheck 是否结算校验
     * @param couponReduceAmountMap 订单或商品的各类优惠券扣减数据集合
     * @return
     */
    Boolean validateCouponAvailable(MemberBo memberReqBo, OrderBo orderReqBo,
                                    Boolean isSettleCheck,
                                    Map<CouponReduceInfoBo, Map<Long, BigDecimal>> couponReduceAmountMap) throws Exception;
}

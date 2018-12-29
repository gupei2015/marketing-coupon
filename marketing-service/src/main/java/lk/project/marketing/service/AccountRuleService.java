package lk.project.marketing.service;


import lk.project.marketing.base.bo.*;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.base.enums.AccountRuleRewardTypeEnum;
import lk.project.marketing.base.enums.AccountRuleThresholdTypeEnum;
import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.base.utils.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.Map;

public interface AccountRuleService {

    /**
     * 计算使用单张优惠券结算的优惠结果
     * @param coupon
     * @param totalAmount
     * @return
     * @throws BusinessException
     */
    static CouponsReduceAmountBo calcCouponReward(Coupon coupon, BigDecimal totalAmount, BigDecimal goodsSalePrice)
            throws BusinessException{

        if (coupon==null) throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO);
        AccountRule accountRule = coupon.getAccountRule();
        if (accountRule==null) throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE);

        CouponsReduceAmountBo couponsRewardResult = new CouponsReduceAmountBo();
        couponsRewardResult.setCouponId(coupon.getId());
        couponsRewardResult.setRewardDesc(accountRule.getRewardDesc());

        if (totalAmount==null||totalAmount.equals(BigDecimal.ZERO)) return couponsRewardResult;

        switch(AccountRuleRewardTypeEnum.getValueByCode(accountRule.getRewardType())){
            case MONEY:
                if(accountRule.getThresholdType().equals(AccountRuleThresholdTypeEnum.PREFERENTIAL.getCode())) {
                    if (accountRule.getPromotionPrice().compareTo(BigDecimal.ZERO) == 0) {
                        throw new BusinessException(BusinessErrorCodeEnum.EMPTY_USER_SELECTED_COUPONS_PROMOTION_PRICE_EXCEPTION);
                    }
                    if (accountRule.getPromotionPrice().compareTo(goodsSalePrice)!=-1){
                        throw new BusinessException(BusinessErrorCodeEnum.NO_REDUCE_AMOUNT);
                    }
                    couponsRewardResult.setPaymentAmount(accountRule.getPromotionPrice());
                    couponsRewardResult.setCouponReduceAmount(goodsSalePrice.subtract(accountRule.getPromotionPrice()));
                }
                else {
                    if (accountRule.getRewardAmount().compareTo(totalAmount) == -1) {
                        couponsRewardResult.setCouponReduceAmount(accountRule.getRewardAmount());
                        couponsRewardResult.setPaymentAmount(totalAmount.subtract(accountRule.getRewardAmount()));
                    } else {
                        couponsRewardResult.setCouponReduceAmount(totalAmount);
                        couponsRewardResult.setPaymentAmount(BigDecimal.ZERO);
                    }
                }

                break;
            case DISCOUNT:
                BigDecimal afterDiscountAmt = BigDecimalUtil.multiply(totalAmount.doubleValue(),
                                                                        accountRule.getRewardAmount().doubleValue());
                BigDecimal discountAmt = BigDecimalUtil.subtract(totalAmount.doubleValue(),
                                                                    afterDiscountAmt.doubleValue());
                couponsRewardResult.setCouponReduceAmount(discountAmt);
                couponsRewardResult.setPaymentAmount(afterDiscountAmt);
                break;
            case POINTS:
                BigDecimal reduceAmt;
                try {
                    reduceAmt = BigDecimalUtil.divide(1L,
                            accountRule.getExchangeRatio().doubleValue(),2);
                } catch (IllegalAccessException e) {
                    throw new BusinessException(BusinessErrorCodeEnum.INVALID_POINT_RATIO);
                }
                if (reduceAmt.compareTo(totalAmount) == -1) {
                    couponsRewardResult.setCouponReduceAmount(reduceAmt);
                    couponsRewardResult.setPaymentAmount(totalAmount.subtract(reduceAmt));
                } else {
                    couponsRewardResult.setCouponReduceAmount(totalAmount);
                    couponsRewardResult.setPaymentAmount(BigDecimal.ZERO);
                }
                break;
            case GIFT:
                couponsRewardResult.setPaymentAmount(totalAmount);
                couponsRewardResult.setCouponReduceAmount(BigDecimal.ZERO);
                couponsRewardResult.setRewardCouponActivityId(accountRule.getRewardActivityId());
                couponsRewardResult.setRewardProductId(accountRule.getRewardProduct());
                break;
        }

        return couponsRewardResult;
    }

    /**
     * 根据优惠券校验满减结算规则(金额/数量)是否匹配
     * 满减金额/数量是否匹配
     *
     * @param couponId 优惠券ID
     * @param couponQuantity 优惠券当前使用数量
     * @param originPayloadValue 结算规则阈值金额/数量
     * @return 是否单项优惠券结算规则校验通过
     */
    Boolean validateAccountRuleMatch(Long couponId, Long couponQuantity, BigDecimal originPayloadValue);

    /**
     * 校验匹配订单或商品层次优惠券满减结算规则(金额和数量)
     * @param orderReqBo 订单信息
     * @param orderItemReqBo 订单项/商品信息
     * @param usingCouponsMap 使用的优惠券信息集合
     * @param isSettleCheck 是否结算校验
     * @param couponReduceAmountMap 订单或商品的各类优惠券扣减数据集合
     * @return 是否校验均通过
     */
    Boolean checkAccountRuleMatchResult(OrderBo orderReqBo, OrderItemBo orderItemReqBo,
                                        Map<Long, Long> usingCouponsMap, Boolean isSettleCheck,
                                        Map<CouponReduceInfoBo, Map<Long, BigDecimal>> couponReduceAmountMap) throws Exception;

    /**
     * 校验每种优惠券是否匹配结算递减的满减条件
     * 规则:按照Coupon.IsAccumulated字段判断是否在下一个劵种判断递减金额(劵额*当前用劵数量)
     * @param couponId 优惠券ID
     * @param couponQuantity 优惠券数量
     * @param goodsSalePrice 商品/服务销售原单价
     * @param tmpTotalAmount 临时变量(当前商品或订单的总金额)
     * @param finalActualResultTotalAmount 最后计算返回的总金额
     * @param reduceAmountSubMap 订单或商品的各类优惠券扣减数据
     * @return 是否匹配每种优惠券是否匹配结算递减的条件
     */
    CalcCouponReduceResultBo calcCouponReduceResultBySequence(Long couponId,
                                                              Long couponQuantity,
                                                              BigDecimal goodsSalePrice,
                                                              BigDecimal tmpTotalAmount,
                                                              BigDecimal finalActualResultTotalAmount,
                                                              Map<Long, BigDecimal> reduceAmountSubMap) throws Exception;

    /**
     * 计算订单或商品的每种优惠券本次总共应扣减的金额
     * @param eachCouponReqBo 计算订单或商品的每种优惠券本次应扣结算规则信息
     * @return
     */
    BigDecimal calcTotalReduceAmountByEachCoupon(CalcTotalReduceByEachCouponReqBo eachCouponReqBo) throws Exception;

    /**
     * 根据结算规则和当前额度计算该劵种实际可用数量
     * @param couponId 优惠券ID
     * @param curCouponUserRemainQuantity 用户当前劵种剩余劵数
     * @param validateTotalAmount 当前总金额
     * @param validateTotalQuantity 当前总数量
     * @return
     */
    @Deprecated
    Long getCouponAvailableQuantityByRule(Long couponId, Long curCouponUserRemainQuantity,
                                          BigDecimal validateTotalAmount, Long validateTotalQuantity);
}

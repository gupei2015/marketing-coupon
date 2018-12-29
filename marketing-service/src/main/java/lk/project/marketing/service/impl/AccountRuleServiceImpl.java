package lk.project.marketing.service.impl;

import lk.project.marketing.base.bo.*;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.enums.AccountRuleRewardTypeEnum;
import lk.project.marketing.base.enums.AccountRuleThresholdTypeEnum;
import lk.project.marketing.base.enums.CouponTypeEnum;
import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.repository.AccountRuleRepository;
import lk.project.marketing.repository.CouponRepository;
import lk.project.marketing.service.AccountRuleService;
import lk.project.marketing.base.utils.BigDecimalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算规则服务实现类
 * Created by zhanghongda on 2018/10/30.
 */
@Service
public class AccountRuleServiceImpl implements AccountRuleService {
    private final static Logger log = LoggerFactory.getLogger(AccountRuleServiceImpl.class);

    @Autowired
    AccountRuleRepository accountRuleRepository;

    @Autowired
    CouponRepository couponRepository;

    /**
     * 根据优惠券校验满减结算规则
     * 满减金额/数量是否匹配
     *
     * @param couponId           优惠券ID
     * @param couponQuantity     优惠券当前使用数量
     * @param originPayloadValue 当前源总金额/总数量
     * @return 是否单项优惠券结算规则校验通过
     */
    @Override
    public Boolean validateAccountRuleMatch(Long couponId,
                                            Long couponQuantity,
                                            BigDecimal originPayloadValue) {
        Coupon couponInfo = couponRepository.getCouponById(couponId);
        if (couponInfo == null) {
            throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO);
        }

        AccountRule accountRuleInfo = couponInfo.getAccountRule();
        if (accountRuleInfo == null) {
            throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE);
        }

        log.info("开始校验优惠券满减结算规则(检查满减金额/数量是否匹配):优惠券ID:{},{}-阈值:{}", couponId,
                AccountRuleThresholdTypeEnum.getValueByCode(accountRuleInfo.getThresholdType()).getTypeName(),
                accountRuleInfo.getRewardThreshold().toString());

        //开始匹配结算金额或数量
        couponQuantity = (couponQuantity == null || couponQuantity.equals(0L)) ? 1L : couponQuantity;
        if (couponInfo.getCouponType().equals(CouponTypeEnum.DISCOUNT.getCode())) {
            String errTipMsg;
            if (accountRuleInfo.getThresholdType().equals(AccountRuleThresholdTypeEnum.AMOUNT.getCode())) {
                //若金额小于结算规则阈值金额
                if (originPayloadValue.compareTo(accountRuleInfo.getRewardThreshold()
                        .multiply(BigDecimal.valueOf(couponQuantity))) == -1) {
                    errTipMsg = String.format(BusinessErrorCodeEnum.COUPON_ACCOUNT_RULE_MATCH_THRESHOLD_AMOUNT_FAILED.getMessage(),
                            originPayloadValue.toString(), accountRuleInfo.getRewardThreshold().toString());
                    throw new BusinessException(BusinessErrorCodeEnum.COUPON_ACCOUNT_RULE_MATCH_THRESHOLD_AMOUNT_FAILED.getCode(), errTipMsg);
                }
            } else if (accountRuleInfo.getThresholdType().equals(AccountRuleThresholdTypeEnum.QUANTITY.getCode())) {
                //若数量小于结算规则阈值数量
                if (originPayloadValue.compareTo(accountRuleInfo.getRewardThreshold()
                        .multiply(BigDecimal.valueOf(couponQuantity))) == -1) {
                    errTipMsg = String.format(BusinessErrorCodeEnum.COUPON_ACCOUNT_RULE_MATCH_THRESHOLD_QUANTITY_FAILED.getMessage(),
                            originPayloadValue.toString(), accountRuleInfo.getRewardThreshold().toString());
                    throw new BusinessException(BusinessErrorCodeEnum.COUPON_ACCOUNT_RULE_MATCH_THRESHOLD_QUANTITY_FAILED.getCode(), errTipMsg);
                }
            }
        }

        return true;
    }

    /**
     * 校验匹配订单或商品层次优惠券满减结算规则(金额和数量)
     *
     * @param orderReqBo            订单信息
     * @param orderItemReqBo        订单项/商品信息
     * @param usingCouponsMap       使用的优惠券信息集合[CouponId,CouponUsingQuantity]
     * @param isSettleCheck         是否结算校验
     * @param couponReduceAmountMap 订单或商品的各类优惠券扣减数据集合
     * @return 是否校验均通过
     */
    @Override
    public Boolean checkAccountRuleMatchResult(OrderBo orderReqBo,
                                               OrderItemBo orderItemReqBo,
                                               Map<Long, Long> usingCouponsMap,
                                               Boolean isSettleCheck,
                                               Map<CouponReduceInfoBo, Map<Long, BigDecimal>> couponReduceAmountMap) throws Exception {
        if (couponReduceAmountMap == null) {
            couponReduceAmountMap = new HashMap(16);
        }

        CouponReduceInfoBo couponReduceInfoBo = new CouponReduceInfoBo();
        BigDecimal tmpTotalAmount = BigDecimal.ZERO;
        Map<Long, BigDecimal> reduceAmountSubMap = new HashMap(16);

        for (Map.Entry<Long, Long> usingCouponInfo : usingCouponsMap.entrySet()) {
            try {
                if (orderItemReqBo != null) {
                    if(tmpTotalAmount.compareTo(BigDecimal.ZERO) == 0) {
                        tmpTotalAmount = orderItemReqBo.getTotalGoodsAmount();
                    }

                    couponReduceInfoBo.setCouponReduceTargetFlag(CommonConstants.COUPON_REDUCE_TARGET_TYPE_ORDER_ITEM);
                    BeanUtils.copyProperties(orderItemReqBo, couponReduceInfoBo);

                    if (orderItemReqBo.getTotalGoodsAmount() != null
                            && orderItemReqBo.getTotalGoodsAmount().compareTo(BigDecimal.ZERO) != 0) {
                        //一般情况下,校验单个订单项总金额是否匹配结算条件
                        validateAccountRuleMatch(usingCouponInfo.getKey(), usingCouponInfo.getValue(),
                                orderItemReqBo.getTotalGoodsAmount());
                    } else if (orderItemReqBo.getGoodsQuantity() != null && orderItemReqBo.getGoodsQuantity() > 0L) {
                        //一般情况下,校验单个订单项数量是否匹配结算条件
                        validateAccountRuleMatch(usingCouponInfo.getKey(), usingCouponInfo.getValue(),
                                BigDecimal.valueOf(orderItemReqBo.getGoodsQuantity()));
                    }
                } else if (orderReqBo != null) {
                    if(tmpTotalAmount.compareTo(BigDecimal.ZERO) == 0) {
                        tmpTotalAmount = orderReqBo.getTotalOrderAmount();
                    }

                    if (orderReqBo.getTotalOrderAmount() != null
                            && orderReqBo.getTotalOrderAmount().compareTo(BigDecimal.ZERO) != 0) {
                        couponReduceInfoBo.setCouponReduceTargetFlag(CommonConstants.COUPON_REDUCE_TARGET_TYPE_ORDER);
                        BeanUtils.copyProperties(orderReqBo, couponReduceInfoBo);

                        //一般情况下,校验单个订单的总金额是否匹配结算条件
                        validateAccountRuleMatch(usingCouponInfo.getKey(), usingCouponInfo.getValue(), orderReqBo.getTotalOrderAmount());
                    }
                }

                //若是结算操作,则按照Coupon.IsAccumulated字段判断是否在下一个劵种判断递减金额(劵额*当前用劵数量)
                if (isSettleCheck) {
                    BigDecimal finalActualResultTotalAmount = BigDecimal.ZERO;
                    CalcCouponReduceResultBo calcCouponReduceResultBo = calcCouponReduceResultBySequence(
                            usingCouponInfo.getKey(), usingCouponInfo.getValue(),
                            orderItemReqBo != null ? orderItemReqBo.getGoodsSalePrice() : BigDecimal.ZERO,
                            tmpTotalAmount, finalActualResultTotalAmount, reduceAmountSubMap);
                    if(!calcCouponReduceResultBo.getCalcCouponReduceSucceed()){
                        throw new BusinessException(BusinessErrorCodeEnum.USER_SELECTED_COUPONS_ACCOUNT_RULE_MATCH_FAILED);
                    }

                    //返回当前计算后的总金额
                    tmpTotalAmount = calcCouponReduceResultBo.getCalcResultTotalAmount();
                }
            } catch (BusinessException e) {
                log.error(BusinessErrorCodeEnum.CHECK_COUPON_ACCOUNT_RULES_MATCH_FAILED.getMessage(), e);
                return false;
            }
        }

        couponReduceAmountMap.put(couponReduceInfoBo, reduceAmountSubMap);

        return true;
    }

    /**
     * 校验每种优惠券是否匹配结算递减的满减条件
     * 规则:按照Coupon.IsAccumulated字段判断是否在下一个劵种判断递减金额(劵额*当前用劵数量)
     *
     * @param couponId           优惠券ID
     * @param couponQuantity     优惠券数量
     * @param tmpTotalAmount     临时变量(当前商品或订单的总金额)
     * @param finalActualResultTotalAmount 最后计算返回的总金额
     * @param reduceAmountSubMap 订单或商品的各类优惠券扣减数据
     * @return 是否匹配每种优惠券是否匹配结算递减的条件
     */
    @Override
    public CalcCouponReduceResultBo calcCouponReduceResultBySequence(Long couponId,
                                                                     Long couponQuantity,
                                                                     BigDecimal goodsSalePrice,
                                                                     BigDecimal tmpTotalAmount,
                                                                     BigDecimal finalActualResultTotalAmount,
                                                                     Map<Long, BigDecimal> reduceAmountSubMap) throws Exception {
        Coupon couponInfo = couponRepository.getCouponById(couponId);
        if (couponInfo == null) {
            throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO);
        }

        AccountRule accountRuleInfo = couponInfo.getAccountRule();
        if (accountRuleInfo == null) {
            throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE);
        }

        CalcCouponReduceResultBo calcCouponReduceResultBo = new CalcCouponReduceResultBo();
        CalcTotalReduceByEachCouponReqBo eachCouponReqBo = new CalcTotalReduceByEachCouponReqBo();

        //执行校验并返回订单或商品的每种优惠券扣减数据集合
        if (validateAccountRuleMatch(couponId, couponQuantity, tmpTotalAmount)) {
            eachCouponReqBo.setAccountRuleInfo(accountRuleInfo);
            eachCouponReqBo.setCouponQuantity(couponQuantity);
            eachCouponReqBo.setGoodsSalePrice(goodsSalePrice);
            eachCouponReqBo.setTmpTotalAmount(tmpTotalAmount);

            //计算获取订单或商品的每种优惠券本次总共应扣减的金额
            BigDecimal needReduceAmt = calcTotalReduceAmountByEachCoupon(eachCouponReqBo);

            if (reduceAmountSubMap.get(couponId) != null) {
                BigDecimal curReducedAmount = reduceAmountSubMap.get(couponId).add(needReduceAmt);
                reduceAmountSubMap.put(couponId, curReducedAmount);
            } else {
                reduceAmountSubMap.put(couponId, needReduceAmt);
            }

            //统计可累计扣减的优惠券及其需扣减的各项金额
            if (!couponInfo.getAccumulated()) {
                eachCouponReqBo.setTmpTotalAmount(BigDecimalUtil.subtract(eachCouponReqBo.getTmpTotalAmount().doubleValue(),
                                                    needReduceAmt.doubleValue()));
                calcCouponReduceResultBo.setCalcResultTotalAmount(BigDecimalUtil.round(
                        BigDecimalUtil.subtract(tmpTotalAmount.doubleValue(),
                                needReduceAmt.doubleValue()).doubleValue(), CommonConstants.AMOUNT_SCALE_NUM));
            }else {
                calcCouponReduceResultBo.setCalcResultTotalAmount(tmpTotalAmount);
            }

            //无论是否下一个劵种累计到支付总量与阈值匹配校验,返回前台的总金额总是需要扣减总劵额
            calcCouponReduceResultBo.setFinalTotalResultAmount(BigDecimalUtil.round(
                            BigDecimalUtil.subtract(finalActualResultTotalAmount.doubleValue(),
                            needReduceAmt.doubleValue()).doubleValue(), CommonConstants.AMOUNT_SCALE_NUM));
            calcCouponReduceResultBo.setCalcCouponReduceSucceed(true);
        }else {
            calcCouponReduceResultBo.setCalcCouponReduceSucceed(false);
            calcCouponReduceResultBo.setCalcResultTotalAmount(BigDecimal.ZERO);
        }

        return calcCouponReduceResultBo;
    }

    /**
     * 计算订单或商品的每种优惠券本次总共应扣减的金额
     * @param eachCouponReqBo 计算订单或商品的每种优惠券本次应扣结算规则信息
     * @return
     */
    @Override
    public BigDecimal calcTotalReduceAmountByEachCoupon(CalcTotalReduceByEachCouponReqBo eachCouponReqBo) throws Exception{
        BigDecimal resultNeedReduceAmt = BigDecimal.ZERO;
        List<BigDecimal> reduceAmtList;

        if (eachCouponReqBo.getCouponQuantity()==null||eachCouponReqBo.getCouponQuantity()==0l)
            return BigDecimal.ZERO;

        if(eachCouponReqBo == null){
            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_CALC_TOTAL_REDUCE_BY_EACH_COUPON_REQ_INFO);
        }

        AccountRule accountRuleInfo = eachCouponReqBo.getAccountRuleInfo();
        if (accountRuleInfo != null) {
            switch (AccountRuleRewardTypeEnum.getValueByCode(accountRuleInfo.getRewardType())) {
                case MONEY:
                    if(accountRuleInfo.getThresholdType().equals(AccountRuleThresholdTypeEnum.PREFERENTIAL.getCode())) {
                        if(accountRuleInfo.getPromotionPrice().compareTo(BigDecimal.ZERO) == 0){
                            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_USER_SELECTED_COUPONS_PROMOTION_PRICE_EXCEPTION);
                        }

                        if(eachCouponReqBo.getGoodsSalePrice() != null
                                && eachCouponReqBo.getGoodsSalePrice().compareTo(BigDecimal.ZERO) != 0) {
                            try {
//                                Long userBuyGoodsQuantity = BigDecimalUtil.divide(tmpTotalAmount.doubleValue(),
//                                                            goodsSalePrice.doubleValue(), 2).longValue();
//                                if(userBuyGoodsQuantity > 0L){
                                    resultNeedReduceAmt = BigDecimalUtil.multiply(
                                                                BigDecimalUtil.subtract(eachCouponReqBo.getGoodsSalePrice().doubleValue(),
                                                                accountRuleInfo.getPromotionPrice().doubleValue()).doubleValue(),
                                                                eachCouponReqBo.getCouponQuantity().doubleValue());
//                                }
                            } catch (Exception e) {
                                throw new BusinessException(BusinessErrorCodeEnum.USER_SELECTED_COUPONS_REDUCE_CALC_ERROR);
                            }
                        }
                    }else {
                        if (eachCouponReqBo.getCouponQuantity()!=null){
                            resultNeedReduceAmt = BigDecimalUtil.multiply(accountRuleInfo.getRewardAmount().doubleValue(),
                                    eachCouponReqBo.getCouponQuantity().doubleValue());
                        }
                    }
                    break;
                case DISCOUNT:
                    reduceAmtList = new ArrayList();
                    BigDecimal afterDiscountAmt = eachCouponReqBo.getTmpTotalAmount();
                    BigDecimal eachTimeReduceAmt;

                    for (Long discountCount = 0L; discountCount < eachCouponReqBo.getCouponQuantity(); discountCount++) {
                        afterDiscountAmt = BigDecimalUtil.multiply(afterDiscountAmt.doubleValue(), accountRuleInfo.getRewardAmount().doubleValue());
                        eachTimeReduceAmt = BigDecimalUtil.subtract(eachCouponReqBo.getTmpTotalAmount().doubleValue(), afterDiscountAmt.doubleValue());
                        reduceAmtList.add(eachTimeReduceAmt);
                        eachCouponReqBo.setTmpTotalAmount(BigDecimalUtil.subtract(eachCouponReqBo.getTmpTotalAmount().doubleValue(), eachTimeReduceAmt.doubleValue()));
                    }
                    resultNeedReduceAmt = BigDecimalUtil.round(reduceAmtList.stream().reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue(),
                                          CommonConstants.AMOUNT_SCALE_NUM);
                    break;
                case POINTS:
                    resultNeedReduceAmt = BigDecimalUtil.divide(eachCouponReqBo.getCouponQuantity().doubleValue(),
                                          accountRuleInfo.getExchangeRatio().doubleValue(),0);
                    break;
            }
        }

         return resultNeedReduceAmt;
    }

    /**
     * 根据结算规则和当前额度计算该劵种实际可用数量
     *
     * @param couponId                    优惠券ID
     * @param curCouponUserRemainQuantity 用户当前劵种剩余劵数
     * @param validateTotalAmount         当前总金额
     * @param validateTotalQuantity       当前总数量
     * @return
     */
    @Override
    public Long getCouponAvailableQuantityByRule(Long couponId, Long curCouponUserRemainQuantity,
                                                 BigDecimal validateTotalAmount, Long validateTotalQuantity) {
        Coupon couponInfo = couponRepository.getCouponById(couponId);
        if (couponInfo == null) {
            throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO);
        }

        AccountRule accountRuleInfo = accountRuleRepository.getAccountRuleById(couponInfo.getAccountRuleId());
        if (accountRuleInfo == null) {
            log.error(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE.getMessage() + "优惠券ID:" + couponId);
            throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE);
        }

        Long couponActualQuantity = 0L;

        log.info("根据优惠券满减结算规则检查满减金额/数量是否匹配:规则ID:{},{}-阈值:{}", couponInfo.getAccountRuleId(),
                AccountRuleThresholdTypeEnum.getValueByCode(accountRuleInfo.getThresholdType()).getTypeName(),
                accountRuleInfo.getRewardThreshold().toString());

        //开始匹配结算金额或数量
        if (couponInfo.getCouponType().equals(CouponTypeEnum.DISCOUNT.getCode())) {
            switch (AccountRuleThresholdTypeEnum.getValueByCode(accountRuleInfo.getThresholdType())) {
                case AMOUNT:
                    if (validateTotalAmount != null && validateTotalAmount.compareTo(BigDecimal.ZERO) != 0) {
                        //若金额小于结算规则阈值金额
                        couponActualQuantity = (validateTotalAmount.divide(accountRuleInfo.getRewardThreshold(),
                                                2, BigDecimal.ROUND_DOWN).longValue());
                        couponActualQuantity = couponActualQuantity > curCouponUserRemainQuantity
                                                ? curCouponUserRemainQuantity : couponActualQuantity;
                    }
                    break;
                case QUANTITY:
                    if (validateTotalQuantity != null && validateTotalQuantity > 0L
                            && accountRuleInfo.getRewardThreshold().compareTo(BigDecimal.ZERO) != 0) {
                        couponActualQuantity = new Double(Math.floor(validateTotalQuantity /
                                accountRuleInfo.getRewardThreshold().doubleValue())).longValue();
                        //当为商品时,和商品购买数量比较后返回最小值作为实际使用数量
                        couponActualQuantity = couponActualQuantity > curCouponUserRemainQuantity
                                                ? curCouponUserRemainQuantity : couponActualQuantity;
                    }
                    break;
                case UNCONDITIONAL:
                    couponActualQuantity = curCouponUserRemainQuantity;
                    break;
                case PREFERENTIAL:
                    //当为商品时,和商品购买数量比较后返回最小值作为实际使用数量
                    couponActualQuantity = (validateTotalQuantity <= curCouponUserRemainQuantity) ?
                            validateTotalQuantity : curCouponUserRemainQuantity;
                    break;
            }
        } else if (couponInfo.getCouponType().equals(CouponTypeEnum.POINTS.getCode())) {
            //当为订单时,直接返回用户已领积分数
            if(validateTotalQuantity == null || validateTotalQuantity.equals(0L)){
                couponActualQuantity = curCouponUserRemainQuantity;
            }else {
                //todo: 当为商品时,待支付金额与用户积分金额最小值对应的积分数

            }
        }

        return couponActualQuantity == null ? 0L : couponActualQuantity;
    }
}

package lk.project.marketing.service.impl;

import com.google.gson.Gson;
import lk.project.marketing.base.bo.*;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.enums.AccountRuleRewardTypeEnum;
import lk.project.marketing.base.enums.AccountRuleThresholdTypeEnum;
import lk.project.marketing.base.enums.CouponAccumulateTypeEnum;
import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.base.entity.CouponSummary;
import lk.project.marketing.repository.AccountRuleRepository;
import lk.project.marketing.repository.CouponRepository;
import lk.project.marketing.repository.CouponSummaryRepository;
import lk.project.marketing.service.AccountRuleService;
import lk.project.marketing.service.CouponConsumeService;
import lk.project.marketing.service.CouponSummaryService;
import lk.project.marketing.service.SettleAccountService;
import lk.project.marketing.base.utils.BigDecimalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by alexlu on 2018/11/20.
 */
@Service
public class SettleAccountServiceImpl implements SettleAccountService {
    @Autowired
    CouponConsumeService couponConsumeService;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponSummaryRepository couponSummaryRepository;

    @Autowired
    CouponSummaryService couponSummaryService;

    @Autowired
    AccountRuleRepository accountRuleRepository;

    @Autowired
    AccountRuleService accountRuleService;

    private final static Logger log = LoggerFactory.getLogger(SettleAccountServiceImpl.class);

    /**
     * 订单结算
     *
     * @param memberReqBo 用户信息
     * @param orderReqBo  订单和商品信息(包含使用的优惠券)
     * @return 订单结算结果对象
     */
    @Override
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public OrderSettleResultBo orderSettlement(MemberBo memberReqBo, OrderBo orderReqBo) throws Exception {
        Gson gson = new Gson();

        log.info("----------开始进行订单结算-用户信息:{},订单信息:{}----------", gson.toJson(memberReqBo), gson.toJson(orderReqBo));
        OrderSettleResultBo orderSettleResultBo = new OrderSettleResultBo();
        BeanUtils.copyProperties(orderReqBo, orderSettleResultBo);

        //先校验当前用户下单和商品所选择的的优惠券是否可用
        Map<CouponReduceInfoBo, Map<Long, BigDecimal>> couponReduceAmountMap = new HashMap(16);
        boolean isValidateSucceed = couponConsumeService.validateCouponAvailable(memberReqBo, orderReqBo,
                true, couponReduceAmountMap);
        if (!isValidateSucceed) {
            throw new BusinessException(BusinessErrorCodeEnum.VERIFY_COUPON_AVAILABLE_RULES_CHECK_FAILED);
        }

        //订单或商品的各类优惠券扣减数据
        Map<Long, BigDecimal> reduceAmountSubMap;

        //优惠扣减总金额小计
        BigDecimal totalReducedSettlePayAmount;

        //处理订单层次的各优惠券用劵
        CouponReduceInfoBo couponReduceInfoBo = new CouponReduceInfoBo();
        couponReduceInfoBo.setCouponReduceTargetFlag(CommonConstants.COUPON_REDUCE_TARGET_TYPE_ORDER);
        BeanUtils.copyProperties(orderReqBo, couponReduceInfoBo);
        reduceAmountSubMap = couponReduceAmountMap.get(couponReduceInfoBo);

        SubTotalReduceInfoBo subTotalReduceInfoBo = new SubTotalReduceInfoBo();
        subTotalReduceInfoBo.setSubTotalFullConditionReduceAmount(BigDecimal.ZERO);
        subTotalReduceInfoBo.setSubTotalDiscountReduceAmount(BigDecimal.ZERO);
        subTotalReduceInfoBo.setSubTotalCreditExchangeReduceAmount(BigDecimal.ZERO);
        subTotalReduceInfoBo.setSubTotalOnceReduceAmount(BigDecimal.ZERO);

        List<BigDecimal> discountValueList = new ArrayList();
        Map<Long, BigDecimal> creditValueMap = new HashMap(16);

        //根据结算规则计算订单层次优惠券满减扣减情况
        totalReducedSettlePayAmount = calcReduceAmount(memberReqBo, orderReqBo, null,
                                        orderReqBo.getUseCoupons(), reduceAmountSubMap, subTotalReduceInfoBo,
                                        discountValueList, creditValueMap);

        //根据结算规则计算订单项/商品层次优惠券满减扣减情况
        if (!CollectionUtils.isEmpty(orderReqBo.getOrderItemList())) {
            for (OrderItemBo orderItemBo : orderReqBo.getOrderItemList()) {
                couponReduceInfoBo = new CouponReduceInfoBo();
                couponReduceInfoBo.setCouponReduceTargetFlag(CommonConstants.COUPON_REDUCE_TARGET_TYPE_ORDER_ITEM);
                BeanUtils.copyProperties(orderItemBo, couponReduceInfoBo);
                reduceAmountSubMap = couponReduceAmountMap.get(couponReduceInfoBo);

                totalReducedSettlePayAmount = totalReducedSettlePayAmount.subtract(calcReduceAmount(memberReqBo, null, orderItemBo,
                        orderItemBo.getUseCoupons(), reduceAmountSubMap, subTotalReduceInfoBo,
                        discountValueList, creditValueMap));
            }
        }

        //再结算所有折扣劵扣减优惠情况(仅扣减一次额度,从总额扣减)
        if (!CollectionUtils.isEmpty(discountValueList)) {
            for (BigDecimal discountRatioAmt : discountValueList) {
                subTotalReduceInfoBo.setSubTotalDiscountReduceAmount(BigDecimalUtil.add(
                        subTotalReduceInfoBo.getSubTotalDiscountReduceAmount().doubleValue(),
                        discountRatioAmt.doubleValue()));
                totalReducedSettlePayAmount = BigDecimalUtil.subtract(totalReducedSettlePayAmount.doubleValue(),
                                              discountRatioAmt.doubleValue());
            }
        }

        //再结算所有积分劵兑换扣减优惠情况(从总额扣减)
        if(!CollectionUtils.isEmpty(creditValueMap)) {
            BigDecimal creditExchangeReduceAmt = BigDecimal.ZERO;
            for (Map.Entry<Long, BigDecimal> creditNum : creditValueMap.entrySet()) {
                //计算各种优惠券的积分兑换率
                creditExchangeReduceAmt = BigDecimalUtil.add(creditExchangeReduceAmt.doubleValue(),
                        creditNum.getValue().doubleValue());
                subTotalReduceInfoBo.setSubTotalCreditExchangeReduceAmount(BigDecimalUtil.add(
                        subTotalReduceInfoBo.getSubTotalCreditExchangeReduceAmount().doubleValue(),
                        creditExchangeReduceAmt.doubleValue()));
                totalReducedSettlePayAmount = BigDecimalUtil.subtract(totalReducedSettlePayAmount.doubleValue(),
                                              creditExchangeReduceAmt.doubleValue());
            }
        }

        //存储各优惠形式情况下的各项扣减金额,以便返回前台显示
        List<BigDecimal> couponReduceAmountList = new ArrayList();
        couponReduceAmountList.add(BigDecimalUtil.round(subTotalReduceInfoBo.getSubTotalFullConditionReduceAmount().doubleValue(),
                                    CommonConstants.AMOUNT_SCALE_NUM));
        couponReduceAmountList.add(BigDecimalUtil.round(subTotalReduceInfoBo.getSubTotalDiscountReduceAmount().doubleValue(),
                                    CommonConstants.AMOUNT_SCALE_NUM));
        couponReduceAmountList.add(BigDecimalUtil.round(subTotalReduceInfoBo.getSubTotalCreditExchangeReduceAmount().doubleValue(),
                                    CommonConstants.AMOUNT_SCALE_NUM));
        couponReduceAmountList.add(BigDecimalUtil.round(subTotalReduceInfoBo.getSubTotalOnceReduceAmount().doubleValue(),
                                    CommonConstants.AMOUNT_SCALE_NUM));

        BeanUtils.copyProperties(subTotalReduceInfoBo, orderSettleResultBo);

        //计算总优惠金额并返回
        BigDecimal totalReducedAmount = BigDecimal.ZERO;
        for (BigDecimal cpReduceAmt : couponReduceAmountList){
            totalReducedAmount = BigDecimalUtil.add(totalReducedAmount.doubleValue(), cpReduceAmt.doubleValue());
        }
        orderSettleResultBo.setTotalReduceAmount(BigDecimalUtil.round(totalReducedAmount.doubleValue(), CommonConstants.AMOUNT_SCALE_NUM));

        //返回总实付金额
        BigDecimal actualPayAmount = BigDecimal.ZERO;
        if(orderSettleResultBo.getOriginOrderAmount().compareTo(totalReducedAmount) != -1){
            actualPayAmount = BigDecimalUtil.round(BigDecimalUtil.subtract(orderSettleResultBo.getOriginOrderAmount().doubleValue(),
                    totalReducedAmount.doubleValue()).doubleValue(), CommonConstants.AMOUNT_SCALE_NUM);
        }

        orderSettleResultBo.setPayOrderAmount(actualPayAmount);

        return orderSettleResultBo;
    }

    /**
     * 根据各个优惠券的结算规则计算优惠应付金额
     *
     * @param memberReqBo          用户信息
     * @param orderReqBo           订单信息
     * @param orderItemReqBo       订单项信息
     * @param useCouponList        用户请求使用的优惠券列表
     * @param reduceAmountSubMap   订单或商品的各类优惠券扣减数据
     * @param discountValueList    所有折扣劵的扣减金额列表
     * @param discountValueList    所有折扣劵的应扣减金额列表
     * @param creditValueMap       所有积分劵的应扣减列表
     * @return
     */
    private BigDecimal calcReduceAmount(MemberBo memberReqBo,
                                        OrderBo orderReqBo, OrderItemBo orderItemReqBo,
                                        List<UseCouponBo> useCouponList,
                                        Map<Long, BigDecimal> reduceAmountSubMap,
                                        SubTotalReduceInfoBo subTotalReduceInfoBo,
                                        List<BigDecimal> discountValueList,
                                        Map<Long, BigDecimal> creditValueMap) {
        Gson gson = new Gson();
        if (CollectionUtils.isEmpty(useCouponList)) {
            return BigDecimal.ZERO;
        }

        if (reduceAmountSubMap == null) {
            log.error(BusinessErrorCodeEnum.EMPTY_ACCOUNT_RULES_CHECK_RETURN_REDUCE_AMOUNT_MAP.getMessage()
                    + "订单数据:" + gson.toJson(orderReqBo) + "优惠券数据:" + gson.toJson(useCouponList));
//            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_ACCOUNT_RULES_CHECK_RETURN_REDUCE_AMOUNT_MAP);
        }

        //优惠扣减总金额小计
        BigDecimal resultReducedPayAmount = BigDecimal.ZERO;

        if (orderReqBo != null) {
            resultReducedPayAmount = orderReqBo.getTotalOrderAmount();
        } else if (orderItemReqBo != null) {
            resultReducedPayAmount = orderItemReqBo.getTotalGoodsAmount();
        }

        List<UseCouponBo> resultSettledCouponList = new ArrayList<>();
        resultSettledCouponList.addAll(useCouponList);

        int cpIndex = 0;

        for (UseCouponBo useCouponBo : useCouponList) {
            //查找优惠券汇总记录,用于校验和后续数据回写更新优惠券使用数量和金额
            CouponSummary couponSummaryInfo = couponSummaryRepository.getUserCouponSummaryInfo(memberReqBo.getUserId(),
                                                useCouponBo.getCouponId());
            if (couponSummaryInfo == null) {
                throw new BusinessException(BusinessErrorCodeEnum.EMPTY_COUPON_SUMMARY_INFO);
            }

            String couponLogStr = "优惠券ID:" + useCouponBo.getCouponId();
            //校验优惠券可用数量是否满足
            if (useCouponBo.getCouponQuantity() > couponSummaryInfo.getCouponQuantity()) {
                throw new BusinessException(BusinessErrorCodeEnum.COUPON_SUMMARY_QUANTITY_NOT_AVAILABLE.getCode(),
                        BusinessErrorCodeEnum.COUPON_SUMMARY_QUANTITY_NOT_AVAILABLE.getMessage() + couponLogStr);
            }

            Coupon coupon = couponRepository.getCouponById(useCouponBo.getCouponId());
            if (coupon == null) {
                log.error(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO.getMessage() + couponLogStr);
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO);
            }

            AccountRule accountRule = accountRuleRepository.getAccountRuleById(coupon.getAccountRuleId());
            if (accountRule == null) {
                log.error(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE.getMessage() + "结算规则ID:" + coupon.getAccountRuleId());
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE);
            }

            BigDecimal curReduceAmount = reduceAmountSubMap.get(useCouponBo.getCouponId());

            try {
                if (accountRule.getRewardType().equals(AccountRuleRewardTypeEnum.MONEY.getCode())) {
                    if (orderItemReqBo != null) {
                        //若是商品的一口价类型的优惠券,当购买数量大于选择的优惠券数量,则退回用户多出来的优惠券
                        if (accountRule.getThresholdType().equals(AccountRuleThresholdTypeEnum.PREFERENTIAL.getCode())) {
                            if (orderItemReqBo.getGoodsQuantity() < useCouponBo.getCouponQuantity()) {
                                //从请求的劵数量中扣减,用于更新后续用劵实际使用(不返回前台,用劵时校验)
                                resultSettledCouponList.get(cpIndex).setCouponQuantity(orderItemReqBo.getGoodsQuantity());
                            }

                            //计算一口价情况优惠的金额(优惠金额=(原销售价-一口价格)*用券数量),累加扣减额度
                            subTotalReduceInfoBo.setSubTotalOnceReduceAmount(BigDecimalUtil.add(
                                    subTotalReduceInfoBo.getSubTotalOnceReduceAmount().doubleValue(),
                                    orderItemReqBo.getGoodsSalePrice().subtract(accountRule.getPromotionPrice()).multiply(
                                    BigDecimal.valueOf(resultSettledCouponList.get(cpIndex).getCouponQuantity())).doubleValue()));
                            //结算一口价情况优惠的金额(从总额扣减)
                            resultReducedPayAmount = BigDecimalUtil.subtract(resultReducedPayAmount.doubleValue(),
                                    subTotalReduceInfoBo.getSubTotalOnceReduceAmount().doubleValue());
                            return resultReducedPayAmount;
                        }
                    }

                    //计算满减情况的累加扣减额度
                    if(curReduceAmount.compareTo(BigDecimal.ZERO) != 0) {
                        subTotalReduceInfoBo.setSubTotalFullConditionReduceAmount(
                                BigDecimalUtil.add(subTotalReduceInfoBo.getSubTotalFullConditionReduceAmount().doubleValue(),
                                        curReduceAmount.doubleValue()));
                    }

                    //结算满减情况的累加扣减额度
                    resultReducedPayAmount = BigDecimalUtil.subtract(resultReducedPayAmount.doubleValue(), curReduceAmount.doubleValue());
                } else if (accountRule.getRewardType().equals(AccountRuleRewardTypeEnum.DISCOUNT.getCode())) {
                    discountValueList.add(curReduceAmount);
                } else if (accountRule.getRewardType().equals(AccountRuleRewardTypeEnum.POINTS.getCode())) {
                    creditValueMap.put(useCouponBo.getCouponId(), curReduceAmount);
                }
            } catch (BusinessException e) {
                log.error(BusinessErrorCodeEnum.SETTLE_CALC_SUM_REWARD_AMOUNT_FAILED.getMessage(), e);
                throw new BusinessException(BusinessErrorCodeEnum.SETTLE_CALC_SUM_REWARD_AMOUNT_FAILED);
            }

            cpIndex++;
        }

        return resultReducedPayAmount;
    }

    /**
     * 校验优惠券选项有效性
     * @param dynamicSelectCouponReqBo
     * @throws BusinessException
     */
    private void validateSelectCoupon(DynamicSelectCouponReqBo dynamicSelectCouponReqBo)throws BusinessException{

        List<DynamicCalcSelectedCouponBo> calcUserCouponList = dynamicSelectCouponReqBo.getDynamicCalcSelectedCouponList();
        if (CollectionUtils.isEmpty(calcUserCouponList)) {
            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_USER_SELECTED_COUPONS_LIST);
        }

        calcUserCouponList.stream()
            .forEach(o->{
                Coupon coupon = couponRepository.getCouponById(o.getCouponId());
                if (coupon == null) {
                    throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO);
                }
                /** 将选中状态但使用数量为0的优惠券选项重置为未选中状态 */
                Long couponSelectQuantity = o.getCouponSelectQuantity()==null?0l:o.getCouponSelectQuantity();
                if ( o.getStatus().equals(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_SELECTED)&&
                     couponSelectQuantity==0l){
                    o.setStatus(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_NOT_SELECTED);
                }
                o.setCoupon(coupon);
            });

    }

    /**
     * 重算优惠券剩余可用数量
     * @param dynamicSelectCouponReqBo
     * @param couponOption
     */
    private void setCouponAvailableCount(DynamicSelectCouponReqBo dynamicSelectCouponReqBo,
                                        DynamicCalcSelectedCouponBo couponOption){

        Long useQuantity = couponOption.getCouponSelectQuantity()==null?0L:couponOption.getCouponSelectQuantity();

        Long userRealTimeAvailableQty = couponSummaryService.getCouponAvailableQuantityForOrder(couponOption.getCouponId(),
                dynamicSelectCouponReqBo.getOrderBo(),
                dynamicSelectCouponReqBo.getOrderItemBo(),
                dynamicSelectCouponReqBo.getMemberBo());

        couponOption.setCouponAvailableQuantity(userRealTimeAvailableQty);
        couponOption.setCouponAvailableQuantity(userRealTimeAvailableQty != 0L
                ? (userRealTimeAvailableQty - useQuantity) : 0L);
    }

    /**
     * 校验优惠券结算规则并计算优惠券扣减金额
     * @param couponItem
     * @param dynamicSelectCouponReqBo
     * @param reduceAmountSubMap
     * @return
     */
    private Boolean validateCouponReduceAmt(DynamicCalcSelectedCouponBo couponItem,
                                            DynamicSelectCouponReqBo dynamicSelectCouponReqBo,
                                            Map<Long, BigDecimal> reduceAmountSubMap){
        try {
            setCouponAvailableCount(dynamicSelectCouponReqBo, couponItem);
            CalcCouponReduceResultBo couponReduceResultBo = accountRuleService.calcCouponReduceResultBySequence(
                    couponItem.getCouponId(), couponItem.getCouponSelectQuantity(),
                    dynamicSelectCouponReqBo.getOrderItemBo() != null ?
                            dynamicSelectCouponReqBo.getOrderItemBo().getGoodsSalePrice() : BigDecimal.ZERO,
                    dynamicSelectCouponReqBo.getVirtualCalcTotalAmount(),
                    dynamicSelectCouponReqBo.getTotalAmount(),
                    reduceAmountSubMap);
            if (couponReduceResultBo.getCalcCouponReduceSucceed()) {
                dynamicSelectCouponReqBo.setTotalAmount(couponReduceResultBo.getFinalTotalResultAmount());
                dynamicSelectCouponReqBo.setVirtualCalcTotalAmount(couponReduceResultBo.getCalcResultTotalAmount());
                couponItem.setCouponReducedAmount(BigDecimalUtil.round(
                        reduceAmountSubMap.get(couponItem.getCouponId()).doubleValue(),
                        CommonConstants.AMOUNT_SCALE_NUM));
            }
            return couponReduceResultBo.getCalcCouponReduceSucceed();
        } catch (Exception e) {
            //若条件不满足,则禁用
            log.error(BusinessErrorCodeEnum.USER_SELECTED_COUPONS_ACCOUNT_RULE_MATCH_FAILED.getMessage() +
                            "优惠券ID:" + couponItem.getCouponId() +
                            "当前总金额:" + dynamicSelectCouponReqBo.getTotalAmount().toString(), e);
            couponItem.setStatus(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_FORBIDDEN);
            return false;
        }
    }

    /**
     * 动态计算返回用户每次选择的优惠券数据列表
     *
     * @param dynamicSelectCouponReqBo
     * @return
     */
    @Override
    public DynamicSelectCouponResultRespBo selectCoupon(DynamicSelectCouponReqBo dynamicSelectCouponReqBo) throws Exception {

        validateSelectCoupon(dynamicSelectCouponReqBo);
        DynamicSelectCouponResultRespBo dynamicSelectCouponResultRespBo = new DynamicSelectCouponResultRespBo();
        BeanUtils.copyProperties(dynamicSelectCouponReqBo, dynamicSelectCouponResultRespBo);

        if (dynamicSelectCouponReqBo.getTotalAmount() == null){
            dynamicSelectCouponReqBo.setTotalAmount(BigDecimal.ZERO);
        }
        dynamicSelectCouponReqBo.setVirtualCalcTotalAmount(dynamicSelectCouponReqBo.getTotalAmount());

        final Map<Long, BigDecimal> reduceAmountSubMap = new HashMap(16);

        /**
         * 取选中的第一项排他优惠券使用，并计算扣减和应付金额
         */
        Optional<DynamicCalcSelectedCouponBo> exclusiveUseCoupon =
        dynamicSelectCouponReqBo.getDynamicCalcSelectedCouponList().stream()
                .filter(o->(o.getCoupon().getAccumulateType().equals(CouponAccumulateTypeEnum.EXCLUSIVE.getCode())
                                && o.getStatus().equals(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_SELECTED)))
                .findFirst();

        exclusiveUseCoupon.ifPresent(exclusiveCoupon-> {
            if (validateCouponReduceAmt(exclusiveCoupon, dynamicSelectCouponReqBo, reduceAmountSubMap)) {
                /** 禁用其余优惠券选项 */
                dynamicSelectCouponReqBo.getDynamicCalcSelectedCouponList().stream()
                        .filter(o -> (o.getCouponId() != exclusiveCoupon.getCouponId()))
                        .forEach(o -> {
                            o.setStatus(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_FORBIDDEN);
                            o.setCouponSelectQuantity(0l);
                        });
            }
        });

        exclusiveUseCoupon.orElseGet( ()-> {
                /** 重算选中状态优惠券的优惠金额及可使用数量*/
                dynamicSelectCouponReqBo.getDynamicCalcSelectedCouponList().stream()
                        .filter(o -> (o.getStatus().equals(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_SELECTED)))
                        .forEach(o -> validateCouponReduceAmt(o, dynamicSelectCouponReqBo, reduceAmountSubMap));

                /** 判断重置未选中状态优惠券的可选状态、可用数量*/
                dynamicSelectCouponReqBo.getDynamicCalcSelectedCouponList().stream()
                        .filter(o -> (!o.getStatus().equals(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_SELECTED)))
                        .forEach(o -> {
                            if (validateCouponReduceAmt(o, dynamicSelectCouponReqBo, reduceAmountSubMap)) {
                                o.setStatus(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_NOT_SELECTED);
                            } else {
                                o.setStatus(CommonConstants.CURRENT_USER_USE_COUPON_STATUS_FORBIDDEN);
                            }
                        });
                return null;
            }

        );

        dynamicSelectCouponResultRespBo.setVirtualCalcTotalAmount(dynamicSelectCouponReqBo.getVirtualCalcTotalAmount());
        dynamicSelectCouponResultRespBo.setFinalTotalAmount(dynamicSelectCouponReqBo.getTotalAmount());
        dynamicSelectCouponResultRespBo.setDynamicCalcSelectedCouponList(dynamicSelectCouponReqBo.getDynamicCalcSelectedCouponList());

        return dynamicSelectCouponResultRespBo;
    }

}

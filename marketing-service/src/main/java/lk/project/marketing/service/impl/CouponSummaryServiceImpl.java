package lk.project.marketing.service.impl;

import lk.project.marketing.base.bo.*;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.base.enums.AccountRuleRewardTypeEnum;
import lk.project.marketing.base.enums.AccountRuleThresholdTypeEnum;
import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.base.entity.CouponSummary;
import lk.project.marketing.repository.CouponReceiveDetailRepository;
import lk.project.marketing.repository.CouponRepository;
import lk.project.marketing.repository.CouponSummaryRepository;
import lk.project.marketing.service.AccountRuleService;
import lk.project.marketing.service.CouponConsumeService;
import lk.project.marketing.service.CouponSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static lk.project.marketing.base.enums.AccountRuleRewardTypeEnum.DISCOUNT;
import static lk.project.marketing.base.enums.AccountRuleRewardTypeEnum.GIFT;

/**
 * Created by gupei on 2018/12/07.
 */
@Service
@Slf4j
public class CouponSummaryServiceImpl implements CouponSummaryService {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponConsumeService couponConsumeService;

    @Autowired
    CouponSummaryRepository couponSummaryRepository;

    @Autowired
    CouponReceiveDetailRepository couponReceiveDetailRepository;

    @Override
    public List<CouponSummaryBo> getUserCouponSummary(String userId) {

        List<CouponSummary> userCouponSummaryList = couponSummaryRepository.queryUserCoupons(userId);

        if (CollectionUtils.isEmpty(userCouponSummaryList)){
            return Collections.EMPTY_LIST;
        }
        else{
            return userCouponSummaryList.stream()
                    .map( o->{
                        CouponSummaryBo couponSummaryBo = new CouponSummaryBo();
                        couponSummaryBo.setCouponSummary(o);
                        Coupon coupon = couponRepository.getCouponById(o.getCouponId());
                        couponSummaryBo.setCompanyId(coupon.getCompanyId());
                        couponSummaryBo.setCouponType(coupon.getCouponType());
                        couponSummaryBo.setCouponName(coupon.getCouponName());
                        couponSummaryBo.setCouponDesc(coupon.getCouponDesc());
                        couponSummaryBo.setIcon(coupon.getIcon());
                        return couponSummaryBo;
                    })
                    .collect(Collectors.toList());
        }

    }

    @Override
    public List<CouponSummaryDetailBo> getUserCouponDetail(String userId, Long couponId) {

        return couponReceiveDetailRepository.getUserCouponReceiveDetailListByCouponId(couponId, userId);
    }

    @Override
    public Long getCouponAvailableQuantityForOrder(Long couponId,
                                                   OrderBo orderReqBo,
                                                   OrderItemBo orderItemReqBo,
                                                   MemberBo memberReqBo) {

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal goodsSalePrice = BigDecimal.ZERO;
        Long totalGoodsQuantity = 0L;

        if (orderReqBo!=null){
            totalAmount = orderReqBo.getTotalOrderAmount();
        }
        else if(orderItemReqBo!=null){
            totalAmount = orderItemReqBo.getTotalGoodsAmount();
            goodsSalePrice = orderItemReqBo.getGoodsSalePrice();
            totalGoodsQuantity = orderItemReqBo.getGoodsQuantity();
        }

        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0){
            return 0L;
        }

        Coupon couponInfo = couponRepository.getCouponById(couponId);
        if (couponInfo == null) {
            throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO);
        }

        AccountRule accountRuleInfo = couponInfo.getAccountRule();
        if (accountRuleInfo == null) {
            log.error(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE.getMessage() + "优惠券ID:" + couponId);
            throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE);
        }

        /** 取用户持有并适用订单的优惠券数量作为可用券数量的初始值 */
        Long couponAvailableQuantity = couponConsumeService.getTotalCouponValidRemainQuantity(memberReqBo,
                                                                orderReqBo,orderItemReqBo,couponId);

        /**
         * 按满减类型及阈值计算订单可用券数量，并与用户拥有的优惠券数量取最小值作为当前可用券数量
         */
        switch (AccountRuleThresholdTypeEnum.getValueByCode(accountRuleInfo.getThresholdType())){
            case PREFERENTIAL:
                if (totalGoodsQuantity != null && totalGoodsQuantity > 0L){
                    couponAvailableQuantity = Math.min(couponAvailableQuantity,totalGoodsQuantity);
                }
                break;
            case AMOUNT:
                if (accountRuleInfo.getRewardThreshold().compareTo(BigDecimal.ZERO) != 0) {
                    couponAvailableQuantity = Math.min(couponAvailableQuantity,
                            totalAmount.divide(accountRuleInfo.getRewardThreshold(),
                                    2, BigDecimal.ROUND_DOWN).longValue());
                }
                break;
            case QUANTITY:
                if (totalGoodsQuantity != null && totalGoodsQuantity > 0L
                        && accountRuleInfo.getRewardThreshold().compareTo(BigDecimal.ZERO) != 0) {
                    couponAvailableQuantity = Math.min(couponAvailableQuantity,
                             new Double(Math.floor(totalGoodsQuantity /
                            accountRuleInfo.getRewardThreshold().doubleValue())).longValue());
                }
                break;
            case UNCONDITIONAL:
                break;

        }

        /**
         * 计算支付金额最多需要的优惠券数量，并与当前可用券数量取最小值；
         * 折扣券、赠品及一口价不需要考虑支付金额对应的最大用券数
         */
        AccountRuleRewardTypeEnum rewardTypeEnum = AccountRuleRewardTypeEnum.
                getValueByCode(accountRuleInfo.getRewardType());
        if (!rewardTypeEnum.equals(DISCOUNT)&&
            !rewardTypeEnum.equals(GIFT)&&
            !accountRuleInfo.getThresholdType().equals(AccountRuleThresholdTypeEnum.PREFERENTIAL.getCode())){
            CouponsReduceAmountBo couponsRewardBo = AccountRuleService.calcCouponReward(
                    couponInfo,totalAmount,goodsSalePrice);
            if (couponsRewardBo.getCouponReduceAmount()!=null&&
                couponsRewardBo.getCouponReduceAmount().compareTo(BigDecimal.ZERO)!=0){
                couponAvailableQuantity = Math.min(couponAvailableQuantity,
                totalAmount.divide(couponsRewardBo.getCouponReduceAmount(),0, BigDecimal.ROUND_UP).longValue());
            }
        }

        return couponAvailableQuantity;
    }
}

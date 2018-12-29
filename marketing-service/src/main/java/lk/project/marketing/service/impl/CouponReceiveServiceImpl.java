package lk.project.marketing.service.impl;

import lk.project.marketing.base.bo.CouponReceiveBo;
import lk.project.marketing.base.bo.CouponReceiveReqBo;
import lk.project.marketing.base.bo.MemberBo;
import lk.project.marketing.base.bo.OrderBo;
import lk.project.marketing.base.client.RedisLock;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.base.enums.AccountRuleRewardTypeEnum;
import lk.project.marketing.base.enums.CouponReceiveStatusEnum;
import lk.project.marketing.base.enums.PromotionRuleTypeEnum;
import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.base.entity.CouponReceive;
import lk.project.marketing.base.entity.PromotionActivity;
import lk.project.marketing.repository.ActivityRepository;
import lk.project.marketing.repository.CouponReceiveRepository;
import lk.project.marketing.repository.CouponRepository;
import lk.project.marketing.service.ActivityService;
import lk.project.marketing.service.CouponReceiveDetailService;
import lk.project.marketing.service.CouponReceiveService;
import lk.project.marketing.service.PromotionRuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by Pei Gu on 2018/9/25.
 */
@Slf4j
@Service
public class CouponReceiveServiceImpl implements CouponReceiveService {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    CouponReceiveRepository couponReceiveRepository;

    @Autowired
    CouponReceiveDetailService couponReceiveDetailServiceImpl;

    @Autowired
    private RedisLock redisLock;

    private static final String LOCK_PREFIX="activity:";

    private static final Long RECEIVE_TIMEOUT = 3000L;


    @Override
    public CouponReceiveBo produceCoupon(CouponReceiveReqBo couponReceiveReqBo, MemberBo memberReqBo) {

        Long activityId = couponReceiveReqBo.getActivityId();
        if (activityId==null) throw new BusinessException( BusinessErrorCodeEnum.EMPTY_PROMOTION_ACTIVITY);

        PromotionActivity activePromotion = activityRepository.getActivityById(activityId);
        if (activePromotion==null) throw new BusinessException(BusinessErrorCodeEnum.NO_ACTIVE_PROMOTION);
        if  (!ActivityService.verifyActivityPeriod(activePromotion)) {
            throw new BusinessException(BusinessErrorCodeEnum.NOT_MATCHED_ACTIVITY_RULE);
        }

        if (!PromotionRuleService.verifyPromotionRule(memberReqBo,couponReceiveReqBo.getOrderBo(),
                couponReceiveReqBo.getOrderItemBo(), activePromotion.getPromotionRules(), PromotionRuleTypeEnum.ISSUE.getCode())){
            log.info("不满足领券条件，促销活动:{};用户ID:{}",activePromotion.getActivityName(),memberReqBo.getUserId());
            throw new BusinessException(BusinessErrorCodeEnum.NOT_MATCHED_RECEIVE_PROMOTION_RULE.getCode(),
                            String.format(BusinessErrorCodeEnum.NOT_MATCHED_RECEIVE_PROMOTION_RULE.getMessage(),
                            activePromotion.getActivityName()));
        }

        /**
         * 领券/发券活动有数量限制时，需要同步锁定领券流程
         */
        String lockName = LOCK_PREFIX;
        if (activePromotion.getIssueQuantity()!=null){
            lockName += activePromotion.getId();
        }
        if (lockName.equals(LOCK_PREFIX)&&activePromotion.getLimitQuantity()!=null) {
            lockName += memberReqBo.getUserId();
        }

        CouponReceiveBo couponReceiveBo;
        if (lockName.equals(LOCK_PREFIX)){
            couponReceiveBo = executeCouponReceiveTransaction(couponReceiveReqBo, memberReqBo);
        }
        else{
            String lockIdentify = redisLock.lock(lockName,RECEIVE_TIMEOUT);
            if (StringUtils.isNotEmpty(lockIdentify)) {
                couponReceiveBo = executeCouponReceiveTransaction(couponReceiveReqBo, memberReqBo);
                redisLock.releaseLock(lockName, lockIdentify);
            }
            else{
                throw new BusinessException(BusinessErrorCodeEnum.RECEIVE_COUPON_TIME);
            }

        }
        return couponReceiveBo;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private CouponReceiveBo executeCouponReceiveTransaction( CouponReceiveReqBo couponReceiveReqBo,
                                                             MemberBo memberReqBo){

        PromotionActivity activity = activityRepository.getActivityById(couponReceiveReqBo.getActivityId());
        CouponReceiveBo couponReceiveBo = buildCouponReceive(activity, couponReceiveReqBo, memberReqBo);
        couponReceiveRepository.saveCouponReceiveBo(couponReceiveBo);
        Long receivedQuantity = activity.getReceivedQuantity();
        if (receivedQuantity==null){
            receivedQuantity = couponReceiveBo.getCouponReceive().getReceiveQuantity();
        }
        else{
            receivedQuantity+= couponReceiveBo.getCouponReceive().getReceiveQuantity();
        }
        activity.setReceivedQuantity(receivedQuantity);
        activityRepository.updateActivity(activity);
        return couponReceiveBo;

    }

    private CouponReceiveBo buildCouponReceive(PromotionActivity activePromotion,
                                               CouponReceiveReqBo receiveReqBo,
                                               MemberBo memberReqBo){

        Coupon coupon = couponRepository.getCouponById(activePromotion.getCouponId());
        if (coupon==null) throw new BusinessException(BusinessErrorCodeEnum.NO_RELATED_COUPON);
        AccountRule accountRule = coupon.getAccountRule();
        if (accountRule==null) throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE);

        CouponReceiveBo couponReceiveBo = new CouponReceiveBo();
        couponReceiveBo.setCompanyId(coupon.getCompanyId());
        CouponReceive couponReceive = new CouponReceive();
        couponReceiveBo.setCouponReceive(couponReceive);

        couponReceive.setActivityId(activePromotion.getId());
        couponReceive.setCouponId(activePromotion.getCouponId());
        couponReceive.setUserId(memberReqBo.getUserId());
        couponReceive.setUserCode(memberReqBo.getUserCode());

        Integer receiveQuantity = receiveReqBo.getRequestQuantity();
        if (receiveQuantity==null||receiveQuantity.intValue()<=0){
            /** 由活动指定领券数量,活动自动发券不需要校验总发行数量及用户领取数量上限 */
            if (activePromotion.getAutoIssueQuantity()==null||activePromotion.getAutoIssueQuantity().intValue()<=0){
                throw new BusinessException(BusinessErrorCodeEnum.NO_RECEIVE_QUANTITY);
            }
            couponReceive.setReceiveQuantity(activePromotion.getAutoIssueQuantity().longValue());

        }
        else{
            /** 存在用户领券数上限时,领券数量校验 */
            Long limitedQuantity = activePromotion.getLimitQuantity();
            if (limitedQuantity!=null&&limitedQuantity>0){
                Long historyQuantity = couponReceiveRepository.getReceivedCouponQuantities(
                        String.valueOf(memberReqBo.getUserId()),
                        activePromotion.getId());
                historyQuantity = historyQuantity==null?0l:historyQuantity;
                Long canReceiveQuantity = limitedQuantity - historyQuantity;
                if (receiveQuantity>canReceiveQuantity){
                    throw new BusinessException(BusinessErrorCodeEnum.EXCEED_USER_RECEIVE_QUANTITY);
                }
            }

            /** 存在活动发行数量限制时,校验活动可发券数量 */
            Long issueQuantity = activePromotion.getIssueQuantity();
            if (issueQuantity!=null&&issueQuantity>0){
                Long activityReceivedQuantity = activePromotion.getReceivedQuantity();
                activityReceivedQuantity = activityReceivedQuantity==null?0l:activityReceivedQuantity;
                Long canReceiveQuantity = issueQuantity - activityReceivedQuantity;
                if (receiveQuantity>canReceiveQuantity){
                    throw new BusinessException(BusinessErrorCodeEnum.EXCEED_ACTIVITY_RECEIVE_QUANTITY);
                }
            }

            couponReceive.setReceiveQuantity(receiveQuantity.longValue());
        }

        couponReceive.setRemainQuantity(couponReceive.getReceiveQuantity());
        couponReceive.setCouponAmount(accountRule.getRewardAmount());

        if (receiveReqBo.getOrderBo()!=null){
            couponReceive.setOrderId(receiveReqBo.getOrderBo().getOrderId());
            couponReceive.setOrderNo(receiveReqBo.getOrderBo().getOrderNo());
        }

        if (receiveReqBo.getOrderItemBo()!=null) {
            couponReceive.setOrderItemId(receiveReqBo.getOrderItemBo().getOrderItemId());
        }

        couponReceive.setStatus(CouponReceiveStatusEnum.NOT_USED.getCode());
        if (activePromotion.getEffectDate()==null){
            couponReceive.setStartDate(new Date(System.currentTimeMillis()));
        }
        else{
            couponReceive.setStartDate(activePromotion.getEffectDate());
        }

        /** 未指定有效天数表示没有有效期 */
        if (activePromotion.getEffectDays()!=null){
            couponReceive.setEndDate(
                    new Date(DateUtils.addDays(couponReceive.getStartDate(),activePromotion.getEffectDays().intValue())
                            .getTime()) );

        }
        couponReceive.setRemark(receiveReqBo.getSceneDesc());

        /**
         * 非积分类型领券/发券需要生成发券明细记录
         */
        if (!accountRule.getRewardType().equals(AccountRuleRewardTypeEnum.POINTS.getCode())){
            couponReceiveBo.setCouponReceiveDetails(
                    couponReceiveDetailServiceImpl.buildCouponReceiveDetails(couponReceive));

        }

        return couponReceiveBo;
    }

    @Override
    public List<CouponReceiveBo> produceCouponForOrder(List<Coupon> qualifiedCoupons, MemberBo memberReqBo, OrderBo orderReqBo) {
        return null;
    }


    @Override
    public List<CouponReceiveBo> getRefundCoupon(List<CouponReceiveBo> refundCoupons) {
        return null;
    }

}

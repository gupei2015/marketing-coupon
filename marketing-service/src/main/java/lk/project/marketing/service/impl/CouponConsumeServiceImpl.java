package lk.project.marketing.service.impl;

import com.google.gson.Gson;
import lk.project.marketing.base.bo.*;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.entity.*;
import lk.project.marketing.base.enums.*;
import lk.project.marketing.base.utils.BigDecimalUtil;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.repository.*;
import lk.project.marketing.service.*;
import org.apache.commons.lang3.StringUtils;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by alexlu on 2018/10/29.
 */
@Service
public class CouponConsumeServiceImpl implements CouponConsumeService {

    @Autowired
    PromotionRuleService promotionRuleService;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponReceiveRepository couponReceiveRepository;

    @Autowired
    CouponReceiveDetailRepository couponReceiveDetailRepository;

    @Autowired
    PromotionRuleRepository promotionRuleRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    CouponConsumeDetailRepository couponConsumeDetailRepository;

    @Autowired
    CouponSummaryRepository couponSummaryRepository;

    @Autowired
    CouponSummaryService couponSummaryService;

    @Autowired
    CouponConsumeRepository couponConsumeRepository;

    @Autowired
    AccountRuleService accountRuleService;

    @Autowired
    AccountRuleRepository accountRuleRepository;

    private final static Logger log = LoggerFactory.getLogger(CouponConsumeServiceImpl.class);

    /**
     * 查询订单关联的优惠券使用记录
     *
     * @param orderId 订单ID
     * @return 已使用的优惠券
     */
    @Override
    public CouponConsumeHistoryRespBo queryOrderConsumedCoupon(String orderId) {
        CouponConsumeHistoryRespBo couponConsumeHistoryBo = new CouponConsumeHistoryRespBo();

        if (StringUtils.isNotEmpty(orderId)) {
            CouponConsume couponConsume = couponConsumeRepository.getCouponConsumeInfoByOrderId(orderId);
            if (couponConsume == null) {
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_USER_COUPON_CONSUME_RECORD);
            }

            BeanUtils.copyProperties(couponConsume, couponConsumeHistoryBo);

            List<CouponHistoryInfoBo> couponHistoryInfoBoList = couponSummaryRepository.getCouponHistoryDetails(couponConsume.getUserId(), orderId);
            couponConsumeHistoryBo.setCouponHistoryInfoBoList(couponHistoryInfoBoList);
        }

        return couponConsumeHistoryBo;
    }

    /**
     * 查询会员购买单项商品/服务可使用的优惠券
     *
     * @param memberReqBo    会员信息
     * @param orderItemReqBo 订单明细项
     * @return 查询订单明细项(商品)可用优惠券结果
     */
    @Override
    public OrderItemAvailableCouponsRespBo queryMatchedCouponForPurchaseItem(MemberBo memberReqBo, OrderItemBo orderItemReqBo) {
        OrderItemAvailableCouponsRespBo couponAvailableRespBo = new OrderItemAvailableCouponsRespBo();
        couponAvailableRespBo.setSkuId(orderItemReqBo.getSkuId());
        couponAvailableRespBo.setSkuCategory(orderItemReqBo.getSkuCategory());
        couponAvailableRespBo.setSelectCouponUseList(getSelectCouponUseList(null, orderItemReqBo, memberReqBo,
                getSelectCouponReceiveInfoList(memberReqBo, null, orderItemReqBo)));

        return couponAvailableRespBo;
    }

    /**
     * 查询会员/用户下单可使用的优惠券
     *
     * @param memberReqBo 会员信息
     * @param orderReqBo  订单信息
     * @returns 查询订单可用优惠券结果
     */
    @Override
    public OrderAvailableCouponsRespBo queryMatchedCouponForOrder(MemberBo memberReqBo, OrderBo orderReqBo) {
        OrderAvailableCouponsRespBo orderAvailableCouponsRespBo = new OrderAvailableCouponsRespBo();
        orderAvailableCouponsRespBo.setOrderId(orderReqBo.getOrderId());
        orderAvailableCouponsRespBo.setOrderNo(orderReqBo.getOrderNo());
        orderAvailableCouponsRespBo.setSelectCouponUseList(getSelectCouponUseList(orderReqBo,
                null,
                memberReqBo,
                getSelectCouponReceiveInfoList(memberReqBo, orderReqBo, null)));

        return orderAvailableCouponsRespBo;
    }

    public Boolean verifyCouponActivityForOrder(Long activityId,
                                                MemberBo memberReqBo,
                                                OrderBo orderReqBo,
                                                OrderItemBo orderItemReqBo){

        List<PromotionRule> promotionRules = promotionRuleRepository.getPromotionRuleListByActivityId(activityId);

        //过滤无商品使用范围匹配条件的券
        if (orderItemReqBo != null && !CollectionUtils.isEmpty(promotionRules)) {
            promotionRules = promotionRules.stream().filter(p ->
                    !StringUtils.isBlank(p.getSkuCondition())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(promotionRules)) {
                return false;
            }
        }

        //遍历校验各领劵的活动规则和结算规则,有匹配的劵则加入返回列表
        try {
            if (!PromotionRuleService.verifyPromotionRule(memberReqBo, orderReqBo, orderItemReqBo,
                    promotionRules, PromotionRuleTypeEnum.USE.getCode())) {
                return false;
            }
        } catch (Exception e) {
            log.error(String.format("%s-活动ID:%s, 用户Id:%s", BusinessErrorCodeEnum.PROMOTION_ACTIVITY_RULE_MATCH_USER_FAILED.getMessage(),
                    activityId, memberReqBo.getUserId()));
            return false;

        }

        try {
            PromotionActivity activityInfo = activityRepository.getActivityById(activityId);
            if (activityInfo == null) {
                log.error(BusinessErrorCodeEnum.NOT_FOUND_PROMOTION_ACTIVITY.getMessage() + "优惠券ID:" + activityInfo.getCouponId());
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_PROMOTION_ACTIVITY);
            }

            //校验计算可用的劵时,取假如用户最低用1张劵的情况匹配是否满减条件
            if (orderItemReqBo != null) {
                if (orderItemReqBo.getTotalGoodsAmount() != null
                        && orderItemReqBo.getTotalGoodsAmount().compareTo(BigDecimal.ZERO) != 0) {
                    accountRuleService.validateAccountRuleMatch(activityInfo.getCouponId(), 1L,
                            orderItemReqBo.getTotalGoodsAmount());
                } else if (orderItemReqBo.getGoodsQuantity() != null
                        && orderItemReqBo.getGoodsQuantity() > 0L) {
                    accountRuleService.validateAccountRuleMatch(activityInfo.getCouponId(), 1L,
                            BigDecimal.valueOf(orderItemReqBo.getGoodsQuantity()));
                }
            } else if (orderReqBo != null) {
                if (orderReqBo.getTotalOrderAmount() != null
                        && orderReqBo.getTotalOrderAmount().compareTo(BigDecimal.ZERO) != 0) {
                    accountRuleService.validateAccountRuleMatch(activityInfo.getCouponId(), 1L,
                            orderReqBo.getTotalOrderAmount());
                }
            }
        } catch (BusinessException e) {
            log.error("查询订单或商品可用优惠券结果-校验匹配优惠券(ID:" + activityId + ")满减结算规则失败!");
            return false;
        }
        return true;

    }

    public List<CouponReceive> getMatchedCouponReceiveList(MemberBo memberReqBo,
                                                           OrderBo orderReqBo,
                                                           OrderItemBo orderItemReqBo,
                                                           Long couponId) {
        if (StringUtils.isBlank(memberReqBo.getUserId()) && StringUtils.isBlank(memberReqBo.getUserCode())) {
            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_USER_BASIC_INFO);
        }

        if (orderReqBo != null && StringUtils.isBlank(orderReqBo.getOrderId())) {
            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_ORDER_INFO);
        }

        if (orderItemReqBo != null && StringUtils.isBlank(orderItemReqBo.getSkuId())) {
            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_ORDER_ITEM_INFO);
        }

        List<CouponReceive> couponReceiveList = couponReceiveRepository.getUserCouponReceiveList(memberReqBo.getUserId(),
                memberReqBo.getUserCode(), couponId);

        //用户没有任何可用的优惠券领劵记录
        if (CollectionUtils.isEmpty(couponReceiveList)) {
            return Collections.emptyList();
        }

        Map<Long,List<CouponReceive>> couponReceiveByActivityMap =
                couponReceiveList.stream()
                        .filter(o->(o.getActivityId()!=null
                                &&o.getRemainQuantity()!=null
                                &&o.getRemainQuantity()>0L))
                        .collect(groupingBy(CouponReceive::getActivityId));

        List<CouponReceive> matchedCouponReceiveList = new ArrayList();
        couponReceiveByActivityMap.entrySet().stream()
            .filter( e-> verifyCouponActivityForOrder(e.getKey(),
                    memberReqBo,
                    orderReqBo,
                    orderItemReqBo))
            .map(e-> e.getValue())
            .collect(Collectors.toList()).stream()
                .forEach(o->matchedCouponReceiveList.addAll(o));

        return matchedCouponReceiveList;

    }

    /**
     * 查询订单或商品(各活动)可用优惠券结果集合
     *
     * @param memberReqBo 会员信息
     * @param orderReqBo 订单信息
     * @param orderItemReqBo 订单项信息
     * @return
     */
    @Override
    public List<SelectCouponReceiveInfoBo> getSelectCouponReceiveInfoList(MemberBo memberReqBo,
                                                                          OrderBo orderReqBo,
                                                                          OrderItemBo orderItemReqBo) {

        List<CouponReceive> couponReceiveList = getMatchedCouponReceiveList(memberReqBo,
                                                                orderReqBo,orderItemReqBo,null);
        if (!CollectionUtils.isEmpty(couponReceiveList)) {
            return couponReceiveList.stream()
                    .map(o -> {
                            //组装可选优惠券领劵记录列表
                            SelectCouponReceiveInfoBo selectCouponReceiveInfo = new SelectCouponReceiveInfoBo();
                            selectCouponReceiveInfo.setCouponReceiveId(o.getId());
                            selectCouponReceiveInfo.setCouponId(o.getCouponId());
                            selectCouponReceiveInfo.setCouponReceiveRemainQuantity(o.getRemainQuantity());
                            selectCouponReceiveInfo.setCouponAmount(o.getCouponAmount());
                            selectCouponReceiveInfo.setCouponEndDate(o.getEndDate());
                            selectCouponReceiveInfo.setCouponReceiveStatus(o.getStatus());

                            //查找优惠券模板权重设置值(由后台系统手动维护)
                            Coupon coupon = couponRepository.getCouponById(o.getCouponId());
                            selectCouponReceiveInfo.setCouponWeight(coupon.getWeight());
                            selectCouponReceiveInfo.setCouponName(coupon.getCouponName());
                            selectCouponReceiveInfo.setCouponDesc(coupon.getCouponDesc());
                            selectCouponReceiveInfo.setCouponIcon(coupon.getIcon());
                            return selectCouponReceiveInfo;
                        })
                    .collect(Collectors.toList());
        }

        return Collections.EMPTY_LIST;

    }

    /**
     * 获取订单或商品(各活动)优惠券总剩余数量
     *
     * @param memberReqBo 会员信息
     * @param orderReqBo 订单信息
     * @param orderItemReqBo 订单项信息
     * @param couponId 当前查询的优惠券ID
     * @return
     */
    @Override
    public Long getTotalCouponValidRemainQuantity(MemberBo memberReqBo, OrderBo orderReqBo,
                                                  OrderItemBo orderItemReqBo, final Long couponId) {

        List<CouponReceive> couponReceiveList = getMatchedCouponReceiveList(memberReqBo,
                                                        orderReqBo, orderItemReqBo,couponId);

        if (!CollectionUtils.isEmpty(couponReceiveList)){
            return couponReceiveList.stream()
                    .mapToLong(CouponReceive::getReceiveQuantity)
                    .sum();
        }
        return 0L;

    }

    /**
     * 将用户优惠券可选列表排序返回
     *
     * @param orderReqBo                  订单信息
     * @param orderItemReqBo              订单明细项信息
     * @param memberReqBo                 用户信息
     * @param selectCouponReceiveInfoList 可选的用户优惠券领劵记录列表
     * @return 策略排序后的用户优惠券可选列表
     */
    @Override
    public List<SelectCouponUseBo> getSelectCouponUseList(OrderBo orderReqBo,
                                                          OrderItemBo orderItemReqBo,
                                                          MemberBo memberReqBo,
                                                          List<SelectCouponReceiveInfoBo> selectCouponReceiveInfoList) {
        if (CollectionUtils.isEmpty(selectCouponReceiveInfoList)) {
//            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_AVAILABLE_COUPON);
            return null;
        }

        Map<Long, List<SelectCouponReceiveInfoBo>> groupCouponMap;

        //按优惠券预设定的权重从小到大排序
        selectCouponReceiveInfoList.sort(Comparator.comparingInt(w -> w.getCouponWeight()));
        groupCouponMap = selectCouponReceiveInfoList.stream()
                .collect(Collectors.groupingBy(SelectCouponReceiveInfoBo::getCouponId));

        List<SelectCouponReceiveInfoBo> selCpReceiveList;
        List<SelectCouponUseBo> resultSelectCouponList = new ArrayList();
        SelectCouponUseBo selectCouponUseInfo;

        //同一种优惠券按截止日期由近及远排序
        for (Map.Entry<Long, List<SelectCouponReceiveInfoBo>> entry : groupCouponMap.entrySet()) {
            if (entry.getKey() > 0L) {
                selCpReceiveList = entry.getValue();
                if (!CollectionUtils.isEmpty(selCpReceiveList)) {
                    selCpReceiveList.sort(Comparator.comparing(c -> c.getCouponEndDate(), Comparator.nullsLast(Date::compareTo)));

                    selectCouponUseInfo = new SelectCouponUseBo();
                    selectCouponUseInfo.setCouponId(entry.getKey());
                    selectCouponUseInfo.setCouponAmount(selCpReceiveList.get(0).getCouponAmount());
                    selectCouponUseInfo.setSelectCouponReceiveInfoList(selCpReceiveList);

                    Coupon coupon = couponRepository.getCouponById(entry.getKey());
                    if (coupon != null) {
                        selectCouponUseInfo.setCouponName(coupon.getCouponName());
                        selectCouponUseInfo.setCouponDesc(coupon.getCouponDesc());
                        selectCouponUseInfo.setCouponIcon(coupon.getIcon());
                        selectCouponUseInfo.setCouponWeight(coupon.getWeight());

                        //汇总优惠券各活动领劵剩余数量
                        Long resultCouponQuantity =  couponSummaryService.getCouponAvailableQuantityForOrder(
                                coupon.getId(),
                                orderReqBo,
                                orderItemReqBo,
                                memberReqBo
                        );

                        if (resultCouponQuantity > 0L) {
                            selectCouponUseInfo.setCouponTotalRemainQuantity(resultCouponQuantity);
                        }
                    }

                    resultSelectCouponList.add(selectCouponUseInfo);
                }
            }
        }

        return resultSelectCouponList;
    }

    /**
     * 将可用优惠券明细列表按优先使用策略排序
     *
     * @param availableCouponList 可用的用户优惠券明细记录列表
     * @return 策略排序后的可用优惠券明细记录列表
     */
    @Override
    public List<AvailableCouponDetailsBo> sortAvailableMatchedCoupons(List<AvailableCouponDetailsBo> availableCouponList) {
        if (CollectionUtils.isEmpty(availableCouponList)) {
            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_AVAILABLE_COUPON);
        }

        Map<Long, List<AvailableCouponDetailsBo>> groupCouponMap;

        //按优惠券预设定的权重从小到大排序
        availableCouponList.sort(Comparator.comparingInt(w -> w.getCouponWeight()));
        groupCouponMap = availableCouponList.stream()
                .collect(Collectors.groupingBy(AvailableCouponDetailsBo::getCouponId));

        List<AvailableCouponDetailsBo> originDetailsBoList;
        List<AvailableCouponDetailsBo> resultDetailBoList = new ArrayList();

        //同一种优惠券按截止日期由近及远排序
        for (Map.Entry<Long, List<AvailableCouponDetailsBo>> entry : groupCouponMap.entrySet()) {
            if (entry.getKey() > 0L) {
                originDetailsBoList = entry.getValue();
                originDetailsBoList.sort(Comparator.comparing(c -> c.getCouponEndDate()));
                resultDetailBoList.addAll(originDetailsBoList);
            }
        }

        return resultDetailBoList;
    }

    /**
     * 用券接口
     * 校验用券信息,每一种优惠券按使用策略顺序更新领券记录状态,并更新用户中心优惠券信息
     *
     * @param orderReqBo 包含使用优惠券的订单信息
     * @return 实际使用的优惠券
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CouponConsumeBo> consumeCoupon(MemberBo memberReqBo, OrderBo orderReqBo) throws Exception {
        Gson gson = new Gson();
        List<CouponConsumeBo> resultUserCouponConsumeList = new ArrayList<>();

        //先校验当前用户下单和商品所选择的的优惠券是否可用
        Map<CouponReduceInfoBo, Map<Long, BigDecimal>> couponReduceAmountMap = new HashMap(16);
        if (!validateCouponAvailable(memberReqBo, orderReqBo, true, couponReduceAmountMap)) {
            throw new BusinessException(BusinessErrorCodeEnum.VERIFY_COUPON_AVAILABLE_RULES_CHECK_FAILED);
        }

        UserCouponConsumeProcessBo userCouponConsumeProcessBo = new UserCouponConsumeProcessBo();
        userCouponConsumeProcessBo.setUserId(memberReqBo.getUserId());
        userCouponConsumeProcessBo.setUserCode(memberReqBo.getUserCode());

        try {
            //处理订单层次的各优惠券用劵
            if (!CollectionUtils.isEmpty(orderReqBo.getUseCoupons())) {
                userCouponConsumeProcessBo.setOrderId(orderReqBo.getOrderId());
                userCouponConsumeProcessBo.setOrderNo(orderReqBo.getOrderNo());
                userCouponConsumeProcessBo.setOriginalAmount(orderReqBo.getOriginOrderAmount());
                userCouponConsumeProcessBo.setCouponReduceAmountList(
                        getReducedPayAmountCouponsList(couponReduceAmountMap, orderReqBo.getOrderId(), null));
                processConsumingCoupons(userCouponConsumeProcessBo, orderReqBo.getUseCoupons(), resultUserCouponConsumeList);
            }

            //处理订单明细项/商品层次的各优惠券用劵
            if (!CollectionUtils.isEmpty(orderReqBo.getOrderItemList())) {
                for (OrderItemBo orderItem : orderReqBo.getOrderItemList()) {
                    if (!CollectionUtils.isEmpty(orderItem.getUseCoupons())) {
                        userCouponConsumeProcessBo.setOrderItemId(orderItem.getOrderItemId());
                        userCouponConsumeProcessBo.setOriginalAmount(orderItem.getTotalGoodsAmount());
                        userCouponConsumeProcessBo.setGoodsQuantity(orderItem.getGoodsQuantity());
                        userCouponConsumeProcessBo.setCouponReduceAmountList(
                                getReducedPayAmountCouponsList(couponReduceAmountMap, null, orderItem.getOrderItemId()));
                        processConsumingCoupons(userCouponConsumeProcessBo, orderItem.getUseCoupons(), resultUserCouponConsumeList);
                    }
                }
            }
        } catch (Exception e) {
            log.error(String.format("%s请求用户:%s,请求订单:%s", BusinessErrorCodeEnum.PROCESS_USER_CONSUMING_COUPON_FAILED.getMessage(),
                    gson.toJson(memberReqBo), gson.toJson(orderReqBo)), e);
            throw new BusinessException(BusinessErrorCodeEnum.PROCESS_USER_CONSUMING_COUPON_FAILED);
        }

        return resultUserCouponConsumeList;
    }

    /**
     * 处理当前使用的优惠券相关操作
     *
     * @param userCouponConsumeProcessBo  用户使用优惠券处理操作对象
     * @param userConsumingCouponList     用户正在使用的优惠券数据列表
     * @param resultUserCouponConsumeList 使用优惠券返回结果列表
     * @throws Exception
     */
    private void processConsumingCoupons(UserCouponConsumeProcessBo userCouponConsumeProcessBo,
                                         List<UseCouponBo> userConsumingCouponList,
                                         List<CouponConsumeBo> resultUserCouponConsumeList) throws Exception {
        Gson gson = new Gson();
        List<CouponConsumeDetailBo> couponConsumeDetailList;

        for (UseCouponBo useCouponBo : userConsumingCouponList) {
            //校验优惠券信息是否存在
            Coupon coupon = couponRepository.getCouponById(useCouponBo.getCouponId());
            if (coupon == null) {
                log.error(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO.getMessage() + "优惠券ID:" + useCouponBo.getCouponId());
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO.getCode(),
                        BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO.getMessage() + "优惠券ID:" + useCouponBo.getCouponId());
            }

            //校验当为一口价劵种时,商品总数目是否少于优惠券使用数量
            AccountRule accountRule = accountRuleRepository.getAccountRuleById(coupon.getAccountRuleId());
            if (accountRule == null) {
                log.error(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE.getMessage() + "结算规则ID:" + coupon.getAccountRuleId());
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE.getCode(),
                        BusinessErrorCodeEnum.NOT_FOUND_COUPON_ACCOUNT_RULE.getMessage() + "结算规则ID:" + coupon.getAccountRuleId());
            }

            if (accountRule.getRewardType().equals(AccountRuleRewardTypeEnum.MONEY.getCode())
                    && accountRule.getThresholdType().equals(AccountRuleThresholdTypeEnum.PREFERENTIAL.getCode())) {
                if (userCouponConsumeProcessBo.getGoodsQuantity() < useCouponBo.getCouponQuantity()) {
                    //自动修正为和商品数量相同
                    useCouponBo.setCouponQuantity(userCouponConsumeProcessBo.getGoodsQuantity());
                }
            }

            //查找优惠券汇总记录,用于校验和后续数据回写更新优惠券使用数量和金额
            CouponSummary couponSummaryInfo = couponSummaryRepository.getUserCouponSummaryInfo(userCouponConsumeProcessBo.getUserId(),
                    useCouponBo.getCouponId());
            if (couponSummaryInfo == null) {
                throw new BusinessException(BusinessErrorCodeEnum.EMPTY_COUPON_SUMMARY_INFO);
            }

            //校验用户的优惠券汇总剩余可用数量是否满足
            if (useCouponBo.getCouponQuantity() > couponSummaryInfo.getCouponQuantity()) {
                throw new BusinessException(BusinessErrorCodeEnum.COUPON_SUMMARY_QUANTITY_NOT_AVAILABLE.getMessage()
                        + "优惠券ID:" + useCouponBo.getCouponId());
            }

            //筛选出此种优惠券的总计实付金额,记录到用劵主表中
            if (!userCouponConsumeProcessBo.getCouponReduceAmountList().isEmpty()) {
                List<BigDecimal> reduceAmtList = userCouponConsumeProcessBo.getCouponReduceAmountList().stream()
                        .filter(c -> c.getCouponId().equals(useCouponBo.getCouponId()))
                        .collect(Collectors.toList())
                        .stream().map(m -> m.getCouponReduceAmount()).collect(Collectors.toList());
                userCouponConsumeProcessBo.setFinalPayAmount(BigDecimalUtil.subtract(
                        userCouponConsumeProcessBo.getOriginalAmount().doubleValue(),
                        reduceAmtList.stream().reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue()));
            }

            //查找到此用户可用的领券清单数据(按优惠券截止使用日期由近及远排序)
            List<CouponReceive> couponReceiveList = couponReceiveRepository.getUserCouponReceiveList(userCouponConsumeProcessBo.getUserId(),
                    userCouponConsumeProcessBo.getUserCode(), useCouponBo.getCouponId());

            if (!CollectionUtils.isEmpty(couponReceiveList)) {
                //临时变量,存储计算当前仍需用到领劵的数目,初始为用户请求总数目
                Long tmpUsedRemainCount = useCouponBo.getCouponQuantity();
                couponConsumeDetailList = new ArrayList<>();

                CouponConsumeBo couponConsumeProcessResultBo = new CouponConsumeBo();
                CouponConsume couponConsumeInfo;
                for (CouponReceive useCpReceiveInfo : couponReceiveList) {
                    //若当前是无剩余数量的已用完的领劵历史记录,则忽略
                    if (useCpReceiveInfo.getRemainQuantity() == null || useCpReceiveInfo.getRemainQuantity().equals(0L)) {
                        continue;
                    }

                    couponConsumeInfo = new CouponConsume();
                    BeanUtils.copyProperties(userCouponConsumeProcessBo, couponConsumeInfo);
                    couponConsumeInfo.setCouponId(useCouponBo.getCouponId());
                    couponConsumeInfo.setStatus(CouponConsumeStatusEnum.USED_WITH_PAYED.getCode());
                    couponConsumeInfo.setCouponAmount(useCpReceiveInfo.getCouponAmount());

                    CouponConsumeDetail couponConsumeDetail;

                    //若请求用劵数量正好匹配仅有一个活动领劵数量的记录,直接使用
                    boolean isOneCouponAvailable = useCpReceiveInfo.getRemainQuantity() >= useCouponBo.getCouponQuantity()
                            && tmpUsedRemainCount.equals(useCouponBo.getCouponQuantity());
                    boolean isLastUseReceiveCoupon = false;
                    Long recvCouponRemainQuantity = useCpReceiveInfo.getRemainQuantity();

                    if (isOneCouponAvailable) {
                        //更新领劵记录为劵已使用状态
                        CouponReceiveStatusEnum couponReceiveStatus = useCpReceiveInfo.getRemainQuantity().equals(useCouponBo.getCouponQuantity()) ?
                                CouponReceiveStatusEnum.USED : CouponReceiveStatusEnum.PARTIAL_USED;
                        useCpReceiveInfo.setStatus(couponReceiveStatus.getCode());

                        //保存优惠券用劵主记录和明细记录
                        couponConsumeInfo.setConsumeQuantity(useCouponBo.getCouponQuantity());
                        couponConsumeDetail = saveConsumeRecord(couponConsumeInfo, useCouponBo.getCouponQuantity(), useCpReceiveInfo);
                    } else {
                        //若此领劵记录的劵剩余数量不够,从多个领劵记录拆分到用劵明细
                        if (tmpUsedRemainCount >= useCpReceiveInfo.getRemainQuantity()) {
                            tmpUsedRemainCount -= useCpReceiveInfo.getRemainQuantity();

                            //更新领劵记录为劵已使用状态
                            useCpReceiveInfo.setStatus(CouponReceiveStatusEnum.USED.getCode());

                            long currentRemainUseQuantity = useCpReceiveInfo.getRemainQuantity();
                            couponConsumeInfo.setConsumeQuantity(useCpReceiveInfo.getRemainQuantity());
                            couponConsumeDetail = saveConsumeRecord(couponConsumeInfo, currentRemainUseQuantity, useCpReceiveInfo);
                        } else {
                            //若未使用完毕,更新领劵记录为设为劵部分使用状态
                            log.info("用户优惠券未使用完毕,更新领劵记录为劵部分使用状态-领劵记录ID:" + useCpReceiveInfo.getId());
                            useCpReceiveInfo.setStatus(CouponReceiveStatusEnum.PARTIAL_USED.getCode());
                            couponConsumeInfo.setConsumeQuantity(tmpUsedRemainCount);
                            couponConsumeDetail = saveConsumeRecord(couponConsumeInfo, tmpUsedRemainCount, useCpReceiveInfo);
                            isLastUseReceiveCoupon = true;
                        }
                    }

                    //加入到返回结果数据列表
                    BeanUtils.copyProperties(couponConsumeInfo, couponConsumeProcessResultBo);

                    //更新优惠券汇总记录
                    if (couponConsumeDetail != null) {
                        CouponConsumeDetailBo couponConsumeDetailRespBo = new CouponConsumeDetailBo();
                        BeanUtils.copyProperties(couponConsumeDetail, couponConsumeDetailRespBo);
                        couponConsumeDetailList.add(couponConsumeDetailRespBo);
                    }

                    if (isOneCouponAvailable
                            || (isLastUseReceiveCoupon && tmpUsedRemainCount <= recvCouponRemainQuantity)
                            || tmpUsedRemainCount == 0) {
                        break;
                    }
                }

                //若用劵成功,则更新优惠券汇总表记录(使用数量和金额)
                couponSummaryInfo.setConsumedQuantity((couponSummaryInfo.getConsumedQuantity() != null
                        ? couponSummaryInfo.getConsumedQuantity() : 0)
                        + useCouponBo.getCouponQuantity());
                couponSummaryInfo.setCouponQuantity((couponSummaryInfo.getCouponQuantity() != null
                        ? couponSummaryInfo.getCouponQuantity() : 0)
                        - useCouponBo.getCouponQuantity());

                BigDecimal curRewardAmt = couponSummaryInfo.getRewardAmount() == null
                        ? BigDecimal.ZERO : couponSummaryInfo.getRewardAmount();
                BigDecimal originAmt = userCouponConsumeProcessBo.getOriginalAmount() == null
                        ? BigDecimal.ZERO : userCouponConsumeProcessBo.getOriginalAmount();
                couponSummaryInfo.setRewardAmount(curRewardAmt.add((originAmt.subtract(userCouponConsumeProcessBo.getFinalPayAmount()))));
                couponSummaryRepository.updateById(couponSummaryInfo);
                log.info("保存优惠券汇总表记录成功!优惠券汇总记录:" + gson.toJson(couponSummaryInfo));

                //返回数据
                couponConsumeProcessResultBo.setCouponConsumeDetailList(couponConsumeDetailList);
                resultUserCouponConsumeList.add(couponConsumeProcessResultBo);
            }
        }
    }

    /**
     * 保存优惠券用劵明细记录
     *
     * @param couponConsumeInfo 用劵主信息对象
     * @param useCouponQuantity 用户请求本次使用劵数量
     * @param useCpReceiveInfo  当前优惠券模板对应的领劵主记录
     */
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    private CouponConsumeDetail saveConsumeRecord(CouponConsume couponConsumeInfo, Long useCouponQuantity,
                                                  CouponReceive useCpReceiveInfo) throws Exception {
        Gson gson = new Gson();

        //保存商品层次优惠券用劵主记录
        couponConsumeRepository.insertOrUpdate(couponConsumeInfo);
        log.info("保存优惠券用劵主记录成功!用劵主记录:" + gson.toJson(couponConsumeInfo));

        CouponConsumeDetail consumeDetailInfo = new CouponConsumeDetail();
        if (couponConsumeInfo.getId() > 0) {
            //保存优惠券用劵明细记录
            consumeDetailInfo.setActivityId(useCpReceiveInfo.getActivityId());
            consumeDetailInfo.setCouponConsumeId(couponConsumeInfo.getId());
            consumeDetailInfo.setCouponReceiveId(useCpReceiveInfo.getId());

            //查找领劵明细列表记录(劵号等)
            List<CouponReceiveDetail> couponReceiveDetailList = couponReceiveDetailRepository.getCouponReceiveDetailListByQuantity(
                    useCpReceiveInfo.getId(), useCouponQuantity);

            //当优惠券为非积分形式时
            if (!CollectionUtils.isEmpty(couponReceiveDetailList)) {
                StringBuilder consumeDetailInfoIdsStr = new StringBuilder();

                for (CouponReceiveDetail receiveDetail : couponReceiveDetailList) {
                    //更新该劵为已使用
                    receiveDetail.setStatus(CouponReceiveStatusEnum.USED.getCode());
                    couponReceiveDetailRepository.updateById(receiveDetail);

                    //拼接领劵记录明细ID集合,记录到用劵明细记录
                    consumeDetailInfoIdsStr.append(receiveDetail.getId().toString() + CommonConstants.TAG_COMMA);
                }

                consumeDetailInfo.setCouponReceiveDetailIds(StringUtils.removeEndIgnoreCase(
                        consumeDetailInfoIdsStr.toString(), CommonConstants.TAG_COMMA));
            } else {
                //当优惠券为积分形式时,无领劵详情记录
                if (couponConsumeInfo.getCouponId() > 0L) {
                    Coupon coupon = couponRepository.getCouponById(couponConsumeInfo.getCouponId());
                    if (coupon != null && coupon.getCouponType().equals(CouponTypeEnum.POINTS.getCode())) {
                        consumeDetailInfo.setCouponReceiveDetailIds(null);
                    }
                }
            }

            consumeDetailInfo.setConsumeQuantity(useCouponQuantity);
            couponConsumeDetailRepository.insert(consumeDetailInfo);
            log.info("保存优惠券用劵明细记录成功!用劵明细记录:" + gson.toJson(consumeDetailInfo));

            //存储完用劵主子记录之后,更新相应的领劵信息
            useCpReceiveInfo.setRemainQuantity(useCpReceiveInfo.getRemainQuantity() - useCouponQuantity);
            couponReceiveRepository.updateById(useCpReceiveInfo);
            log.info("更新用户用劵相应的领劵信息成功!领劵记录:" + gson.toJson(useCpReceiveInfo));
        } else {
            log.error(BusinessErrorCodeEnum.COUPON_CONSUME_RECORD_STORE_EXCEPTION.getMessage());
            throw new BusinessException(BusinessErrorCodeEnum.COUPON_CONSUME_RECORD_STORE_EXCEPTION);
        }

        return consumeDetailInfo;
    }

    /**
     * 获取订单或商品的实付劵列表
     *
     * @param couponReduceAmountMap 订单或商品的各类优惠券扣减数据集合
     * @param orderId               订单ID
     * @param orderItemId           订单项/商品ID
     * @return
     */
    private List<CouponsReduceAmountBo> getReducedPayAmountCouponsList(Map<CouponReduceInfoBo, Map<Long, BigDecimal>> couponReduceAmountMap,
                                                                       final String orderId, final String orderItemId) {
        //组装每种优惠券计算后的实付金额集合,便于后续存储到用劵记录
        if (couponReduceAmountMap.isEmpty()) {
            throw new BusinessException(BusinessErrorCodeEnum.VERIFY_COUPON_AVAILABLE_RULES_CHECK_FAILED);
        }

        List<CouponsReduceAmountBo> couponReduceAmountList = new ArrayList();
        Predicate<CouponReduceInfoBo> filterCondition = null;

        if (StringUtils.isNotBlank(orderId)) {
            filterCondition = (t) -> null != t && orderId.equals(t.getOrderId());
        } else if (StringUtils.isNotBlank(orderItemId)) {
            filterCondition = (t) -> null != t && orderItemId.equals(t.getOrderItemId());
        }

        //在Map集合中查找到当前商品或订单
        final CouponReduceInfoBo couponReduceInfoBo = couponReduceAmountMap.keySet().stream()
                                                      .filter(filterCondition).findFirst().get();

        if(couponReduceInfoBo != null) {
            List<Map<Long, BigDecimal>> cpReduceAmtMapList = couponReduceAmountMap.entrySet().stream()
                    .filter(map -> map.getKey().equals(couponReduceInfoBo))
                    .map(map -> map.getValue()).collect(Collectors.toList());

            cpReduceAmtMapList.forEach(r -> {
                r.forEach((cpId, cpReduceAmt) -> {
                    CouponsReduceAmountBo couponsReduceAmountBo = new CouponsReduceAmountBo();
                    couponsReduceAmountBo.setCouponId(cpId);
                    couponsReduceAmountBo.setCouponReduceAmount(cpReduceAmt);
                    couponReduceAmountList.add(couponsReduceAmountBo);
                });
            });
        }

        return couponReduceAmountList;
    }

    /**
     * 校验当前用户下单和商品/服务优惠券是否可用
     * 分别依次匹配结算规则和活动规则
     *
     * @param memberReqBo           用户信息对象
     * @param orderReqBo            包含使用优惠券的订单信息对象
     * @param isSettleCheck         是否结算校验
     * @param couponReduceAmountMap 订单或商品的各类优惠券扣减数据集合
     * @return
     */
    @Override
    public Boolean validateCouponAvailable(MemberBo memberReqBo, OrderBo orderReqBo,
                                           Boolean isSettleCheck,
                                           Map<CouponReduceInfoBo, Map<Long, BigDecimal>> couponReduceAmountMap) throws Exception {
        Gson gson = new Gson();
        String requestStr = String.format("用户信息:{},订单信息:{}.", gson.toJson(memberReqBo), gson.toJson(orderReqBo));
        log.info("校验当前用户下单和商品/服务优惠券是否可用--{}", requestStr);

        //先对用户订单的优惠券活动规则校验
        if (memberReqBo != null && StringUtils.isNotEmpty(memberReqBo.getUserId()) && orderReqBo != null) {
            try {
                List<UseCouponBo> actualUseCouponList;
                if (!CollectionUtils.isEmpty(orderReqBo.getUseCoupons())) {
                    log.info("-----开始校验优惠券可用性-匹配用户订单优惠券活动规则-----");
                    if (!validatePromotionRuleForUsedCoupons(orderReqBo.getUseCoupons(), memberReqBo,
                            orderReqBo, null)) {
                        log.error(BusinessErrorCodeEnum.VERIFY_COUPON_AVAILABLE_RULES_CHECK_FAILED.getMessage());
                        return false;
                    }
                    log.info("匹配用户订单-" + BusinessErrorCodeEnum.PROMOTION_ACTIVITY_RULE_CHECK_SUCCEED.getMessage());

                    //校验匹配商品层次优惠券满减结算规则(金额和数量),计算返回各劵种应扣金额
                    log.info("-----开始校验优惠券可用性-匹配用户订单优惠券结算规则-----");
                    final Map<Long, Long> usingOrderCouponsMap = new HashMap(16);
                    //按扣减类型排序用户请求劵列表
                    actualUseCouponList = couponService.sortUserRequestCouponsByRewardType(orderReqBo.getUseCoupons());
                    actualUseCouponList.forEach(c -> {
                        usingOrderCouponsMap.put(c.getCouponId(), c.getCouponQuantity());
                    });

                    accountRuleService.checkAccountRuleMatchResult(orderReqBo, null,
                                        usingOrderCouponsMap, isSettleCheck, couponReduceAmountMap);
                    log.info("匹配并计算应扣金额-订单-" + BusinessErrorCodeEnum.VERIFY_ACCOUNT_RULES_CHECK_SUCCEED.getMessage());
                }

                //再对订单每个商品/服务进行活动规则校验
                log.info("-----开始校验优惠券可用性-匹配订单中每个商品/服务活动规则-----");
                if (!CollectionUtils.isEmpty(orderReqBo.getOrderItemList())) {
                    for (OrderItemBo orderItemBo : orderReqBo.getOrderItemList()) {
                        if (!CollectionUtils.isEmpty(orderItemBo.getUseCoupons())) {
                            //按扣减类型排序用户请求劵列表
                            actualUseCouponList = couponService.sortUserRequestCouponsByRewardType(orderItemBo.getUseCoupons());
                            if (!validatePromotionRuleForUsedCoupons(actualUseCouponList, memberReqBo,
                                    orderReqBo, orderItemBo)) {
                                return false;
                            }
                            log.info("匹配并计算应扣金额-订单项/商品-" + BusinessErrorCodeEnum.PROMOTION_ACTIVITY_RULE_CHECK_SUCCEED.getMessage());

                            //校验匹配商品层次优惠券满减结算规则(金额和数量),计算返回各劵种应扣金额
                            final Map<Long, Long> usingOrderItemCouponsMap = new HashMap(16);
                            orderItemBo.getUseCoupons().forEach(c -> {
                                usingOrderItemCouponsMap.put(c.getCouponId(), c.getCouponQuantity());
                            });

                            accountRuleService.checkAccountRuleMatchResult(null, orderItemBo,
                                    usingOrderItemCouponsMap, isSettleCheck, couponReduceAmountMap);
                            log.info("匹配并计算应扣金额-订单项/商品-" + BusinessErrorCodeEnum.VERIFY_ACCOUNT_RULES_CHECK_SUCCEED.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                log.error(BusinessErrorCodeEnum.VERIFY_COUPON_AVAILABLE_RULES_CHECK_FAILED.getMessage(), e);
                throw e;
            }
        }

        return true;
    }

    /**
     * 校验待用的各优惠券是否符合促销规则配置
     *
     * @param useCoupons     正在使用的优惠券列表
     * @param memberReqBo    用户信息
     * @param orderBo        订单信息
     * @param orderItemReqBo 订单项信息
     * @return
     */
    private Boolean validatePromotionRuleForUsedCoupons(List<UseCouponBo> useCoupons, MemberBo
            memberReqBo, OrderBo orderBo, OrderItemBo orderItemReqBo) throws Exception {
        if (!CollectionUtils.isEmpty(useCoupons)) {
            List<Long> useCouponIdList = useCoupons.stream().map(x -> x.getCouponId()).collect(Collectors.toList());
            List<Long> activityIdList = activityRepository.getActivityIdListByCouponIdList(useCouponIdList);
            if (!CollectionUtils.isEmpty(activityIdList)) {
                Gson gson = new Gson();
                List<PromotionRule> promotionRules;

                //依次校验所有活动规则是否匹配,发现任意不匹配则整单返回匹配失败
                for (Long activityId : activityIdList) {
                    PromotionActivity activityInfo;

                    //校验活动截止日期和优惠券截止日期
                    activityInfo = activityRepository.getActivityById(activityId);
                    if (activityInfo == null) {
                        log.error(BusinessErrorCodeEnum.NOT_FOUND_PROMOTION_ACTIVITY.getMessage() + "优惠券ID:" + activityInfo.getCouponId());
                        throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_PROMOTION_ACTIVITY);
                    }

                    if (!couponReceiveRepository.checkUserCouponValidation(memberReqBo.getUserId(), activityInfo.getCouponId())) {
                        throw new BusinessException(BusinessErrorCodeEnum.USER_COUPON_INVALID);
                    }

                    //校验匹配活动规则:一个活动下仅对应一个用劵规则
                    promotionRules = promotionRuleRepository.getPromotionRuleListByActivityId(activityId);

                    if (memberReqBo != null) {
                        if (!PromotionRuleService.verifyPromotionRule(memberReqBo, orderBo, orderItemReqBo,
                                promotionRules, PromotionRuleTypeEnum.USE.getCode())) {
                            String errMsg = String.format("%s 活动信息:%s, 用户信息:%s, 订单明细项信息:%s",
                                    BusinessErrorCodeEnum.VERIFY_COUPON_MATCH_PROMOTION_RULES_FAILED.getMessage(),
                                    gson.toJson(activityInfo), gson.toJson(memberReqBo), gson.toJson(orderItemReqBo));
                            log.error(errMsg);
                            throw new BusinessException(BusinessErrorCodeEnum.VERIFY_COUPON_MATCH_PROMOTION_RULES_FAILED);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }
}

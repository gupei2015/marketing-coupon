package lk.project.marketing.service.impl;


import lk.project.marketing.base.bo.CouponReqBo;
import lk.project.marketing.base.bo.CouponSortBo;
import lk.project.marketing.base.bo.QueryCouponReqBo;
import lk.project.marketing.base.bo.UseCouponBo;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.base.enums.AccountRuleRewardTypeEnum;
import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.repository.AccountRuleRepository;
import lk.project.marketing.repository.CouponRepository;
import lk.project.marketing.service.CouponService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by gupei on 2018/09/11.
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponRepository couponRepository;

    @Autowired
    AccountRuleRepository accountRuleRepository;

    @Override
    public Boolean createCoupon(CouponReqBo couponRequest) {

        //todo; 解析获取优惠券基础信息、核算规则、活动及规则并保存
        //baseMapper.insert(coupon);
        return true;

    }

    @Override
    public Boolean updateCoupon(CouponReqBo couponRequest) {
        return false;
    }

    @Override
    public List<Coupon> queryCoupon(QueryCouponReqBo queryCouponReq) {
        return null;
    }

    /**
     * 按扣减类型排序用户请求劵列表
     * @param originUserRequestCouponList 原始用户请求劵列表
     * @return 已排序的用户请求劵列表
     */
    @Override
    public List<UseCouponBo> sortUserRequestCouponsByRewardType(List<UseCouponBo> originUserRequestCouponList){
        if(originUserRequestCouponList.isEmpty()){
            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_COUPON_REDUCE_AMOUNT_MAP);
        }

        List<UseCouponBo> resultSortedCouponList = new ArrayList();
        List<CouponSortBo> couponSortBoList = new ArrayList();
        originUserRequestCouponList.forEach(c -> {
            //获取优惠券结算规则
            Coupon coupon = couponRepository.getCouponById(c.getCouponId());
            if(coupon != null){
                AccountRule accountRule = coupon.getAccountRule();
                CouponSortBo couponSortBo = new CouponSortBo();
                couponSortBo.setCouponId(c.getCouponId());
                couponSortBo.setCouponQuantity(c.getCouponQuantity());
                couponSortBo.setRewardTypeReduceSeqNo(AccountRuleRewardTypeEnum.getValueByCode(
                                                        accountRule.getRewardType()).getReduceSeqNo());
                couponSortBoList.add(couponSortBo);
            }
        });

        couponSortBoList.sort(Comparator.comparingInt(c -> c.getRewardTypeReduceSeqNo()));
        couponSortBoList.forEach(s -> {
            UseCouponBo useCouponBo = new UseCouponBo();
            BeanUtils.copyProperties(s, useCouponBo);
            resultSortedCouponList.add(useCouponBo);
        });

        return resultSortedCouponList;
    }
}

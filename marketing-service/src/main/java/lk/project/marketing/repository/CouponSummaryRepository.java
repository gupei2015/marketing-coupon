package lk.project.marketing.repository;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import lk.project.marketing.base.bo.CouponHistoryInfoBo;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.entity.CouponSummary;
import lk.project.marketing.mapper.CouponSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class CouponSummaryRepository extends ServiceImpl<CouponSummaryMapper, CouponSummary> {
    private final static Logger log = LoggerFactory.getLogger(CouponSummaryRepository.class);

    @Autowired
    CouponSummaryMapper couponSummaryMapper;

    /**
     * 根据用户信息查找优惠券汇总记录
     * @param userId
     * @param couponId
     * @return
     */
    public CouponSummary getUserCouponSummaryInfo(String userId, Long couponId){
        CouponSummary couponSummaryInfo = new CouponSummary();
        couponSummaryInfo.setUserId(userId);
        couponSummaryInfo.setCouponId(couponId);
        couponSummaryInfo.setDelete(CommonConstants.DEFAULT_VALID);
        Wrapper wrapperSummary = new EntityWrapper(couponSummaryInfo);
        couponSummaryInfo = selectOne(wrapperSummary);

        return couponSummaryInfo;
    }

    /**
     * 查询用户订单的用劵历史记录列表
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return
     */
    public List<CouponHistoryInfoBo> getCouponHistoryDetails(String userId, String orderId){
        return couponSummaryMapper.getCouponHistoryDetailsByUserOrder(userId, orderId);
    }

    /**
     * 更新优惠券实时汇总数据
     *
     * @param couponSummaryInfo       更新的优惠券对象
     * @param receiveQuantity         当前领取数量
     * @param consumeQuantity         当前使用数量
     * @param couponAvailableQuantity 优惠券可用数量
     * @param rewardAmount            当前扣减/优惠总金额
     * @return
     */
    public Boolean updateCouponUsageSummary(CouponSummary couponSummaryInfo, Long receiveQuantity, Long consumeQuantity,
                                            Long couponAvailableQuantity, BigDecimal rewardAmount) throws Exception {
        Gson gson = new Gson();

        if (couponSummaryInfo == null) {
            throw new BusinessException(BusinessErrorCodeEnum.EMPTY_COUPON_SUMMARY_INFO);
        }

        if (receiveQuantity != null && receiveQuantity > 0L) {
            couponSummaryInfo.setReceivedQuantity(couponSummaryInfo.getReceivedQuantity() + receiveQuantity);
        }

        if (consumeQuantity != null && consumeQuantity > 0L) {
            couponSummaryInfo.setConsumedQuantity(couponSummaryInfo.getConsumedQuantity() + consumeQuantity);
        }

        if (consumeQuantity != null && consumeQuantity > 0L) {
            couponSummaryInfo.setCouponQuantity(couponSummaryInfo.getCouponQuantity() + couponAvailableQuantity);
        }

        if (rewardAmount != null && rewardAmount.compareTo(BigDecimal.ZERO) != 0) {
            couponSummaryInfo.setRewardAmount(couponSummaryInfo.getRewardAmount().add(rewardAmount));
        }

        log.info("更新优惠券汇总记录:" + gson.toJson(couponSummaryInfo));
        return updateById(couponSummaryInfo);
    }

    /**
     * 根据指定条件查询优惠券汇总记录
     * @return
     */
    public CouponSummary getCouponSummary(CouponSummary couponSummary){
        Wrapper<CouponSummary> wrapperObj = new EntityWrapper<>(couponSummary);
        return selectOne(wrapperObj);
    }

    /**
     * 查询指定用户优惠券汇总信息
     * @param userId
     * @return
     */
    public List<CouponSummary> queryUserCoupons(String userId){
        CouponSummary filterObj = new CouponSummary();
        filterObj.setUserId(userId);
        filterObj.setDelete(CommonConstants.DEFAULT_VALID);
        Wrapper<CouponSummary> wrapperObj = new EntityWrapper<>(filterObj);
        wrapperObj.gt("coupon_quantity",0);
        return selectList(wrapperObj);
    }
}

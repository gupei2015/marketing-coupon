package lk.project.marketing.repository;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.bo.CouponReceiveBo;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.entity.CouponReceive;
import lk.project.marketing.base.entity.CouponReceiveDetail;
import lk.project.marketing.base.entity.CouponSummary;
import lk.project.marketing.mapper.CouponReceiveMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Repository
public class CouponReceiveRepository extends ServiceImpl<CouponReceiveMapper,CouponReceive> {

    @Autowired
    CouponReceiveMapper couponReceiveMapper;

    @Autowired
    CouponReceiveDetailRepository couponReceiveDetailRepository;

    @Autowired
    CouponSummaryRepository couponSummaryRepository;

    /**
     * 查询用户可用的领券清单数据(按优惠券截止使用日期由近及远排序)
     * @param userId
     * @param userCode
     * @param couponId
     * @return
     */
    public List<CouponReceive> getUserCouponReceiveList(String userId, String userCode, Long couponId){
        CouponReceive filterObj = new CouponReceive();
        if(couponId != null && couponId > 0) {
            filterObj.setCouponId(couponId);
        }

        filterObj.setUserId(userId);
        if (StringUtils.isNotEmpty(userCode)){
            filterObj.setUserCode(userCode);
        }
        filterObj.setDelete(CommonConstants.DEFAULT_VALID);
        Wrapper wrapper = new EntityWrapper(filterObj);

        //起始日期之后的有效劵
        wrapper.le("start_date", new Date());

        //截止日期未超过今天
        wrapper.ge("end_date", new Date()).or().isNull("end_date");

        //未使用或部分使用状态
        wrapper.eq("status", 0).or().eq("status", 2);

        //按截止日期从最近到最晚排序
        wrapper.orderBy("end_date", true);

        return selectList(wrapper);
    }

    /**
     * 根据发券id列表获得促销活动id列表
     * @param couponReceiveIds
     * @return
     */
    public List<Long> getCouponActivityIdsByReceiveIds(List<Long> couponReceiveIds){
        return couponReceiveMapper.getCouponActivityIdsByReceiveIds(couponReceiveIds);
    }

    /**
     * 通过发券ID获得发券记录
     * @param couponReceiveId
     * @return
     */
    public CouponReceive getCouponReceiveById(Long couponReceiveId) {
        CouponReceive filterObj = new CouponReceive();
        filterObj.setDelete(CommonConstants.DEFAULT_VALID);
        filterObj.setId(couponReceiveId);
        Wrapper<CouponReceive> wrapperObj = new EntityWrapper<>(filterObj);
        return selectOne(wrapperObj);
    }

    /**
     * 查询用户优惠券领劵记录可用性
     * @param userId
     * @param couponId
     * @return
     */
    public Boolean checkUserCouponValidation(String userId, Long couponId) {
        if(StringUtils.isNotBlank(userId) && couponId > 0L){
            CouponReceive couponReceive = new CouponReceive();
            couponReceive.setUserId(userId);
            couponReceive.setCouponId(couponId);
            couponReceive.setDelete(CommonConstants.DEFAULT_VALID);
            Wrapper wrapper = new EntityWrapper(couponReceive);

            CouponReceive couponReceiveResult = selectOne(wrapper);
            Date today = new Date();
            if(couponReceiveResult != null && couponReceiveResult.getEndDate().getTime() >= today.getTime()){
                return true;
            }
        }

        return false;
    }

    /**
     * 获得指定用户某次活动领取优惠券数量
     * @param userId
     * @param activityId
     * @return
     */
    public Long getReceivedCouponQuantities(String userId, Long activityId){
        return couponReceiveMapper.getReceivedCouponQuantities(userId,activityId);
    }

    /**
     * 保存发券相关信息
     * @param couponReceiveBo
     * @return
     */
    public Boolean saveCouponReceiveBo(CouponReceiveBo couponReceiveBo){
            if(couponReceiveBo==null){
                return false;
            }
            CouponReceive couponReceive = couponReceiveBo.getCouponReceive();
            if(couponReceive==null){
                return false;
            }

            insert(couponReceive);
            List<CouponReceiveDetail> couponReceiveDetailList = couponReceiveBo.getCouponReceiveDetails();
            if(!CollectionUtils.isEmpty(couponReceiveDetailList)){
                couponReceiveDetailList.stream()
                        .forEach(o->{
                            o.setCouponReceiveId(couponReceive.getId());
                            couponReceiveDetailRepository.insert(o);
                        });
            }

            CouponSummary userCouponSummaryInfo = couponSummaryRepository.getUserCouponSummaryInfo(
                    couponReceive.getUserId(), couponReceive.getCouponId());
            if(userCouponSummaryInfo==null){
                CouponSummary couponSummary = new CouponSummary();
                couponSummary.setUserId(couponReceive.getUserId());
                couponSummary.setUserCode(couponReceive.getUserCode());
                couponSummary.setCouponId(couponReceive.getCouponId());
                couponSummary.setCouponAmount(couponReceive.getCouponAmount());
                couponSummary.setReceivedQuantity(couponReceive.getReceiveQuantity());
                couponSummary.setCouponQuantity(couponReceive.getReceiveQuantity());
                couponSummaryRepository.insert(couponSummary);
            }else {
                userCouponSummaryInfo.setReceivedQuantity(userCouponSummaryInfo.getReceivedQuantity()+couponReceive
                        .getReceiveQuantity());
                userCouponSummaryInfo.setCouponQuantity(userCouponSummaryInfo.getCouponQuantity()+couponReceive
                        .getReceiveQuantity());
                couponSummaryRepository.updateById(userCouponSummaryInfo);
            }
            return true;
    }

    /**
     * 根据领券ID逻辑删除领券记录
     * @param couponReceiveId
     * @return
     */
    public Boolean logicDeleteCouponReceive(Long couponReceiveId){
        CouponReceive couponReceive = getCouponReceiveById(couponReceiveId);
        couponReceive.setDelete(CommonConstants.DEFAULT_INVALID);
        return updateById(couponReceive);
    }
}

package lk.project.marketing.backend.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.entity.PromotionActivity;
import lk.project.marketing.backend.mapper.ActivityManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActivityManagementRepository extends ServiceImpl<ActivityManagementMapper,PromotionActivity> {

    @Autowired
    ActivityManagementMapper activityManagementMapper;

    /**
     * 根据优惠券ID获取对应的活动列表
     * @param couponId 优惠券ID
     * @return
     */
    public List<PromotionActivity> getActivityListByCouponId(Long couponId) {
        return activityManagementMapper.getActivityListByCouponId(couponId);
    }

    /**
     * 根据促销活动id获得促销活动实体
     * @param id
     * @return
     */
    public PromotionActivity getActivityById(Long id){
        return activityManagementMapper.getActivityById(id);
    }

    /**
     * 根据优惠券ID集合获取对应的活动ID集合
     * @param couponIdList
     * @return
     */
    public List<Long> getActivityIdListByCouponIdList(List<Long> couponIdList){
        return activityManagementMapper.getActivityIdListByCouponIdList(couponIdList);
    }

    /**
     * 更新促销活动记录
     * @param promotionActivity
     * @return
     */
    public Boolean updateActivity(PromotionActivity promotionActivity){
        return activityManagementMapper.updateActivity(promotionActivity);
    }

}

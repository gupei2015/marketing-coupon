package lk.project.marketing.mapper;

/**
 * Created by Pei Gu on 2018/9/13.
 */

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.entity.PromotionActivity;

import java.util.List;

public interface ActivityMapper extends BaseMapper<PromotionActivity> {


    /**
     * 根据优惠券ID获取对应的活动列表
     * @param couponId 优惠券ID
     * @return
     */
    List<PromotionActivity> getActivityListByCouponId(Long couponId);

    /**
     * 根据优惠券ID集合获取对应的活动ID集合
     * @param couponIdList
     * @return
     */
    List<Long> getActivityIdListByCouponIdList(List<Long> couponIdList);

    /**
     * 根据促销活动id获取促销活动实体
     * @param id
     * @return
     */
    PromotionActivity getActivityById(Long id);

    /**
     * 更新促销活动记录
     * @param promotionActivity
     * @return
     */
    Boolean updateActivity(PromotionActivity promotionActivity);
}

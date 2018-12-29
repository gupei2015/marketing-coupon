package lk.project.marketing.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.entity.CouponReceive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by alexlu on 2018/10/29.
 */
public interface CouponReceiveMapper extends BaseMapper<CouponReceive> {
    /**
     *根据发券id列表获得促销活动id列表
     * @param couponReceiveIds
     * @return
     */
    List<Long> getCouponActivityIdsByReceiveIds(List<Long> couponReceiveIds);

    /**
     * 获得指定用户某次活动领取优惠券数量
     * @param userId
     * @param activityId
     * @return
     */
    Long getReceivedCouponQuantities(@Param("userId") String userId, @Param("activityId") Long activityId);
}

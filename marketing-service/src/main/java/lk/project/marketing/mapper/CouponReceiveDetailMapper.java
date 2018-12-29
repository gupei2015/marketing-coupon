package lk.project.marketing.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.bo.CouponSummaryDetailBo;
import lk.project.marketing.base.entity.CouponReceiveDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhanghongda on 2018/10/31.
 */
public interface CouponReceiveDetailMapper extends BaseMapper<CouponReceiveDetail> {

    /**
     * 查询用户优惠券详情
     * @param couponId
     * @param userId
     * @return
     */
    List<CouponSummaryDetailBo> getUserCouponReceiveDetailListByCouponId(@Param("couponID") Long couponId, @Param("userID") String userId);
}

package lk.project.marketing.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.bo.CouponHistoryInfoBo;
import lk.project.marketing.base.entity.CouponSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhanghongda on 2018/10/31.
 */
public interface CouponSummaryMapper extends BaseMapper<CouponSummary> {

    /**
     * 查询用户订单的用劵历史记录列表
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return
     */
    List<CouponHistoryInfoBo> getCouponHistoryDetailsByUserOrder(@Param("userId") String userId, @Param("orderId") String orderId);
}

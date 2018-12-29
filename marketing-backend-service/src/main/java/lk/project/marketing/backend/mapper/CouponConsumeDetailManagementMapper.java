package lk.project.marketing.backend.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.entity.CouponConsumeDetail;

import java.util.List;

/**
 * Created by zhanghongda on 2018/10/31.
 */
public interface CouponConsumeDetailManagementMapper extends BaseMapper<CouponConsumeDetail> {

    /**
     * 根据用券id获取用券详情列表
     * @param consumeId
     * @return
     */
    List<CouponConsumeDetail> getCouponConsumeDetailListByConsumeId(Long consumeId);
}

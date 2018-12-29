package lk.project.marketing.backend.mapper;

/**
 * Created by Pei Gu on 2018/9/13.
 */

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.entity.PromotionRule;

import java.util.List;

public interface PromotionRuleManagementMapper extends BaseMapper<PromotionRule> {

    /**
     * 根据促销活动id获得促销规则列表
     * @param activityId
     * @return
     */
    List<PromotionRule> getPromotionRuleListByActivityId(Long activityId);
}

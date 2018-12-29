package lk.project.marketing.backend.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.backend.mapper.PromotionRuleManagementMapper;
import lk.project.marketing.base.entity.PromotionRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PromotionRuleManagementRepository extends ServiceImpl<PromotionRuleManagementMapper,PromotionRule> {

    @Autowired
    PromotionRuleManagementMapper promotionRuleManagementMapper;

    /**
     * 根据促销活动id获得促销规则列表
     * @param activityId 促销活动ID
     * @return
     */
    public List<PromotionRule> getPromotionRuleListByActivityId(Long activityId){
        return promotionRuleManagementMapper.getPromotionRuleListByActivityId(activityId);
    }
}

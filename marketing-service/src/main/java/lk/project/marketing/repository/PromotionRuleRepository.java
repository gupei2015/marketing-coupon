package lk.project.marketing.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.entity.PromotionRule;
import lk.project.marketing.mapper.PromotionRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PromotionRuleRepository extends ServiceImpl<PromotionRuleMapper,PromotionRule> {

    @Autowired
    PromotionRuleMapper promotionRuleMapper;

    /**
     * 根据促销活动id获得促销规则列表
     * @param activityId 促销活动ID
     * @return
     */
    public List<PromotionRule> getPromotionRuleListByActivityId(Long activityId){
        return promotionRuleMapper.getPromotionRuleListByActivityId(activityId);
    }

}

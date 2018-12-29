package lk.project.marketing.service;

import lk.project.marketing.base.bo.MemberBo;
import lk.project.marketing.base.bo.OrderBo;
import lk.project.marketing.base.bo.OrderItemBo;
import lk.project.marketing.base.entity.PromotionRule;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.base.utils.PromotionRuleMatch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.script.ScriptException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by gupei on 2018/10/12.
 */
public interface PromotionRuleService {

    /**
     * 校验活动规则是否匹配
     * @param memberBo 用户会员信息
     * @param orderBo 订单信息
     * @param orderItem 订单商品信息
     * @param promotionRules    参与促销活动规则
     * @param ruleType  活动规则类型
     * @return
     */
    static boolean verifyPromotionRule(MemberBo memberBo,
                                       OrderBo orderBo,
                                       OrderItemBo orderItem,
                                       List<PromotionRule> promotionRules,
                                       int ruleType){

        if (CollectionUtils.isEmpty(promotionRules)) {
            return true;
        }

        List<PromotionRule> rules = promotionRules.stream()
                .filter( o-> (o.getRuleType() == ruleType) )
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(rules)) {
            return true;
        }
        else {
            Map<String, Object> memberFilterMap;
            Map<String, Object> skuFilterMap;
            Map<String, Object> orderFilterMap;
            try {
                memberFilterMap = PromotionRuleMatch.buildFilterMap(memberBo);
                skuFilterMap = PromotionRuleMatch.buildFilterMap(orderItem);
                orderFilterMap = PromotionRuleMatch.buildFilterMap(orderBo);
            } catch (IllegalAccessException e) {
                throw new BusinessException("获取用户或商品适用对象数据出错", e);
            }

            /**
             * 迭代匹配活动规则，满足任一条规则即返回匹配成功
             */
            for(PromotionRule rule : rules) {
                /**
                 * 用户适用对象条件匹配
                 */
                if (StringUtils.isNotEmpty(rule.getUserCondition())) {
                    if (CollectionUtils.isEmpty(memberFilterMap)) continue;
                    try {
                        if (!PromotionRuleMatch.execJsonExpression(rule.getUserCondition(), memberFilterMap)){
                            continue;
                        }
                    } catch (ScriptException e) {
                        throw new BusinessException("用户适用对象条件匹配出错", e);
                    }
                }

                /**
                 * 商品适用范围条件匹配
                 */
                if (StringUtils.isNotEmpty(rule.getSkuCondition())) {
                    if (CollectionUtils.isEmpty(skuFilterMap)) continue;
                    try {
                        if (!PromotionRuleMatch.execJsonExpression(rule.getSkuCondition(), skuFilterMap)){
                            continue;
                        }
                    } catch (ScriptException e) {
                        throw new BusinessException("商品适用范围条件匹配出错", e);
                    }
                }

                /**
                 * 订单适用范围条件匹配
                 */
                if (StringUtils.isNotEmpty(rule.getOrderCondition())) {
                    if (CollectionUtils.isEmpty(orderFilterMap)) continue;
                    try{
                        if (!PromotionRuleMatch.execJsonExpression(rule.getOrderCondition(), orderFilterMap)){
                            continue;
                        }
                    } catch (ScriptException e) {
                        throw new BusinessException("订单适用范围条件匹配出错", e);
                    }

                }
                return true;
            }
        }
        return false;

    }

}

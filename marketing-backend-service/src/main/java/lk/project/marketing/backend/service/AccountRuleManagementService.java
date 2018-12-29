package lk.project.marketing.backend.service;


import lk.project.marketing.base.bo.AccountRuleReqBo;
import lk.project.marketing.base.bo.PagerBaseRespBo;
import lk.project.marketing.base.bo.QueryAccountRuleReqBo;
import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.client.dto.PagerBaseResponseDto;

/**
 * 结算规则相关后台管理服务
 * Created by luchao on 2018/12/25.
 */
public interface AccountRuleManagementService {

    /**
     * 新增或更新优惠券结算规则
     * @param accountRuleReqBo
     * @return
     */
    Boolean saveAccountRule(AccountRuleReqBo accountRuleReqBo);

    /**
     * 逻辑删除优惠券结算规则
     * @param accountRuleId
     * @return
     */
    Boolean deleteAccountRule(Long accountRuleId);

    /**
     * 分页根据条件查询优惠券结算规则
     * @param queryAccountRuleReqBo
     * @return
     */
    PagerBaseRespBo<AccountRule> queryAccountRule(QueryAccountRuleReqBo queryAccountRuleReqBo);
}

package lk.project.marketing.client.rpc;

import lk.project.marketing.client.dto.AccountRuleReqDto;
import lk.project.marketing.client.dto.QueryAccountRuleReqDto;
import lk.project.marketing.client.vo.ResponseVO;

public interface AccountRuleManagementInterface {

    /**
     * 新增或更新优惠券结算规则
     * @param accountRuleReqDto
     * @return
     */
    ResponseVO saveAccountRule(AccountRuleReqDto accountRuleReqDto);

    /**
     * 逻辑删除优惠券结算规则
     * @param accountRuleId
     * @return
     */
    ResponseVO deleteAccountRule(Long accountRuleId);

    /**
     * 根据条件分页查询优惠券结算规则
     * @param queryAccountRuleReqDto
     * @return
     */
    ResponseVO queryAccountRule(QueryAccountRuleReqDto queryAccountRuleReqDto);
}

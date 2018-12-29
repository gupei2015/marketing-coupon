package lk.project.marketing.backend.service.rpc;

import lk.project.marketing.backend.service.AccountRuleManagementService;
import lk.project.marketing.backend.service.rpc.pojo.BaseResponse;
import lk.project.marketing.base.bo.AccountRuleReqBo;
import lk.project.marketing.base.bo.QueryAccountRuleReqBo;
import lk.project.marketing.client.dto.AccountRuleReqDto;
import lk.project.marketing.client.dto.QueryAccountRuleReqDto;
import lk.project.marketing.client.rpc.AccountRuleManagementInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountRuleManagementInterfaceImpl extends BaseResponse implements AccountRuleManagementInterface {

    @Autowired
    AccountRuleManagementService accountRuleManagementService;

    /**
     * 新增或修改优惠券结算规则
     * @param accountRuleReqDto
     * @return
     */
    @Override
    public ResponseVO saveAccountRule(AccountRuleReqDto accountRuleReqDto){
        AccountRuleReqBo accountRuleReqBo = new AccountRuleReqBo();
        BeanUtils.copyProperties(accountRuleReqDto,accountRuleReqBo);
        return getFromData(accountRuleManagementService.saveAccountRule(accountRuleReqBo));
    }

    /**
     * 逻辑删除优惠券结算规则
     * @param accountRuleId
     * @return
     */
    @Override
    public ResponseVO deleteAccountRule(Long accountRuleId){
        return getFromData(accountRuleManagementService.deleteAccountRule(accountRuleId));
    }

    /**
     * 根据条件分页查询优惠券结算规则
     * @param queryAccountRuleReqDto
     * @return
     */
    @Override
    public ResponseVO queryAccountRule(QueryAccountRuleReqDto queryAccountRuleReqDto){
        QueryAccountRuleReqBo queryAccountRuleReqBo = new QueryAccountRuleReqBo();
        BeanUtils.copyProperties(queryAccountRuleReqDto,queryAccountRuleReqBo);
        return getFromData(accountRuleManagementService.queryAccountRule(queryAccountRuleReqBo));
    }
}

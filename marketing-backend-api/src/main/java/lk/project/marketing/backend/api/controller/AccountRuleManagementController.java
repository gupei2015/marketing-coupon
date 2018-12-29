package lk.project.marketing.backend.api.controller;

import lk.project.marketing.backend.api.common.BaseController;
import lk.project.marketing.client.dto.AccountRuleReqDto;
import lk.project.marketing.client.dto.QueryAccountRuleReqDto;
import lk.project.marketing.client.rpc.AccountRuleManagementInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backend/accountRule")
public class AccountRuleManagementController extends BaseController {

    @Autowired
    AccountRuleManagementInterface accountRuleManagementInterface;

    /**
     * 新增或修改优惠券结算规则
     * @param accountRuleReqDto
     * @return
     */
    @PostMapping("/saveAccountRule")
    public ResponseVO saveAccountRule(@RequestBody AccountRuleReqDto accountRuleReqDto){
        return accountRuleManagementInterface.saveAccountRule(accountRuleReqDto);
    }

    /**
     * 逻辑删除优惠券结算规则
     * @param accountRuleId
     * @return
     */
    @GetMapping("/deleteAccountRule")
    public ResponseVO deleteAccountRule(@RequestParam Long accountRuleId){
        return accountRuleManagementInterface.deleteAccountRule(accountRuleId);
    }

    /**
     * 根据条件分页查询优惠券结算规则
     * @param queryAccountRuleReqDto
     * @return
     */
    @PostMapping("/queryAccountRule")
    public ResponseVO queryAccountRule(@RequestBody QueryAccountRuleReqDto queryAccountRuleReqDto){
        return accountRuleManagementInterface.queryAccountRule(queryAccountRuleReqDto);
    }
}

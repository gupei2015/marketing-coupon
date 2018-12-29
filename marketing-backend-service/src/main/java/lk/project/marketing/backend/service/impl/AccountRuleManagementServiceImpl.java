package lk.project.marketing.backend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lk.project.marketing.backend.repository.AccountRuleManagementRepository;
import lk.project.marketing.backend.service.AccountRuleManagementService;
import lk.project.marketing.base.bo.AccountRuleReqBo;
import lk.project.marketing.base.bo.PagerBaseRespBo;
import lk.project.marketing.base.bo.QueryAccountRuleReqBo;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.entity.AccountRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结算规则服务实现类
 * Created by zhanghongda on 2018/10/30.
 */
@Service
@Slf4j
public class AccountRuleManagementServiceImpl implements AccountRuleManagementService {

    @Autowired
    AccountRuleManagementRepository accountRuleManagementRepository;

    /**
     * 新增或更新优惠券结算规则
     * @param accountRuleReqBo
     * @return
     */
    @Override
    public Boolean saveAccountRule(AccountRuleReqBo accountRuleReqBo){
        AccountRule accountRule = new AccountRule();
        BeanUtils.copyProperties(accountRuleReqBo,accountRule);
        return accountRuleManagementRepository.insertOrUpdate(accountRule);
    }

    /**
     * 逻辑删除优惠券结算规则
     * @param accountRuleId
     * @return
     */
    @Override
    public Boolean deleteAccountRule(Long accountRuleId){
        AccountRule accountRule = accountRuleManagementRepository.selectById(accountRuleId);
        accountRule.setDelete(CommonConstants.DEFAULT_INVALID);
        return accountRuleManagementRepository.updateById(accountRule);
    }

    /**
     * 根据条件分页查询优惠券结算规则
     * @param queryAccountRuleReqBo
     * @return
     */
    @Override
    public PagerBaseRespBo<AccountRule> queryAccountRule(QueryAccountRuleReqBo queryAccountRuleReqBo){
        AccountRule accountRule = new AccountRule();
        BeanUtils.copyProperties(queryAccountRuleReqBo,accountRule);
        accountRule.setDelete(CommonConstants.DEFAULT_VALID);
        Wrapper wrapperObj = new EntityWrapper(accountRule);
        wrapperObj.orderBy("created_at",false);
        List records;
        PagerBaseRespBo<AccountRule> pagerBaseRespBo = new PagerBaseRespBo<>();
        if(queryAccountRuleReqBo.getIsPager().equals(CommonConstants.DEFAULT_QUERY_IS_PAGER)){
            Page<AccountRule> page = new Page<>(queryAccountRuleReqBo.getPageNum(),queryAccountRuleReqBo.getPageSize());
            records = accountRuleManagementRepository.selectPage(page, wrapperObj).getRecords();
            pagerBaseRespBo.setTotalCount(Long.valueOf(records.size()));
            pagerBaseRespBo.setPageData(records);
            return pagerBaseRespBo;
        }
        records = accountRuleManagementRepository.selectList(wrapperObj);
        pagerBaseRespBo.setTotalCount(Long.valueOf(records.size()));
        pagerBaseRespBo.setPageData(records);
        return pagerBaseRespBo;
    }
}

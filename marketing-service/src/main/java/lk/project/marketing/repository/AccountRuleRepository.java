package lk.project.marketing.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.mapper.AccountRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by zhanghongda on 2018/11/8.
 */
@Repository
public class AccountRuleRepository extends ServiceImpl<AccountRuleMapper,AccountRule> {

    @Autowired
    AccountRuleMapper accountRuleMapper;

    /**
     * 根据id获取优惠券结算规则相关信息
     * @param id
     * @return
     */
    public AccountRule getAccountRuleById(Long id) {
        return accountRuleMapper.getAccountRuleById(id);
    }
}

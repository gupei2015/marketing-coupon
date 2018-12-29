package lk.project.marketing.mapper;

/**
 * Created by zhanghongda on 2018/10/30.
 */

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.entity.AccountRule;

public interface AccountRuleMapper extends BaseMapper<AccountRule> {

    /**
     * 通过id获得优惠券结算规则
     * @param id
     * @return
     */
    AccountRule getAccountRuleById(Long id);
}

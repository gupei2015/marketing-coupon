package lk.project.marketing.repository;

import lk.project.marketing.base.entity.AccountRule;
import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
@FixMethodOrder( MethodSorters.NAME_ASCENDING)
public class AccountRuleRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(AccountRuleRepositoryTest.class);

    @Resource
    private AccountRuleRepository accountRuleRepository;

    private static AccountRule accountRule;

    @BeforeClass
    public static void initAccountRule(){
        accountRule = new AccountRule();
        accountRule.setRuleName("满100减20,满200减50");
        accountRule.setRuleDesc("购买指定店铺商品规则");
        accountRule.setThresholdType(1);
        accountRule.setRewardThreshold(new BigDecimal(100));
    }

    /**
     * 新增一条结算规则
     */
    @Test
    public void test001InsertAccountRule(){
        try {
            boolean result = accountRuleRepository.insert(accountRule);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增结算规则失败",e);
        }
    }

    /**
     * 通过结算规则id获得结算规则实体
     */
    @Test
    public void test002GetAccountRuleById(){
        try {
            AccountRule accountRuleById = accountRuleRepository.getAccountRuleById(accountRule.getId());
            if (accountRuleById!=null) {
                //校验结算规则名是否为空
                Assert.assertNotNull(accountRuleById.getRuleName());
                //校验满减条件类型是否为空
                Assert.assertNotNull(accountRuleById.getThresholdType());
            }
        } catch (Exception e) {
            log.error("校验查询结果失败", e);
        }
    }

    /**
     *更新结算规则记录
     */
    @Test
    public void test003UpdateAccountRule(){
        accountRule.setRuleName("买10送2");
        try {
            boolean updateResult = accountRuleRepository.updateById(accountRule);
            Assert.assertTrue(updateResult);
        } catch (Exception e) {
            log.error("校验更新记录失败",e);
        }
    }

    /**
     * 删除结算规则测试数据
     */
    @Test
    public void test099DeleteAccountRule(){
        try {
            boolean deleteResult = accountRuleRepository.deleteById(accountRule.getId());
            Assert.assertTrue(deleteResult);
        } catch (Exception e) {
            log.error("校验删除记录失败",e);
        }
    }
}

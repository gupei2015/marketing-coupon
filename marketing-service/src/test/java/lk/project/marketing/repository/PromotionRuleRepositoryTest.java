package lk.project.marketing.repository;

import lk.project.marketing.base.entity.PromotionRule;
import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
public class PromotionRuleRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(PromotionRuleRepositoryTest.class);

    @Resource
    private PromotionRuleRepository promotionRuleRepository;

    private final static Long id = 21L;
    private PromotionRule promotionRule = new PromotionRule();

    @Before
    public void initPromotionRule(){
        promotionRule.setId(id);
        promotionRule.setActivityId(18L);
        promotionRule.setRuleName("钻石会员享受优惠");
    }

    /**
     * 根据促销活动id获得促销规则列表
     */
    @Test
    public void testGetPromotionRuleListByActivityId(){
        try {
            List<PromotionRule> promotionRules = promotionRuleRepository.getPromotionRuleListByActivityId(3L);
            if(!CollectionUtils.isEmpty(promotionRules)){
                Assert.assertEquals(promotionRules.size(),2);
                for(PromotionRule promotionRule:promotionRules){
                    Assert.assertNotNull(promotionRule.getActivityId());
                    Assert.assertNotNull(promotionRule.getRuleName());
                }
            }
        } catch (Exception e) {
            log.error("校验查询记录失败",e);
        }
    }

    /**
     * 新增促销规则记录
     */
    @Test
    public void testInsertPromotionRule(){
        try {
            Boolean result = promotionRuleRepository.insert(promotionRule);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增促销规则记录失败",e);
        }
    }

    /**
     * 通过id更新促销规则
     */
    @Test
    public void testUpdatePromotionRuleById(){
        try {
            promotionRule.setRuleType(1);
            Boolean result = promotionRuleRepository.updateById(promotionRule);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验更新促销规则失败",e);
        }
    }

    /**
     * 删除测试数据
     */
    @Test
    public void testDeletePromotionRuleById(){
        try {
            boolean result = promotionRuleRepository.deleteById(promotionRule.getId());
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验删除测试数据失败",e);
        }
    }
}

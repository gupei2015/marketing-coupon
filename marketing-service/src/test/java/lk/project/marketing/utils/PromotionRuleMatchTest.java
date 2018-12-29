package lk.project.marketing.utils;

import lk.project.marketing.base.bo.MemberBo;
import lk.project.marketing.base.bo.OrderBo;
import lk.project.marketing.base.utils.PromotionRuleMatch;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.Map;

public class PromotionRuleMatchTest {

    @Test
    public void testExecJsonExpression() throws IllegalAccessException, ScriptException {

        MemberBo memberBo = new MemberBo();
        memberBo.setAge(20);
        memberBo.setGender(0);
        memberBo.setLevel(2);
        memberBo.setUserName("test user");
        memberBo.setPhoneNumber("13661725558l");
        Map<String, Object> filterMap = PromotionRuleMatch.buildFilterMap(memberBo);

        String strCondition ="{\"age\":\"age>18\",\"gender\":\"gender==0\",\"level\":\"level>=2\",\"userName\":\"userName=='test user'\"}";
        Assert.assertTrue(PromotionRuleMatch.execJsonExpression(strCondition,filterMap));

        memberBo.setGender(1);
        filterMap = PromotionRuleMatch.buildFilterMap(memberBo);
        Assert.assertFalse(PromotionRuleMatch.execJsonExpression(strCondition,filterMap));

    }

    @Test
    public void testDateTimeExpression() throws IllegalAccessException, ScriptException {

        OrderBo orderBo = new OrderBo();
        orderBo.setPayOrderAmount(new BigDecimal(820.50));

        Time reserveTime= new Time(10,30,0);
        orderBo.setReserveTime(reserveTime);
        orderBo.setOrderDate(new Date());

        Map<String, Object> filterMap = PromotionRuleMatch.buildFilterMap(orderBo);

        /**
         * reserveTime 8:00:00~11:30:00, orderDate 2018/01/01~2018/12/31
         */
        String strCondition = "{\"payOrderAmount\":\"payOrderAmount>=500\"," +
                "\"reserveTime\":\"reserveTime>=0&&reserveTime<=12600000\"," +
                "\"orderDate\":\"orderDate>=1514736000000&&orderDate<1546272000000\"}";
        Assert.assertTrue(PromotionRuleMatch.execJsonExpression(strCondition,filterMap));

        /**
         * orderDate 2018/01/01~2018/9/30
         */
        strCondition = "{\"payOrderAmount\":\"payOrderAmount>=500\"," +
                "\"reserveTime\":\"reserveTime>=0&&reserveTime<=12600000\"," +
                "\"orderDate\":\"orderDate>=1514736000000&&orderDate<1538323200000\"}";
        Assert.assertFalse(PromotionRuleMatch.execJsonExpression(strCondition,filterMap));

    }

}
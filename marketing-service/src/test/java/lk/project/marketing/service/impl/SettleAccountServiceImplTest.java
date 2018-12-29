package lk.project.marketing.service.impl;

import lk.project.marketing.base.bo.*;
import lk.project.marketing.init.MarketingServiceApplication;
import lk.project.marketing.service.SettleAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexlu on 2018/11/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
public class SettleAccountServiceImplTest {
    private final static Logger log = LoggerFactory.getLogger(CouponConsumeServiceImplTest.class);

    @Autowired
    SettleAccountService settleAccountService;

    @Test
    public void testOrderSettlement(){
        MemberBo memberBo = new MemberBo();
        memberBo.setUserId("1");
        memberBo.setUserCode("LK-User01");
        memberBo.setAge(21);
        memberBo.setGender(0);  //0-男,1-女
        memberBo.setLevel(2);

        OrderBo orderBo = buildOrderDummyData();

        try {
            OrderSettleResultBo orderSettleResultBo = settleAccountService.orderSettlement(memberBo, orderBo);
            Assert.assertNotNull(orderSettleResultBo);
        }catch (Exception e){
            log.error("测试订单结算操作失败!", e);
            Assert.fail("测试订单结算发生异常");
        }
    }

    /**
     * 组装订单模拟请求数据
     */
    public OrderBo buildOrderDummyData(){
        OrderBo orderBo = new OrderBo();
        orderBo.setOrderId("101");
        orderBo.setOrderNo("LKD001");
        orderBo.setOrderDate(new Date());
        //orderBo.setReserveTime(new Time("15:01:00"));
        orderBo.setTotalOrderAmount(new BigDecimal(260.12).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        orderBo.setPayOrderAmount(new BigDecimal(240.11).setScale(3, BigDecimal.ROUND_HALF_DOWN));
//        orderBo.setShopId("LKSP01");

        List<UseCouponBo> orderUseCouponList = new ArrayList<>();
//        orderUseCouponList.add(new UseCouponBo(1L, 3L));
//        orderUseCouponList.add(new UseCouponBo(2L, 5L));

//        orderUseCouponList.add(new UseCouponBo(2L, 2L));
        orderBo.setUseCoupons(orderUseCouponList);

        List<OrderItemBo> itemBoList = new ArrayList<>();

        //商品1
        OrderItemBo orderItemBo = new OrderItemBo();
        orderItemBo.setOrderItemId("11");
        orderItemBo.setGoodsQuantity(15L);
        orderItemBo.setSkuId("LKSKU01");
        orderItemBo.setSkuCategory("LKSKCAT01");
        orderItemBo.setTotalGoodsAmount(new BigDecimal(221.69).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        orderItemBo.setPayGoodsAmount(new BigDecimal(210.12).setScale(3, BigDecimal.ROUND_HALF_DOWN));

        List<UseCouponBo> orderItemUseCouponList = new ArrayList<>();
//        orderItemUseCouponList.add(new UseCouponBo(1L, 2L));
//        orderItemUseCouponList.add(new UseCouponBo(4L, 4L));
        orderItemBo.setUseCoupons(orderItemUseCouponList);
        itemBoList.add(orderItemBo);

        //商品2
        orderItemBo.setOrderItemId("12");
        orderItemBo.setGoodsQuantity(7L);
        orderItemBo.setSkuId("LKSKU02");
        orderItemBo.setSkuCategory("LKSKCAT02");
        orderItemBo.setTotalGoodsAmount(new BigDecimal(129.15).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        orderItemBo.setPayGoodsAmount(new BigDecimal(122.08).setScale(3, BigDecimal.ROUND_HALF_DOWN));

        orderItemUseCouponList = new ArrayList<>();
//        orderItemUseCouponList.add(new UseCouponBo(3L, 3L));
//        orderItemUseCouponList.add(new UseCouponBo(2L, 4L));
        orderItemBo.setUseCoupons(orderItemUseCouponList);
        itemBoList.add(orderItemBo);

        //商品3
        orderItemBo.setOrderItemId("13");
        orderItemBo.setGoodsQuantity(10L);
        orderItemBo.setSkuId("LKSKU03");
        orderItemBo.setSkuCategory("LKSKCAT03");
        orderItemBo.setTotalGoodsAmount(new BigDecimal(56.28).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        orderItemBo.setPayGoodsAmount(new BigDecimal(51.16).setScale(3, BigDecimal.ROUND_HALF_DOWN));

        orderItemUseCouponList = new ArrayList<>();
//        orderItemUseCouponList.add(new UseCouponBo(1L, 2L));
//        orderItemUseCouponList.add(new UseCouponBo(3L, 4L));
        orderItemBo.setUseCoupons(orderItemUseCouponList);
        itemBoList.add(orderItemBo);
        orderBo.setOrderItemList(itemBoList);

        return orderBo;
    }

}

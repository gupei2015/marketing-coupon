package lk.project.marketing.utils;

import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
public class DateConvertTimeTest {

    @Test
    public void testDateConvertTime(){
        Date startDate = new Date("2018/11/1");
        Date endDate = new Date("2019/2/15");
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        System.out.println(startTime);
        System.out.println(endTime);
    }

}

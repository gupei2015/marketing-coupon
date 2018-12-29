package lk.project.marketing.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Administrator on 2018/8/20/020.
 */
@ImportResource(value = {"classpath:/config/*.xml"})
@ComponentScan(basePackages = {"lk.project.marketing.*"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MarketingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketingApiApplication.class, args);
        System.out.println("...MarketingApiApplication started.......");
    }
}

package lk.project.marketing.backend.api;

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
public class MarketingBackendApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketingBackendApiApplication.class, args);
        System.out.println("...MarketingBackendApiApplication started.......");
    }
}

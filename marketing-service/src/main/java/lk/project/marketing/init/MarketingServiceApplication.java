package lk.project.marketing.init;

import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Gupei on 2018/10/31.
 */
@ImportResource(value = {"classpath:/config/*.xml"})
@ComponentScan(basePackages = {"lk.project.marketing"})
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@Slf4j
public class MarketingServiceApplication {
    public static void main(String... args) {
        SpringApplication.run(MarketingServiceApplication.class, args);
        System.out.println("ServiceApplication started.......");
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by Administrator on 2018/8/20/020.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MarketingClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketingClientApplication.class, args);
        System.out.println("...MarketingClientApplication started.......");
    }
}

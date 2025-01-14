package doctorhoai.learn.rateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RateserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateserviceApplication.class, args);
    }

}

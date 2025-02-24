package doctorhoai.learn.notificationservice.config;

import doctorhoai.learn.basedomain.Event.TicketEmail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;

@Configuration
public class RetryTopicConfig {
    @Bean
    public RetryTopicConfiguration myRetryTopic(KafkaTemplate<String, TicketEmail> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .dltHandlerMethod("myCustomDltProcessor", "listenDLT")
                .create(template);
    }
}

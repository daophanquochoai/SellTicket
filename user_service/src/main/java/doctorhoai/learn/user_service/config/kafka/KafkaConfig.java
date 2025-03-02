package doctorhoai.learn.user_service.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfig {

    @Value("${spring.kafka.topic.name}")
    private String emailTopic;

    @Bean
    public NewTopic topicTicket(){
        NewTopic build = TopicBuilder.name(emailTopic).partitions(3).replicas(1).build();
        return build;
    }
}

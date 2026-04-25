package com.project.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderCreatedTopic(
            @Value("${app.kafka.topic.order-created}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCreatedTopic(
            @Value("${app.kafka.topic.payment-created}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCancelledTopic(
            @Value("${app.kafka.topic.payment-cancelled}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic paymentRefundedTopic(
            @Value("${app.kafka.topic.payment-refunded}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }
}

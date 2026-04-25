package com.project.orderservice.config;

import com.project.orderservice.event.PaymentCancelledEvent;
import com.project.orderservice.event.PaymentCreatedEvent;
import com.project.orderservice.event.PaymentRefundedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> baseProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "order-service-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent> paymentCreatedKafkaListenerContainerFactory() {
        Map<String, Object> props = baseProps();
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.project.orderservice.event.PaymentCreatedEvent");

        DefaultKafkaConsumerFactory<String, PaymentCreatedEvent> consumerFactory =
                new DefaultKafkaConsumerFactory<>(props);

        ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCancelledEvent> paymentCancelledKafkaListenerContainerFactory() {
        Map<String, Object> props = baseProps();
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.project.orderservice.event.PaymentCancelledEvent");

        DefaultKafkaConsumerFactory<String, PaymentCancelledEvent> consumerFactory =
                new DefaultKafkaConsumerFactory<>(props);

        ConcurrentKafkaListenerContainerFactory<String, PaymentCancelledEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentRefundedEvent> paymentRefundedKafkaListenerContainerFactory() {
        Map<String, Object> props = baseProps();
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.project.orderservice.event.PaymentRefundedEvent");

        DefaultKafkaConsumerFactory<String, PaymentRefundedEvent> consumerFactory =
                new DefaultKafkaConsumerFactory<>(props);

        ConcurrentKafkaListenerContainerFactory<String, PaymentRefundedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}

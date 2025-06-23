package com.studyhub.studyhub_backend_study.event.producer;

import com.studyhub.studyhub_backend_study.event.KafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T> void send(String topic, KafkaEvent<T> event) {
        kafkaTemplate.send(topic, event);
    }
}
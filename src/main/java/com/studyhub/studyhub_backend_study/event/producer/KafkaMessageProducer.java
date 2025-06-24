package com.studyhub.studyhub_backend_study.event.producer;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import com.studyhub.studyhub_backend_study.event.KafkaEvent;
import com.studyhub.studyhub_backend_study.event.producer.message.StudyCreatedEvent;
import com.studyhub.studyhub_backend_study.event.producer.message.StudyDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T> void send(String topic, KafkaEvent<T> event) {
        kafkaTemplate.send(topic, event);
    }

    public void sendCreateStudyGroupEvent(StudyGroup studyGroup, String userName, String role) {
        StudyCreatedEvent eventData = StudyCreatedEvent.fromEntity(studyGroup,userName,role);
        KafkaEvent<StudyCreatedEvent> kafkaEvent = KafkaEvent.<StudyCreatedEvent>builder()
                .eventType("STUDY_CREATED")
                .data(eventData)
                .timestamp(LocalDateTime.now().toString())
                .build();

        this.send(StudyCreatedEvent.Topic, kafkaEvent);
    }

    public void sendDeleteStudyGroupEvent(StudyGroup studyGroup) {
        StudyDeletedEvent eventData = StudyDeletedEvent.fromEntity(studyGroup);
        KafkaEvent<StudyDeletedEvent> kafkaEvent = KafkaEvent.<StudyDeletedEvent>builder()
                .eventType("STUDY_DELETED")
                .data(eventData)
                .timestamp(LocalDateTime.now().toString())
                .build();

        this.send(StudyDeletedEvent.Topic, kafkaEvent);
    }
}
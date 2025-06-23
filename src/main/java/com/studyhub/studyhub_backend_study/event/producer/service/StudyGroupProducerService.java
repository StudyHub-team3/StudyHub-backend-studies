package com.studyhub.studyhub_backend_study.event.producer.service;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import com.studyhub.studyhub_backend_study.event.KafkaEvent;
import com.studyhub.studyhub_backend_study.event.producer.event.StudyCreatedEvent;
import com.studyhub.studyhub_backend_study.event.producer.event.StudyDeletedEvent;
import com.studyhub.studyhub_backend_study.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudyGroupProducerService {

    private final KafkaMessageProducer kafkaMessageProducer;

    public void sendCreateStudyGroupEvent(StudyGroup studyGroup) {
        StudyCreatedEvent eventData = StudyCreatedEvent.fromEntity(studyGroup);
        KafkaEvent<StudyCreatedEvent> kafkaEvent = KafkaEvent.<StudyCreatedEvent>builder()
                .eventType("STUDY_CREATED")
                .data(eventData)
                .timestamp(LocalDateTime.now().toString())
                .build();

        kafkaMessageProducer.send(StudyCreatedEvent.Topic, kafkaEvent);
    }

    public void sendDeleteStudyGroupEvent(StudyGroup studyGroup) {
        StudyDeletedEvent eventData = StudyDeletedEvent.fromEntity(studyGroup);
        KafkaEvent<StudyDeletedEvent> kafkaEvent = KafkaEvent.<StudyDeletedEvent>builder()
                .eventType("STUDY_DELETED")
                .data(eventData)
                .timestamp(LocalDateTime.now().toString())
                .build();

        kafkaMessageProducer.send(StudyDeletedEvent.Topic, kafkaEvent);
    }
}
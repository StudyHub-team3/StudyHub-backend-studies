package com.studyhub.studyhub_backend_study.event.producer;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import com.studyhub.studyhub_backend_study.event.producer.message.StudyCreatedEvent;
import com.studyhub.studyhub_backend_study.event.producer.message.StudyDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Object event) {
        try {
            kafkaTemplate.send(topic, event);
        } catch (Exception e) {
            log.error("❌ Kafka 메시지 전송 실패: topic={}, event={}", topic, event, e);
        }
    }

    public void sendCreateStudyGroupEvent(StudyGroup studyGroup, String userName, String role) {
        StudyCreatedEvent.Data data = new StudyCreatedEvent.Data();
        data.setStudyId(studyGroup.getId());
        data.setUserId(studyGroup.getCreatedBy());
        data.setUserName(userName);
        data.setCreatorRole(role);

        StudyCreatedEvent eventData = new StudyCreatedEvent();
        eventData.setEventType("STUDY_CREATED");
        eventData.setData(data);
        eventData.setTimestamp(String.valueOf(System.currentTimeMillis()));

        this.send(StudyCreatedEvent.Topic, eventData);
    }

    public void sendDeleteStudyGroupEvent(StudyGroup studyGroup) {
        StudyDeletedEvent eventData = StudyDeletedEvent.fromEntity(studyGroup);
        this.send(StudyDeletedEvent.Topic, eventData);
    }
}
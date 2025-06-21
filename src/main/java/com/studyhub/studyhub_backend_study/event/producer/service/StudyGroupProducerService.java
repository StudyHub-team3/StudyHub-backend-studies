package com.studyhub.studyhub_backend_study.event.producer.service;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import com.studyhub.studyhub_backend_study.event.producer.event.CreateStudyGroupEvent;
import com.studyhub.studyhub_backend_study.event.producer.event.DeleteStudyGroupEvent;
import com.studyhub.studyhub_backend_study.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyGroupProducerService {

    private final KafkaMessageProducer kafkaMessageProducer;

    public void sendCreateStudyGroupEvent(StudyGroup studyGroup) {
        CreateStudyGroupEvent event = CreateStudyGroupEvent.fromEntity(studyGroup);
        kafkaMessageProducer.send(CreateStudyGroupEvent.Topic, event);
    }

    public void sendDeleteStudyGroupEvent(StudyGroup studyGroup) {
        DeleteStudyGroupEvent event = DeleteStudyGroupEvent.fromEntity(studyGroup);
        kafkaMessageProducer.send(DeleteStudyGroupEvent.Topic, event);
    }

}

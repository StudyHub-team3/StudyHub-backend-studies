package com.studyhub.studyhub_backend_study.event.consumer;

import com.studyhub.studyhub_backend_study.event.KafkaEvent;
import com.studyhub.studyhub_backend_study.event.consumer.message.StudyCrewEvent;
import com.studyhub.studyhub_backend_study.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessageConsumer {

    private final StudyGroupService studyGroupService;

    @KafkaListener(
            topics = StudyCrewEvent.Topic,
            properties = {
                    JsonDeserializer.VALUE_DEFAULT_TYPE + ":com.studyhub.studyhub_backend_study.event.KafkaEvent"
            }
    )
    public void handleStudyCrewEvent(KafkaEvent<StudyCrewEvent> event, Acknowledgment ack) {
        try {
            String eventType = event.getEventType();
            StudyCrewEvent data = event.getData();

            switch (eventType) {
                case "STUDY_CREW_JOINED" -> {
                    log.info("[🛜Kafka 수신] eventType={}, studyId={}, userId={}, userName={}, role={}",
                            eventType, data.getStudyId(), data.getUserId(), data.getRole());
                    studyGroupService.handleMemberJoin(data);
                }
                case "STUDY_CREW_QUITED" -> {
                    log.info("[🛜Kafka 수신] eventType={}, studyId={}, userId={}, userName={}, role={}",
                            eventType, data.getStudyId(), data.getUserId(), data.getRole());
                    studyGroupService.handleMemberQuit(data.getStudyId(), data.getRole());
                }
                default -> log.warn("알 수 없는 이벤트 타입: {}", eventType);
            }

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Kafka 이벤트 처리 중 예외 발생", e);
        }
    }
}
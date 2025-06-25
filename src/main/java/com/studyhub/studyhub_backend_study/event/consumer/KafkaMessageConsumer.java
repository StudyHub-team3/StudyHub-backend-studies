package com.studyhub.studyhub_backend_study.event.consumer;

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
            JsonDeserializer.VALUE_DEFAULT_TYPE + ":com.studyhub.studyhub_backend_study.event.consumer.message.StudyCrewEvent"
        }
    )
    public void handleStudyCrewEvent(StudyCrewEvent event, Acknowledgment ack) {
        log.info("🔥 Kafka consume 시작: {}", event);
        try {
            String eventType = event.getEventType();
            StudyCrewEvent.Data data = event.getData();

            log.info("📩 Kafka 수신 이벤트: eventType={}, studyId={}, userId={}, userName={}, role={}",
                    eventType, data.getStudyId(), data.getUserId(), data.getUserName(), data.getRole());

            switch (eventType) {
                case "STUDY_CREW_JOINED" -> studyGroupService.handleMemberJoin(event);
                case "STUDY_CREW_QUITED" -> studyGroupService.handleMemberQuit(event);
                default -> log.warn("알 수 없는 이벤트 타입: {}", eventType);
            }

            // ✅ acknowledgment가 실제로 주입된 경우에만 호출
            if (ack != null) {
                ack.acknowledge();
            }

        } catch (Exception e) {
            log.error("❌ Kafka 이벤트 처리 중 예외 발생", e);
        }
    }
}
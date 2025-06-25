package com.studyhub.studyhub_backend_study.api;

import com.studyhub.studyhub_backend_study.event.consumer.KafkaMessageConsumer;
import com.studyhub.studyhub_backend_study.event.consumer.message.StudyCrewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class ProducerTestController {

    private final KafkaMessageConsumer kafkaMessageConsumer;

    @GetMapping("/test-consume")
    public void testConsume() {
        StudyCrewEvent.Data data = new StudyCrewEvent.Data();
        data.setStudyId(1L);
        data.setUserId(100L);
        data.setUserName("테스트 유저");
        data.setRole("MENTEE");

        StudyCrewEvent event = new StudyCrewEvent();
        event.setEventType("STUDY_CREW_JOINED");
        event.setData(data);
        event.setTimestamp(LocalDateTime.now().toString());

        kafkaMessageConsumer.handleStudyCrewEvent(event, null);
    }
}
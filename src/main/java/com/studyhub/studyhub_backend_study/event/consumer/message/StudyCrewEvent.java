package com.studyhub.studyhub_backend_study.event.consumer.message;

import lombok.*;

@Getter @Setter
public class StudyCrewEvent {

    public static final String Topic = "study";

    private Long studyId;
    private Long userId;
    private String role;
}
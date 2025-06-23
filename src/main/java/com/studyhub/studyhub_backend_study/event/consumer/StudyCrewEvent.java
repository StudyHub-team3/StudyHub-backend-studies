package com.studyhub.studyhub_backend_study.event.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyCrewEvent {
    private Long studyId;
    private Long userId;
    private String role;
}
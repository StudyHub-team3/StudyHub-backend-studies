package com.studyhub.studyhub_backend_study.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaEvent<T> {
    private String eventType;
    private T data;
    private String timestamp;
}

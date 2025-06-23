package com.studyhub.studyhub_backend_study.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaEvent<T> {
    private String eventType;
    private T data;
    private String timestamp;
}

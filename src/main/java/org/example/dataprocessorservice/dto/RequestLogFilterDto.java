package org.example.dataprocessorservice.dto;

import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class RequestLogFilterDto {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long processingTimeMs;
    private Integer xmlTagsCount;
    private Integer jsonKeysCount;
}

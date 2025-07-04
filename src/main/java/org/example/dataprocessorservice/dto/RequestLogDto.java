package org.example.dataprocessorservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RequestLogDto {
    private Long id;
    private String jsonPayload;
    private long processingTimeMs;
    private int xmlTagCount;
    private int jsonKeyCount;
}

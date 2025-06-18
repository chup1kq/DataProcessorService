package org.example.dataprocessorservice.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder(toBuilder = true)
public class RequestLogFilterDto {
    private Long minProcessingTime;
    private Long maxProcessingTime;
    private Integer minXmlTags;
    private Integer maxXmlTags;
    private Integer minJsonKeys;
    private Integer maxJsonKeys;
}

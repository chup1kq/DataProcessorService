package org.example.dataprocessorservice.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class JsonDto {
    @JsonRawValue
    private String jsonPayload;
}

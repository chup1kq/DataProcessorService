package org.example.dataprocessorservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class PageResponseDto<T> {
    private List<T> content;
    private int number;
    private int pageSize;
    private int totalPages;
    private int totalElements;
}

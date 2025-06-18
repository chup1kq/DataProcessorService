package org.example.dataprocessorservice.mapper;

import org.example.dataprocessorservice.dto.RequestLogDto;
import org.example.dataprocessorservice.entity.RequestLog;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        injectionStrategy = InjectionStrategy.FIELD,
        componentModel = "spring"
)
public interface RequestLogMapper {
    RequestLogDto toDto(RequestLog requestLog);

    RequestLog toEntity(RequestLogDto requestLogDto);
}

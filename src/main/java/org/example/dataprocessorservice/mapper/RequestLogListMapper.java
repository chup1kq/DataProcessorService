package org.example.dataprocessorservice.mapper;

import org.example.dataprocessorservice.dto.RequestLogDto;
import org.example.dataprocessorservice.entity.RequestLog;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        injectionStrategy = InjectionStrategy.FIELD,
        componentModel = "spring",
        uses = RequestLogMapper.class
)
public interface RequestLogListMapper {
    List<RequestLogDto> toDtoList(List<RequestLog> requestLogs);

    List<RequestLog> toEntityList(List<RequestLogDto> requestLogsDto);
}

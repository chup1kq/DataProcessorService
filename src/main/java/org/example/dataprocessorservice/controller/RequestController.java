package org.example.dataprocessorservice.controller;

import lombok.AllArgsConstructor;
import org.example.dataprocessorservice.dto.JsonDto;
import org.example.dataprocessorservice.dto.RequestLogDto;
import org.example.dataprocessorservice.dto.RequestLogFilterDto;
import org.example.dataprocessorservice.service.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping
@AllArgsConstructor
public class RequestController {

    private final ConversionService conversionService;

    @PostMapping(path = "/request",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public JsonDto convertXmlToJson(@RequestBody String xml) {
        return conversionService.convertXmlToJson(xml);
    }

    @GetMapping(path = "/page",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Page<RequestLogDto> getRequestData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            @RequestParam(required = false) Long processingTime,
            @RequestParam(required = false) Integer xmlTagsCount,
            @RequestParam(required = false) Integer jsonKeysCount
    ) {
        RequestLogFilterDto requestLogFilterDto = RequestLogFilterDto.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .processingTimeMs(processingTime)
                .xmlTagsCount(xmlTagsCount)
                .jsonKeysCount(jsonKeysCount)
                .build();

        return conversionService.getRequestLogs(requestLogFilterDto, page);
    }
}
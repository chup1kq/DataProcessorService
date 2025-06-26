package org.example.dataprocessorservice.controller;

import lombok.AllArgsConstructor;
import org.example.dataprocessorservice.dto.JsonDto;
import org.example.dataprocessorservice.dto.RequestLogDto;
import org.example.dataprocessorservice.dto.RequestLogFilterDto;
import org.example.dataprocessorservice.service.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) Long minProcessingTime,
            @RequestParam(required = false) Long maxProcessingTime,
            @RequestParam(required = false) Integer minXmlTags,
            @RequestParam(required = false) Integer maxXmlTags,
            @RequestParam(required = false) Integer minJsonKeys,
            @RequestParam(required = false) Integer maxJsonKeys
    ) {
        RequestLogFilterDto requestLogFilterDto = RequestLogFilterDto.builder()
                .minProcessingTime(minProcessingTime)
                .maxProcessingTime(maxProcessingTime)
                .minXmlTags(minXmlTags)
                .maxXmlTags(maxXmlTags)
                .minJsonKeys(minJsonKeys)
                .maxJsonKeys(maxJsonKeys)
                .build();

        return conversionService.getRequestLogs(requestLogFilterDto, page);
    }
}
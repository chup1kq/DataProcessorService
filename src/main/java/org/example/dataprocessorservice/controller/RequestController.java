package org.example.dataprocessorservice.controller;

import lombok.AllArgsConstructor;
import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.service.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class RequestController {

    private final ConversionService conversionService;

    @PostMapping(path = "/request",consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String convertXmlToJson(@RequestBody String xml) {
        return conversionService.convertXmlToJson(xml);
    }

    @GetMapping(path = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequestLog> getRequestData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Long minProcessingTime,
            @RequestParam(required = false) Long maxProcessingTime,
            @RequestParam(required = false) Integer minXmlTags,
            @RequestParam(required = false) Integer maxXmlTags,
            @RequestParam(required = false) Integer minJsonKeys,
            @RequestParam(required = false) Integer maxJsonKeys
    ) {
        return conversionService.getRequestLogs(
                page,
                minProcessingTime,
                maxProcessingTime,
                minXmlTags,
                maxXmlTags,
                minJsonKeys,
                maxJsonKeys
        );
    }
}

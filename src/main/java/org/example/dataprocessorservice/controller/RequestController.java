package org.example.dataprocessorservice.controller;

import lombok.AllArgsConstructor;
import org.example.dataprocessorservice.service.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class RequestController {

    private final ConversionService conversionService;

    @PostMapping(path = "/request",consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String convertXmlToJson(@RequestBody String xml) {
        return conversionService.convertXmlToJson(xml);
    }
}

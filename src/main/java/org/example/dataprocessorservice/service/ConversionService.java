package org.example.dataprocessorservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.example.dataprocessorservice.dto.PageResponseDto;
import org.example.dataprocessorservice.dto.RequestLogDto;
import org.example.dataprocessorservice.dto.RequestLogFilterDto;
import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.exception.InvalidXmlException;
import org.example.dataprocessorservice.exception.XmlParserConfigurationException;
import org.example.dataprocessorservice.exception.XmlProcessingIOException;
import org.example.dataprocessorservice.mapper.RequestLogListMapper;
import org.example.dataprocessorservice.repository.RequestLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ConversionService {

    @Value("${spring.page.page-size}")
    private int pageSize;

    private final RequestLogRepository requestLogRepository;

    private final RequestLogListMapper requestLogListMapper;

    public PageResponseDto<RequestLogDto> getRequestLogs(RequestLogFilterDto requestLogFilterDto, int page) {
        Page<RequestLog> pageContent = requestLogRepository.getRequestLogs(
                requestLogFilterDto, PageRequest.of(page, pageSize));

        return PageResponseDto.<RequestLogDto>builder()
                .content(requestLogListMapper.toDtoList(pageContent.getContent()))
                .number(pageContent.getNumber())
                .pageSize(pageContent.getSize())
                .totalPages(pageContent.getTotalPages())
                .totalElements(pageContent.getNumberOfElements())
                .build();
    }

    public String convertXmlToJson(String xmlContent) {

        try {
            long start = System.currentTimeMillis();

            int xmlTagsCount = countXmlTags(xmlContent);

            ObjectMapper objectMapper = new ObjectMapper();
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(xmlContent.getBytes());
            String json = objectMapper.writeValueAsString(jsonNode);

            int jsonKeysCount = countJSONKeys(jsonNode);

            long conversionTime = System.currentTimeMillis() - start;

            RequestLog requestLog = RequestLog.builder()
                    .jsonPayload(json)
                    .jsonKeyCount(jsonKeysCount)
                    .xmlTagCount(xmlTagsCount)
                    .processingTimeMs(conversionTime)
                    .build();

            requestLogRepository.save(requestLog);

            return json;

        } catch (ParserConfigurationException e) {
            throw new XmlParserConfigurationException();
        } catch (SAXException e) {
            throw new InvalidXmlException();
        } catch (IOException e) {
            throw new XmlProcessingIOException();
        }
    }

    private int countXmlTags(String xmlContent) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xmlContent)))
                .getElementsByTagName("*")
                .getLength();
    }

    private int countJSONKeys(JsonNode jsonNode) {
        if (jsonNode == null || jsonNode.isNull()) return 0;
        return jsonNode.size() + StreamSupport.stream(jsonNode.spliterator(), false)
                .mapToInt(this::countJSONKeys)
                .sum();
    }
}

package org.example.dataprocessorservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ConversionService {

    @Value("${spring.page-size}")
    private int pageSize;

    private final RequestLogRepository requestLogRepository;

    public List<RequestLog> getRequestLogs(
            int page,
            Long minProcessingTime,
            Long maxProcessingTime,
            Integer minXmlTags,
            Integer maxXmlTags,
            Integer minJsonKeys,
            Integer maxJsonKeys
    ) {
        return requestLogRepository.getRequestLogs(
                page,
                pageSize,
                minProcessingTime,
                maxProcessingTime,
                minXmlTags,
                maxXmlTags,
                minJsonKeys,
                maxJsonKeys
        );
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

            requestLogRepository.save(RequestLog.builder()
                    .jsonPayload(json)
                    .jsonKeyCount(jsonKeysCount)
                    .xmlTagCount(xmlTagsCount)
                    .processingTimeMs(conversionTime)
                    .build()
            );

            return json;

        } catch (ParserConfigurationException e) {
            throw new RuntimeException("XML parser configuration error", e);
        } catch (SAXException e) {
            throw new RuntimeException("Invalid XML format", e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error during XML processing", e);
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

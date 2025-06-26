package org.example.dataprocessorservice.service;

import lombok.RequiredArgsConstructor;
import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MigrationService {

    private final RequestLogRepository requestLogRepository;

    private final S3Service s3Service;

    @Value("${spring.page.page-size}")
    private int pageSize;

    @Transactional
    @Scheduled(fixedDelayString = "${spring.migration.delay-ms}")
    public void migrateToS3() {

        int pageNumber = 0;
        Page<RequestLog> page;

        do {
            page = requestLogRepository.findByExternalIdIsNull(PageRequest.of(pageNumber, pageSize));
            List<RequestLog> logs = page.getContent();

            for (RequestLog log : logs) {
                String key = s3Service.uploadJson(log.getJsonPayload());
                log.setJsonPayload(null);
                log.setExternalId(key);
            }

            requestLogRepository.saveAll(logs);
            pageNumber++;
        } while (page.hasNext());
    }
}


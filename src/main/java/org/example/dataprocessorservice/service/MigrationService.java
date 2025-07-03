package org.example.dataprocessorservice.service;

import lombok.RequiredArgsConstructor;
import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MigrationService {

    private final RequestLogRepository requestLogRepository;

    private final MigrationProcessor migrationProcessor;

    @Value("${spring.page.page-size}")
    private int pageSize;

    @Scheduled(fixedDelayString = "${spring.migration.delay-ms}")
    public void migrateToS3() {

        int pageNumber = 0;
        Page<RequestLog> page;

        do {
            page = requestLogRepository.findByExternalIdIsNull(PageRequest.of(pageNumber, pageSize));

            migrationProcessor.processPage(page.getContent());
            pageNumber++;
        } while (page.hasNext());
    }
}


package org.example.dataprocessorservice.service;

import lombok.RequiredArgsConstructor;
import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.repository.RequestLogRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MigrationService {

    private final RequestLogRepository requestLogRepository;

    private final S3Service s3Service;

    @Transactional
    @Scheduled(fixedDelayString = "${spring.migration.delay-ms}")
    public void migrateToS3() {
        List<RequestLog> logsToMigrate = requestLogRepository.findByExternalIdIsNull();

        for (RequestLog log : logsToMigrate) {
            String key = s3Service.uploadJson(log.getJsonPayload());
            log.setJsonPayload(null);
            log.setExternalId(key);
        }

        requestLogRepository.saveAll(logsToMigrate);
    }
}


package org.example.dataprocessorservice.service;

import lombok.RequiredArgsConstructor;
import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.exception.ExceptionName;
import org.example.dataprocessorservice.exception.S3MigrationException;
import org.example.dataprocessorservice.repository.RequestLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MigrationProcessor {

    private final RequestLogRepository requestLogRepository;

    private final S3Service s3Service;

    @Transactional
    public void processPage(List<RequestLog> logs) {
        List<String> uploadedKeys = new ArrayList<>();

        try {
            for (RequestLog log : logs) {
                String key = s3Service.uploadJson(log.getJsonPayload());
                uploadedKeys.add(key);

                log.setJsonPayload(null);
                log.setExternalId(key);
            }
            requestLogRepository.saveAll(logs);
        } catch (Exception e) {
            uploadedKeys.forEach(s3Service::delete);
            throw new S3MigrationException(ExceptionName.S3_UPLOAD_ERROR);
        }
    }
}
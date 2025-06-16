package org.example.dataprocessorservice.repository;

import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.specifications.RequestLogSpecifications;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long>, JpaSpecificationExecutor<RequestLog> {

    default List<RequestLog> getRequestLogs(
            int page,
            int pageSize,
            Long minProcessingTime,
            Long maxProcessingTime,
            Integer minXmlTags,
            Integer maxXmlTags,
            Integer minJsonKeys,
            Integer maxJsonKeys
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return findAll(RequestLogSpecifications.withFilters(
                minProcessingTime,
                maxProcessingTime,
                minXmlTags,
                maxXmlTags,
                minJsonKeys,
                maxJsonKeys
        ), pageable).toList();
    }
}
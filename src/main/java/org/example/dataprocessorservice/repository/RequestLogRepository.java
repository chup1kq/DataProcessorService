package org.example.dataprocessorservice.repository;

import org.example.dataprocessorservice.dto.RequestLogFilterDto;
import org.example.dataprocessorservice.entity.RequestLog;
import org.example.dataprocessorservice.specifications.RequestLogSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface RequestLogRepository extends JpaRepository<RequestLog, Long>, JpaSpecificationExecutor<RequestLog> {

    Page<RequestLog> findByExternalIdIsNull(Pageable pageable);

    default Page<RequestLog> getRequestLogs(RequestLogFilterDto requestLogFilterDto, Pageable pageable) {
        return findAll(RequestLogSpecifications.withFilters(requestLogFilterDto), pageable);
    }
}
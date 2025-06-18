package org.example.dataprocessorservice.specifications;

import org.example.dataprocessorservice.dto.RequestLogFilterDto;
import org.example.dataprocessorservice.entity.RequestLog;
import org.springframework.data.jpa.domain.Specification;

public class RequestLogSpecifications {

    public static Specification<RequestLog> withFilters(RequestLogFilterDto requestLogFilterDto) {
        return Specification.<RequestLog>allOf()
                .and(min(requestLogFilterDto.getMinProcessingTime(), "processingTimeMs"))
                .and(max(requestLogFilterDto.getMaxProcessingTime(), "processingTimeMs"))
                .and(min(requestLogFilterDto.getMinXmlTags(), "xmlTagCount"))
                .and(max(requestLogFilterDto.getMaxXmlTags(), "xmlTagCount"))
                .and(min(requestLogFilterDto.getMinJsonKeys(), "jsonKeyCount"))
                .and(max(requestLogFilterDto.getMaxJsonKeys(), "jsonKeyCount"));
    }

    private static <T extends Number & Comparable<? super T>> Specification<RequestLog> min(T param, String fieldName) {
        return (root, query, cb) ->
                param != null ? cb.greaterThanOrEqualTo(root.get(fieldName), param) : null;
    }

    private static  <T extends Number & Comparable<? super T>> Specification<RequestLog> max(T param, String fieldName) {
        return (root, query, cb) ->
                param != null ? cb.lessThanOrEqualTo(root.get(fieldName), param) : null;
    }
}

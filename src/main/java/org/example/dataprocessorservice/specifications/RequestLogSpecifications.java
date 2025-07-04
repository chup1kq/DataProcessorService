package org.example.dataprocessorservice.specifications;

import org.example.dataprocessorservice.entity.RequestLog;
import org.springframework.data.jpa.domain.Specification;

public class RequestLogSpecifications {

    public static Specification<RequestLog> withFilters(
            Long minProcessingTime,
            Long maxProcessingTime,
            Integer minXmlTags,
            Integer maxXmlTags,
            Integer minJsonKeys,
            Integer maxJsonKeys
    ) {
        return Specification.<RequestLog>allOf()
                .and(min(minProcessingTime, "processingTimeMs"))
                .and(max(maxProcessingTime, "processingTimeMs"))
                .and(min(minXmlTags, "xmlTagCount"))
                .and(max(maxXmlTags, "xmlTagCount"))
                .and(min(minJsonKeys, "jsonKeyCount"))
                .and(max(maxJsonKeys, "jsonKeyCount"));
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

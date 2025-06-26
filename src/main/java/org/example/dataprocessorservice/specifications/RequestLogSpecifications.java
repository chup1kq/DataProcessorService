package org.example.dataprocessorservice.specifications;

import jakarta.persistence.criteria.Predicate;
import org.example.dataprocessorservice.dto.RequestLogFilterDto;
import org.example.dataprocessorservice.entity.RequestLog;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestLogSpecifications {

    public static Specification<RequestLog> withFilters(RequestLogFilterDto filter) {
        return Specification.<RequestLog>allOf()
                .and(dateBetween(filter.getFromDate(), filter.getToDate()))
                .and(equal(filter.getProcessingTimeMs(), "processingTimeMs"))
                .and(equal(filter.getXmlTagsCount(), "xmlTagCount"))
                .and(equal(filter.getJsonKeysCount(), "jsonKeyCount"));
    }

    private static Specification<RequestLog> dateBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, cb) -> {
            if (fromDate == null && toDate == null) return null;

            List<Predicate> predicates = new ArrayList<>();
            if (fromDate != null) predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), fromDate));
            if (toDate != null) predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), toDate));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static  <T extends Number & Comparable<? super T>> Specification<RequestLog> equal(T param, String fieldName) {
        return (root, query, cb) ->
                param != null ? cb.equal(root.get(fieldName), param) : null;
    }
}

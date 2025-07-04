package org.example.dataprocessorservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "request_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "json_payload", columnDefinition = "TEXT")
    private String jsonPayload;

    @Column(name = "processing_time_ms", nullable = false)
    private long processingTimeMs;

    @Column(name = "xml_tag_count")
    private Integer xmlTagCount;

    @Column(name = "json_key_count")
    private Integer jsonKeyCount;

    @Column(name = "external_id")
    private String externalId;
}

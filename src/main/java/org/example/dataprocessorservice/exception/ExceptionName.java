package org.example.dataprocessorservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionName {
    INVALID_BODY(
            "ERR-BODY-400",
            "Request body is invalid or malformed",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_QUERY_PARAM(
            "ERR-QUERY-400",
            "Query parameter is missing or invalid",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_XML(
            "ERR-XML-400",
            "Invalid XML format",
            HttpStatus.BAD_REQUEST
    ),

    XML_PARSER_ERROR(
            "ERR-XML-500",
            "Internal XML parser error",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    XML_PROCESSING_IO_ERROR(
            "ERR-XML-503",
            "Internal XML parser error",
            HttpStatus.SERVICE_UNAVAILABLE
    ),

    S3_UPLOAD_ERROR(
            "ERR-S3-500",
            "Failed to upload file to S3 storage",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    S3_DOWNLOAD_ERROR(
            "ERR-S3-500",
            "Failed to download file from S3 storage",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    S3_DELETE_ERROR(
            "ERR-S3-500",
            "Failed to delete file from S3 storage",
            HttpStatus.INTERNAL_SERVER_ERROR
    );

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ExceptionName(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

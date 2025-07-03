package org.example.dataprocessorservice.exception;

public class S3MigrationException extends S3Exception {

    public S3MigrationException(ExceptionName exceptionName) {
        super(exceptionName);
    }
}

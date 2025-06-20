package org.example.dataprocessorservice.exception;

public class S3Exception extends BaseAppException {
    public S3Exception(ExceptionName exceptionName) {
        super(exceptionName);
    }
}

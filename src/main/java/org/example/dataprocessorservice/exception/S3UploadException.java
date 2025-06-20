package org.example.dataprocessorservice.exception;

public class S3UploadException extends S3Exception {
    public S3UploadException() {
        super(ExceptionName.S3_UPLOAD_ERROR);
    }
}

package org.example.dataprocessorservice.exception;

public class S3DownloadException extends S3Exception {
    public S3DownloadException() {
        super(ExceptionName.S3_DOWNLOAD_ERROR);
    }
}

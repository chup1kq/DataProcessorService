package org.example.dataprocessorservice.exception;

public class S3DeleteException extends S3Exception {

  public S3DeleteException() {
    super(ExceptionName.S3_DELETE_ERROR);
  }
}

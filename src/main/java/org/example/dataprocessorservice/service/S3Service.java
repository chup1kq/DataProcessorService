package org.example.dataprocessorservice.service;

import lombok.RequiredArgsConstructor;
import org.example.dataprocessorservice.exception.S3DeleteException;
import org.example.dataprocessorservice.exception.S3DownloadException;
import org.example.dataprocessorservice.exception.S3UploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public String uploadJson(String json) {
        try {
            String key = UUID.randomUUID().toString();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("application/json")
                    .build();

            s3Client.putObject(request, RequestBody.fromString(json));

            return key;
        } catch (S3Exception e) {
            throw new S3UploadException();
        }
    }

    public String downloadJson(String key) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(request);
            return responseBytes.asUtf8String();
        } catch (S3Exception e) {
            throw new S3DownloadException();
        }
    }

    public void delete(String key) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(request);
        } catch (S3Exception e) {
            throw new S3DeleteException();
        }
    }
}
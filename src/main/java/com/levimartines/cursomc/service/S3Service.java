package com.levimartines.cursomc.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile file) {
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Erro de IO: " + e.getMessage());
        }
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        return uploadFile(is, fileName, contentType);
    }

    private URI uploadFile(InputStream is, String fileName, String contentType) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        log.info("Iniciando upload");
        s3Client.putObject(bucketName, fileName, is, objectMetadata);
        log.info("Upload finalizado");
        try {
            return s3Client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Erro ao converter URL para URI");
        }
    }

}
